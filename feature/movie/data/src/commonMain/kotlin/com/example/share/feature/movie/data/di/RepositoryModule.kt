package com.example.share.feature.movie.data.di

import com.example.share.feature.movie.data.localdatasource.MovieDetailLocalDataSource
import com.example.share.feature.movie.data.localdatasource.MovieDetailLocalDataSourceImpl
import com.example.share.feature.movie.data.remotedatasource.MovieDetailRemoteDataSource
import com.example.share.feature.movie.data.remotedatasource.MovieDetailRemoteDataSourceImpl
import com.example.share.feature.movie.data.localdatasource.MovieLocalDataSource
import com.example.share.feature.movie.data.localdatasource.MovieLocalDataSourceImpl
import com.example.share.feature.movie.data.remotedatasource.MovieRemoteDataSource
import com.example.share.feature.movie.data.remotedatasource.MovieRemoteDataSourceImpl
import com.example.share.feature.movie.data.MovieRepositoryImpl
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
        MovieRepositoryImpl(
            get(), get(), get(), get(), get(), get(), get()
        )
    }
    factory<MovieRemoteDataSource> {
        MovieRemoteDataSourceImpl(get())
    }
    factory<MovieLocalDataSource> {
        MovieLocalDataSourceImpl(get(), get(), get())
    }
    factory<MovieDetailRemoteDataSource> {
        MovieDetailRemoteDataSourceImpl(get())
    }
    factory<MovieDetailLocalDataSource> {
        MovieDetailLocalDataSourceImpl(get(), get(), get(), get(), get(), get(), get(), get())
    }
}