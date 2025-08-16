package com.example.share.feature.movie.domain

import com.example.share.feature.movie.domain.model.Movie
import com.example.share.feature.movie.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieGateway {
     fun getTrendingMovies(): Flow<Result<List<Movie>>>
     fun searchMovies(query: String): Flow<Result<List<Movie>>>
    fun getMovieDetail(movieId: Int): Flow<Result<MovieDetail?>>
}