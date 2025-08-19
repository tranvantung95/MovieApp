package com.example.share.feature.movie.data.remotedatasource

import com.example.share.core.network.ApiClient
import com.example.share.feature.movie.data.dto.MovieDetailDTO
import io.ktor.client.call.body

class MovieDetailRemoteDataSourceImpl(private val apiClient: ApiClient) :
    MovieDetailRemoteDataSource {
    override suspend fun getMovieDetail(movieId: Int): MovieDetailDTO {
        val response =
            apiClient.getData("movie", movieId.toString())
        return response.body()
    }
}