package com.example.share.feature.movie.data.di

import com.example.share.feature.movie.data.MovieRepositoryImpl
import com.example.share.feature.movie.data.TrendingMoviesStrategy
import com.example.share.feature.movie.domain.MovieGateway
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import org.koin.core.qualifier.named
import org.koin.dsl.module

val movieRepositoryModule = module {
    single(named("repositoryScope")) {
        CoroutineScope(SupervisorJob() + Dispatchers.IO + CoroutineName("MovieRepository"))
    }
    single<MovieGateway> {
        MovieRepositoryImpl(get(), get(), get(named("repositoryScope")), get(), get(), get())
    }
    factory {
        TrendingMoviesStrategy(get(), get(), get(), get())
    }
}