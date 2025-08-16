package com.example.movieapp.android


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.share.feature.movie.domain.GetMovieDetailUseCase
import com.example.share.feature.movie.domain.GetMoviesUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class TestViewModel(val getMovieUseCase: GetMoviesUseCase, private val getMovieDetailUseCase: GetMovieDetailUseCase) : ViewModel() {

    private var movieQuery by mutableStateOf("")
    private val queryFlow = snapshotFlow { movieQuery }
        .distinctUntilChanged().debounce(300)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val apiResultsFlow = queryFlow
        .flatMapLatest { query ->
            println("state: flatMapLatest triger with query: $query ")
            getMovieUseCase.invoke(query)
                .map { result ->
                    result.fold(
                        onSuccess = { movies ->
                            println("state: onSuccess triger with query: $query ${movies.size}")
                            ApiState.Success(movies)
                        },
                        onFailure = { error -> ApiState.Error(error.message ?: "Unknown error") }
                    )
                }
                .onStart {
                    emit(ApiState.Loading)
                }
        }
        .catch { exception ->
            emit(ApiState.Error(exception.message ?: "Unknown error"))
        }

    val uiState: StateFlow<MovieListUiState> = combine(
        queryFlow,
        apiResultsFlow
    ) { query, apiResult ->
        println("state: combine triger with query and app Result: $query - $apiResult")
        when (apiResult) {
            is ApiState.Loading -> MovieListUiState(
                searchQuery = query,
                isLoading = true,
                movieUiSize = 0,
                errorMessage = null
            )

            is ApiState.Success -> MovieListUiState(
                searchQuery = query,
                isLoading = false,
                movieUiSize = apiResult.data.size,
                errorMessage = null
            )

            is ApiState.Error -> MovieListUiState(
                searchQuery = query,
                isLoading = false,
                movieUiSize = -1,
                errorMessage = apiResult.message
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MovieListUiState()
    )

    fun onSearchQueryChanged(query: String) {
        movieQuery = query
    }
}

sealed class ApiState<out T> {
    data class Success<T>(val data: T) : ApiState<T>()
    data class Error(val message: String) : ApiState<Nothing>()
    data object Loading : ApiState<Nothing>()
}

data class MovieListUiState(
    val movieUiSize: Int = 0,
    val filteredMovieUis: Int = 0,
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false
)