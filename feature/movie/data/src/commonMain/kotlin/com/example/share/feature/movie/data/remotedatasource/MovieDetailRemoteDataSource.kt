package com.example.share.feature.movie.data.remotedatasource

import com.example.share.feature.movie.data.dto.MovieDetailDTO

interface MovieDetailRemoteDataSource {
    suspend fun getMovieDetail(movieId: Int): MovieDetailDTO
}