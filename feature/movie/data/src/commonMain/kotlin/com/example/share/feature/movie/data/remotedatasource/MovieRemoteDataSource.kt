package com.example.share.feature.movie.data.remotedatasource

import com.example.share.feature.movie.data.dto.MovieDTO

interface MovieRemoteDataSource {
    suspend fun getTrendingMovies(): List<MovieDTO>
    suspend fun searchMovies(query: String): List<MovieDTO>
}