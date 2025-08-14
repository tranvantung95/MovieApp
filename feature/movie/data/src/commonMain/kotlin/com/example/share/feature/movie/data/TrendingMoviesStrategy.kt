package com.example.share.feature.movie.data

import com.example.share.core.data.CacheDuration
import com.example.share.core.data.CacheStrategy
import com.example.share.core.database.MovieDatabase
import com.example.share.core.database.entity.TrendingCacheEntity
import com.example.share.core.network.ApiClient
import com.example.share.feature.movie.data.dto.TrendingMoviesResponse
import com.example.share.feature.movie.data.mapper.MovieDetailEntityMapper
import com.example.share.feature.movie.data.mapper.MovieEntityMapper
import io.ktor.client.call.body

class TrendingMoviesStrategy(
    private val database: MovieDatabase,
    private val apiClient: ApiClient,
    private val movieEntityMapper: MovieEntityMapper,
) : CacheStrategy<TrendingMoviesResponse> {
    override suspend fun getCacheTime(): Long? {
        return database.trendingCacheDao().getTrendingCacheTime()
    }

    override suspend fun isExpired(cacheTime: Long?): Boolean {
        return CacheDuration.isExpired(cacheTime, CacheDuration.TRENDING_MOVIES)
    }

    override suspend fun fetchFromApi(): TrendingMoviesResponse {
        val response = apiClient.getData("").body<TrendingMoviesResponse>()
        return TrendingMoviesResponse(
            results = response.results,
            page = response.page,
            totalPages = response.totalPages,
            totalResults = response.totalResults
        )
    }

    override suspend fun saveToDatabase(data: TrendingMoviesResponse) {
        val movieEntity = data.results.map { movie ->
            movieEntityMapper.mapToEntity(movie)
        }
        database.movieDao().insertMovies(movieEntity)
        val movieIds = data.results.joinToString(",") { it.id.toString() }
        val trendingCacheEntity = TrendingCacheEntity(
            movieIds = movieIds,
            page = data.page,
            totalPages = data.totalPages,
            totalResults = data.totalResults
        )
        database.trendingCacheDao().insertTrendingCache(trendingCacheEntity)
    }

}