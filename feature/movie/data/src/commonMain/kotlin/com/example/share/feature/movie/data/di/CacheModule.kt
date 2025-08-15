package com.example.share.feature.movie.data.di

import com.example.share.core.data.CacheManager
import org.koin.dsl.module

internal val cacheModule = module {
    factory {
        CacheManager()
    }
}