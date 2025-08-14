package com.example.movieapp

import com.example.share.core.database.di.coreDatabaseModule
import com.example.share.core.network.networkModule
import com.example.share.feature.movie.data.di.movieDataModule
import com.example.share.feature.movie.domain.di.movieUseCaseModule
import org.koin.dsl.module

object Modules {
    val core = module {
        includes(coreDatabaseModule, networkModule)
    }
    val movieData = movieDataModule

    val movieDomain = movieUseCaseModule
}