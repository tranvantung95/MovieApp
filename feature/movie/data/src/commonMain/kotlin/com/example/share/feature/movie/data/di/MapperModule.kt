package com.example.share.feature.movie.data.di

import com.example.share.feature.movie.data.mapper.MovieDetailEntityMapper
import com.example.share.feature.movie.data.mapper.MovieEntityMapper
import com.example.share.feature.movie.data.mapper.MovieMapper
import org.koin.dsl.module

internal val mapperModule = module {
    factory {
        MovieMapper()
    }
    factory {
        MovieDetailEntityMapper()
    }
    factory {
        MovieEntityMapper()
    }
}
