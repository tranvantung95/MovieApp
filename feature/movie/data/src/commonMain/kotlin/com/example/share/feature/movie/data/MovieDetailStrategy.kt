package com.example.share.feature.movie.data

import com.example.share.core.data.CacheDuration
import com.example.share.core.data.CacheStrategy
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
import com.example.share.core.network.ApiClient
import com.example.share.feature.movie.data.dto.MovieDetailDTO
import com.example.share.feature.movie.data.mapper.MovieDetailEntityMapper
import io.ktor.client.call.body

class MovieDetailStrategy(
    private val movieId: Int,
    private val apiClient: ApiClient,
    private val movieDetailEntityMapper: MovieDetailEntityMapper,
    private val movieDetailDao: MovieDetailDao,
    private val genreDao: GenreDao,
    private val productionCompanyDao: ProductionCompanyDao,
    private val productionCountryDao: ProductionCountryDao,
    private val spokenLanguageDao: SpokenLanguageDao,
    private val movieDetailCrossRefDao: MovieDetailCrossRefDao,
    private val transaction: DatabaseTransactionExecutor
) : CacheStrategy<MovieDetailDTO> {
    override suspend fun getCacheTime(): Long? {
        return movieDetailDao.getMovieDetailCacheTime(movieId)
    }

    override suspend fun isExpired(cacheTime: Long?): Boolean {
        return CacheDuration.isExpired(cacheTime, CacheDuration.MOVIE_DETAIL)
    }

    override suspend fun fetchFromApi(): MovieDetailDTO {
        val response = apiClient.getData("/search-movie/$movieId")
        return response.body()
    }

    override suspend fun saveToDatabase(data: MovieDetailDTO) {
        transaction.executeInTransaction {
            val movieDetailEntity = movieDetailEntityMapper.apiMovieDetailToEntities(data)

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
            val genreCrossRefs = data.genres.map {
                MovieDetailGenreCrossRef(movieDetailEntity.movieDetail.id, it.id)
            }
            movieDetailCrossRefDao.insertMovieGenres(genreCrossRefs)

            val companyCrossRefs = data.productionCompanies.map {
                MovieDetailProductionCompanyCrossRef(movieDetailEntity.movieDetail.id, it.id)
            }
            movieDetailCrossRefDao.insertMovieProductionCompanies(companyCrossRefs)

            val countryCrossRefs = data.productionCountries.map {
                MovieDetailProductionCountryCrossRef(
                    movieDetailEntity.movieDetail.id,
                    it.iso31661
                )
            }
            movieDetailCrossRefDao.insertMovieProductionCountries(countryCrossRefs)

            val languageCrossRefs = data.spokenLanguages.map {
                MovieDetailSpokenLanguageCrossRef(movieDetailEntity.movieDetail.id, it.iso6391)
            }
            movieDetailCrossRefDao.insertMovieSpokenLanguages(languageCrossRefs)
        }

    }
}