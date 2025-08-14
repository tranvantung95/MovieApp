package com.example.share.feature.movie.data

import androidx.room.immediateTransaction
import androidx.room.useWriterConnection
import com.example.share.core.data.CacheDuration
import com.example.share.core.data.CacheStrategy
import com.example.share.core.database.MovieDatabase
import com.example.share.core.database.refence.MovieDetailGenreCrossRef
import com.example.share.core.database.refence.MovieDetailProductionCompanyCrossRef
import com.example.share.core.database.refence.MovieDetailProductionCountryCrossRef
import com.example.share.core.database.refence.MovieDetailSpokenLanguageCrossRef
import com.example.share.core.network.ApiClient
import com.example.share.feature.movie.data.dto.MovieDetailDTO
import com.example.share.feature.movie.data.mapper.MovieDetailEntityMapper
import io.ktor.client.call.body

class MovieDetailStrategy(
    private val database: MovieDatabase,
    private val movieId: Int,
    private val apiClient: ApiClient,
    private val movieDetailEntityMapper: MovieDetailEntityMapper
) : CacheStrategy<MovieDetailDTO> {
    override suspend fun getCacheTime(): Long? {
        return database.movieDetailDao().getMovieDetailCacheTime(movieId)
    }

    override suspend fun isExpired(cacheTime: Long?): Boolean {
        return CacheDuration.isExpired(cacheTime, CacheDuration.MOVIE_DETAIL)
    }

    override suspend fun fetchFromApi(): MovieDetailDTO {
        val response = apiClient.getData("movieId")
        return response.body()
    }

    override suspend fun saveToDatabase(data: MovieDetailDTO) {
        database.useWriterConnection { transactor ->
            val movieDetailEntity = movieDetailEntityMapper.apiMovieDetailToEntities(data)
            transactor.immediateTransaction {
                // Insert all entities
                database.movieDetailDao().insertMovieDetail(movieDetailEntity.movieDetail)
                database.genreDao().insertGenres(movieDetailEntity.genres)
                database.productionCompanyDao()
                    .insertProductionCompanies(movieDetailEntity.productionCompanies)
                database.productionCountryDao()
                    .insertProductionCountries(movieDetailEntity.productionCountries)
                database.spokenLanguageDao()
                    .insertSpokenLanguages(movieDetailEntity.spokenLanguages)

                // Clear existing relationships
                database.movieDetailCrossRefDao()
                    .deleteMovieGenres(movieDetailEntity.movieDetail.id)
                database.movieDetailCrossRefDao()
                    .deleteMovieProductionCompanies(movieDetailEntity.movieDetail.id)
                database.movieDetailCrossRefDao()
                    .deleteMovieProductionCountries(movieDetailEntity.movieDetail.id)
                database.movieDetailCrossRefDao()
                    .deleteMovieSpokenLanguages(movieDetailEntity.movieDetail.id)

                // Insert new relationships
                val genreCrossRefs = data.genres.map {
                    MovieDetailGenreCrossRef(movieDetailEntity.movieDetail.id, it.id)
                }
                database.movieDetailCrossRefDao().insertMovieGenres(genreCrossRefs)

                val companyCrossRefs = data.productionCompanies.map {
                    MovieDetailProductionCompanyCrossRef(movieDetailEntity.movieDetail.id, it.id)
                }
                database.movieDetailCrossRefDao().insertMovieProductionCompanies(companyCrossRefs)

                val countryCrossRefs = data.productionCountries.map {
                    MovieDetailProductionCountryCrossRef(
                        movieDetailEntity.movieDetail.id,
                        it.iso31661
                    )
                }
                database.movieDetailCrossRefDao().insertMovieProductionCountries(countryCrossRefs)

                val languageCrossRefs = data.spokenLanguages.map {
                    MovieDetailSpokenLanguageCrossRef(movieDetailEntity.movieDetail.id, it.iso6391)
                }
                database.movieDetailCrossRefDao().insertMovieSpokenLanguages(languageCrossRefs)
            }

        }
    }
}