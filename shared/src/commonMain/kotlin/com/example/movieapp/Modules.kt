package com.example.movieapp

import com.example.share.core.database.di.coreDatabaseModule
import com.example.share.core.network.networkModule
import com.example.share.feature.movie.data.di.movieDataModule
import com.example.share.feature.movie.domain.di.movieUseCaseModule
import org.koin.core.module.Module


val sharedModule = listOf(coreDatabaseModule, networkModule, movieDataModule, movieUseCaseModule)

expect val platformModule: Module
