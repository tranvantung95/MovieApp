package com.example.share.feature.movie.data

import com.example.share.core.data.CacheDuration
import com.example.share.core.data.CacheStrategy
import com.example.share.core.database.dao.MovieDao
import com.example.share.core.database.dao.TrendingCacheDao
import com.example.share.core.database.entity.TrendingCacheEntity
import com.example.share.core.network.ApiClient
import com.example.share.feature.movie.data.dto.TrendingMoviesResponse
import com.example.share.feature.movie.data.mapper.MovieEntityMapper
import io.ktor.client.call.body

class TrendingMoviesStrategy(
    private val apiClient: ApiClient,
    private val movieEntityMapper: MovieEntityMapper,
    private val movieDao: MovieDao,
    private val trendingCacheDao: TrendingCacheDao
) : CacheStrategy<TrendingMoviesResponse> {
    override suspend fun getCacheTime(): Long? {
        return trendingCacheDao.getTrendingCacheTime()
    }

    override suspend fun isExpired(cacheTime: Long?): Boolean {
        return CacheDuration.isExpired(cacheTime, CacheDuration.TRENDING_MOVIES)
    }
    override suspend fun fetchFromApi(): TrendingMoviesResponse {
        val response = apiClient.getData("trending", "movie","day", queryParams = mapOf("language" to "en-US")).body<TrendingMoviesResponse>()
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
        movieDao.insertMovies(movieEntity)
        val movieIds = data.results.joinToString(",") { it.id.toString() }
        val trendingCacheEntity = TrendingCacheEntity(
            movieIds = movieIds,
            page = data.page,
            totalPages = data.totalPages,
            totalResults = data.totalResults
        )
        trendingCacheDao.insertTrendingCache(trendingCacheEntity)
    }

}