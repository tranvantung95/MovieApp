package com.example.share.feature.movie.domain.di

import com.example.share.feature.movie.domain.GetMovieDetailUseCase
import com.example.share.feature.movie.domain.GetMovieDetailUseCaseImpl
import com.example.share.feature.movie.domain.GetMoviesUseCase
import com.example.share.feature.movie.domain.GetMoviesUseCaseImpl
import com.example.share.feature.movie.domain.GetMoviesUseCaseIos
import com.example.share.feature.movie.domain.GetTrendingMoviesUseCase
import com.example.share.feature.movie.domain.GetTrendingMoviesUseCaseImpl
import com.example.share.feature.movie.domain.SearchMoviesUseCase
import com.example.share.feature.movie.domain.SearchMoviesUseCaseImpl
import org.koin.dsl.module

val movieUseCaseModule = module {
    factory<GetMovieDetailUseCase> {
        GetMovieDetailUseCaseImpl(get())
    }
    factory<GetMoviesUseCase> {
        GetMoviesUseCaseImpl(get(), get())
    }
    factory<GetTrendingMoviesUseCase> {
        GetTrendingMoviesUseCaseImpl(get())
    }
    factory<SearchMoviesUseCase> {
        SearchMoviesUseCaseImpl(get())
    }
    factory {
        GetMoviesUseCaseIos(get())
    }
}