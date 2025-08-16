package com.example.movieapp.android


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.share.feature.movie.domain.GetMovieDetailUseCase
import com.example.share.feature.movie.domain.GetMoviesUseCase
import com.example.share.feature.movie.domain.model.Movie
import com.example.share.feature.movie.domain.model.MovieDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class TestViewModel(
    val getMovieUseCase: GetMoviesUseCase,
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {
    private var movieQuery by mutableStateOf("")
    private val queryFlow = snapshotFlow { movieQuery }
        .distinctUntilChanged()

    private val _uiState = MutableStateFlow(MovieListUiState())
    private val getDetailFlow = queryFlow.flatMapLatest { query ->
        getMovieUseCase.invoke(query)
            .map { result ->
                result.fold(
                    onSuccess = { movies ->
                        ApiState.Success(movies)
                    },
                    onFailure = { error ->
                        ApiState.Error(error.message ?: "Unknown error")
                    }
                )
            }
            .onStart {
                emit(ApiState.Loading)
            }
    }
        .catch { exception ->
            emit(ApiState.Error(exception.message ?: "Unknown error"))
        }

    val uiState: StateFlow<MovieListUiState> =
        combine(getDetailFlow, _uiState) { movies, currentState ->
            when (movies) {
                is ApiState.Loading -> {
                    currentState.copy(isLoading = true)
                }

                is ApiState.Error -> {
                    currentState.copy(isLoading = false, errorMessage = movies.message)
                }

                is ApiState.Success -> {
                    currentState.copy(
                        isLoading = false,
                        movieUiSize = movies.data.size
                    )
                }
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

data class MovieDetailUiState(
    val movieDetail: MovieDetail? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isFavorite: Boolean = false,
    val isWatchlisted: Boolean = false,
    val similarMovieUis: List<Movie> = emptyList(),
    val isLoadingSimilar: Boolean = false
)