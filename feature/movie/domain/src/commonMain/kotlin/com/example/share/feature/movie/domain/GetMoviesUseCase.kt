package com.example.share.feature.movie.domain

import com.example.share.feature.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach

interface GetMoviesUseCase {
    operator fun invoke(searchQuery: String): Flow<Result<List<Movie>>>
}

class GetMoviesUseCaseImpl(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase
) : GetMoviesUseCase {

    override fun invoke(searchQuery: String): Flow<Result<List<Movie>>> {
        return if (searchQuery.isEmpty()) {
            getTrendingMoviesUseCase.invoke()
        } else {
            searchMoviesUseCase.invoke(searchQuery)
        }
    }
}