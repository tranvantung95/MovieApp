package com.example.share.feature.movie.domain

import com.example.share.feature.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface GetMoviesUseCase {
    suspend operator fun invoke(searchQuery: String): Flow<Result<List<Movie>>>
}

class GetMoviesUseCaseImpl(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : GetMoviesUseCase {

    override suspend fun invoke(searchQuery: String): Flow<Result<List<Movie>>> {
        return if (searchQuery.isEmpty()) {
            getTrendingMoviesUseCase()
        } else {
            searchMoviesUseCase(searchQuery)
        }
    }
}