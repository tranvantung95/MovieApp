package com.example.share.feature.movie.domain

import com.example.share.feature.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface SearchMoviesUseCase {
    suspend operator fun invoke(query: String): Flow<Result<List<Movie>>>
}

class SearchMoviesUseCaseImpl(
    private val movieRepository: MovieGateway
) : SearchMoviesUseCase {

    override suspend fun invoke(query: String): Flow<Result<List<Movie>>> {
        return if (query.isBlank()) {
            flow {
                Result.success(emptyList<Movie>())
            }
        } else {
            movieRepository.searchMovies(query)
        }
    }
}