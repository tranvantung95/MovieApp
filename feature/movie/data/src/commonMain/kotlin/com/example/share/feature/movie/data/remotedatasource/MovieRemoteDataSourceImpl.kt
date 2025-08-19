package com.example.share.feature.movie.data.remotedatasource

import com.example.share.core.network.ApiClient
import com.example.share.feature.movie.data.dto.MovieDTO
import com.example.share.feature.movie.data.dto.TrendingMoviesResponse
import io.ktor.client.call.body

class MovieRemoteDataSourceImpl(private val apiClient: ApiClient) : MovieRemoteDataSource {
    override suspend fun getTrendingMovies(): List<MovieDTO> {
        return apiClient.getData(
            "trending",
            "movie",
            "day",
            queryParams = mapOf("language" to "en-US")
        ).body<TrendingMoviesResponse>().results
    }

    override suspend fun searchMovies(query: String): List<MovieDTO> {
        return apiClient.getData("search", "movie", queryParams = mapOf("query" to query))
            .body<TrendingMoviesResponse>().results
    }
}