package com.example.share.feature.movie.data

import com.example.share.core.data.CacheDuration
import com.example.share.core.data.CacheStrategy
import com.example.share.feature.movie.data.dto.MovieDTO
import com.example.share.feature.movie.data.dto.TrendingMoviesResponse

class TestTrendingMoviesStrategy(
    private val cacheDao: TestTrendingCacheDao
) : CacheStrategy<TrendingMoviesResponse> {
    var apiCalled = false
    var dataSaved = false

    override suspend fun getCacheTime(): Long? = cacheDao.getTrendingCacheTime()

    override suspend fun isExpired(cacheTime: Long?): Boolean {
        return CacheDuration.isExpired(cacheTime, CacheDuration.TRENDING_MOVIES)
    }

    override suspend fun fetchFromApi(): TrendingMoviesResponse {
        apiCalled = true
        return TrendingMoviesResponse(
            results = listOf(
                MovieDTO(
                    id = 1, title = "Movie 1", originalTitle = "Movie 1", overview = "Overview 1",
                    posterPath = null, backdropPath = null, releaseDate = null, voteAverage = 7.0,
                    voteCount = 1000, popularity = 50.0, adult = false, originalLanguage = "en",
                    video = false, genreIds = listOf(18), mediaType = "movie"
                )
            ),
            page = 1, totalPages = 1, totalResults = 1
        )
    }

    override suspend fun saveToDatabase(data: TrendingMoviesResponse) {
        dataSaved = true
    }

    fun reset() {
        apiCalled = false
        dataSaved = false
    }
}