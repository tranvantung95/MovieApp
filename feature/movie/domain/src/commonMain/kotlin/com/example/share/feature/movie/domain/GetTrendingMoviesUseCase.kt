package com.example.share.feature.movie.domain

import com.example.share.feature.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow


interface GetTrendingMoviesUseCase {
    suspend operator fun invoke(): Flow<Result<List<Movie>>>
}

class GetTrendingMoviesUseCaseImpl(
    private val movieRepository: MovieGateway
) : GetTrendingMoviesUseCase {

    override suspend fun invoke(): Flow<Result<List<Movie>>> {
        return movieRepository.getTrendingMovies()
    }
}