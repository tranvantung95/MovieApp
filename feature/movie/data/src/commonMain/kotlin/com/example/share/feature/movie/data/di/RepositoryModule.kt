package com.example.share.feature.movie.data.di

import com.example.share.feature.movie.data.MovieRepositoryImpl
import com.example.share.feature.movie.domain.MovieGateway
import org.koin.dsl.module

val movieRepositoryModule = module {
    single<MovieGateway> {
        MovieRepositoryImpl(get(), get(), get(), get(), get())
    }
}