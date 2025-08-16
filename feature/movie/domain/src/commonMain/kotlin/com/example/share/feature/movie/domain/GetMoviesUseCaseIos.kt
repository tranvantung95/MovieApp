package com.example.share.feature.movie.domain

import com.example.share.feature.movie.domain.model.Movie
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GetMoviesUseCaseIos(private val getMoviesUseCase: GetMoviesUseCase) {
    fun invoke(
        searchQuery: String,
        onResult: (List<Movie>?) -> Unit,
        onError: (String) -> Unit,
    ) {
        MainScope().launch {
            try {
                val result = getMoviesUseCase.invoke(searchQuery).first()
                result.fold(
                    onSuccess = { movies -> onResult(movies) },
                    onFailure = { error -> onError(error.message ?: "Unknown error") }
                )
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            }
        }
    }
}