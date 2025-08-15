package com.example.movieapp.android


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.share.feature.movie.domain.GetMovieDetailUseCase
import com.example.share.feature.movie.domain.GetMoviesUseCase
import kotlinx.coroutines.launch

class TestViewModel(val getMovieUseCase: GetMoviesUseCase, private val getMovieDetailUseCase: GetMovieDetailUseCase) : ViewModel() {

    init {
        getMovieDetail()
    }

    fun getMovieTest() {
        viewModelScope.launch {
            getMovieUseCase.invoke("dd").collect {
                it.onSuccess {
                    println("result -----> $it")
                }.onFailure {
                    println(it.message)
                }
            }
        }
    }
    fun  getMovieDetail(){
        viewModelScope.launch {
            getMovieDetailUseCase.invoke(55178).collect{
                it.onSuccess {
                    println("result -----> $it")
                }.onFailure {
                    println( it.message)
                }
            }
        }
    }
}