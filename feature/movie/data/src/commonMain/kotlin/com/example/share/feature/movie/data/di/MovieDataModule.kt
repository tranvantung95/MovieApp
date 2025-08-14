package com.example.share.feature.movie.data.di

import org.koin.dsl.module

val movieDataModule = module {
    includes(mapperModule, cacheModule)
}