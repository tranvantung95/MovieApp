package com.example.share.feature.movie.data.di

import com.example.share.core.data.CacheManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import org.koin.dsl.module

internal val cacheModule = module {
    factory {
        CoroutineScope(Dispatchers.IO + Job())
    }
    factory {
        CacheManager(get())
    }
}