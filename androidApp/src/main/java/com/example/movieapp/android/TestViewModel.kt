package com.example.movieapp.android


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.share.feature.movie.domain.GetMoviesUseCase
import kotlinx.coroutines.launch

class TestViewModel(val getMovieUseCase: GetMoviesUseCase) : ViewModel() {

    init {
        getMovieTest()
    }

    fun getMovieTest() {
        viewModelScope.launch {
            getMovieUseCase.invoke("").collect {
                it.onSuccess {
                    println(it)
                }.onFailure {
                    println(it.message)
                }
            }
        }

    }
}