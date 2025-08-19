package com.example.share.feature.movie.data.localdatasource

import com.example.share.core.database.DatabaseTransactionExecutor
import com.example.share.core.database.dao.GenreDao
import com.example.share.core.database.dao.MovieDetailCrossRefDao
import com.example.share.core.database.dao.MovieDetailDao
import com.example.share.core.database.dao.ProductionCompanyDao
import com.example.share.core.database.dao.ProductionCountryDao
import com.example.share.core.database.dao.SpokenLanguageDao
import com.example.share.core.database.refence.MovieDetailGenreCrossRef
import com.example.share.core.database.refence.MovieDetailProductionCompanyCrossRef
import com.example.share.core.database.refence.MovieDetailProductionCountryCrossRef
import com.example.share.core.database.refence.MovieDetailSpokenLanguageCrossRef
import com.example.share.core.database.refence.MovieDetailWithRelations
import com.example.share.feature.movie.data.dto.MovieDetailDTO
import com.example.share.feature.movie.data.mapper.MovieDetailEntityMapper
import kotlinx.coroutines.flow.Flow

class MovieDetailLocalDataSourceImpl(
    private val movieDetailDao: MovieDetailDao,
    private val transaction: DatabaseTransactionExecutor,
    private val movieDetailEntityMapper: MovieDetailEntityMapper,
    private val genreDao: GenreDao,
    private val productionCompanyDao: ProductionCompanyDao,
    private val productionCountryDao: ProductionCountryDao,
    private val spokenLanguageDao: SpokenLanguageDao,
    private val movieDetailCrossRefDao: MovieDetailCrossRefDao,
) : MovieDetailLocalDataSource {
    override suspend fun saveMovieDetail(movieDetailDTO: MovieDetailDTO) {
        transaction.executeInTransaction {
            val movieDetailEntity = movieDetailEntityMapper.apiMovieDetailToEntities(movieDetailDTO)

            // Insert all entities
            movieDetailDao.insertMovieDetail(movieDetailEntity.movieDetail)
            genreDao.insertGenres(movieDetailEntity.genres)
            productionCompanyDao.insertProductionCompanies(movieDetailEntity.productionCompanies)
            productionCountryDao.insertProductionCountries(movieDetailEntity.productionCountries)
            spokenLanguageDao.insertSpokenLanguages(movieDetailEntity.spokenLanguages)

            // Clear existing relationships
            movieDetailCrossRefDao.deleteMovieGenres(movieDetailEntity.movieDetail.id)
            movieDetailCrossRefDao.deleteMovieProductionCompanies(movieDetailEntity.movieDetail.id)
            movieDetailCrossRefDao.deleteMovieProductionCountries(movieDetailEntity.movieDetail.id)
            movieDetailCrossRefDao.deleteMovieSpokenLanguages(movieDetailEntity.movieDetail.id)

            // Insert new relationships
            val genreCrossRefs = movieDetailDTO.genres.map {
                MovieDetailGenreCrossRef(movieDetailEntity.movieDetail.id, it.id)
            }
            movieDetailCrossRefDao.insertMovieGenres(genreCrossRefs)

            val companyCrossRefs = movieDetailDTO.productionCompanies.map {
                MovieDetailProductionCompanyCrossRef(movieDetailEntity.movieDetail.id, it.id)
            }
            movieDetailCrossRefDao.insertMovieProductionCompanies(companyCrossRefs)

            val countryCrossRefs = movieDetailDTO.productionCountries.map {
                MovieDetailProductionCountryCrossRef(
                    movieDetailEntity.movieDetail.id,
                    it.iso31661
                )
            }
            movieDetailCrossRefDao.insertMovieProductionCountries(countryCrossRefs)

            val languageCrossRefs = movieDetailDTO.spokenLanguages.map {
                MovieDetailSpokenLanguageCrossRef(movieDetailEntity.movieDetail.id, it.iso6391)
            }
            movieDetailCrossRefDao.insertMovieSpokenLanguages(languageCrossRefs)
        }
    }

    override fun getMovieDetail(movieId: Int): Flow<MovieDetailWithRelations?> {
        return movieDetailDao.getMovieDetailWithRelations(movieId = movieId)
    }

    override suspend fun getMovieDetailExpiredTime(movieId: Int): Long {
        return movieDetailDao.getMovieDetailCacheTime(movieId = movieId) ?: 0
    }
}