package com.example.share.core.database.di

import com.example.share.core.database.AppDatabaseConstructor
import com.example.share.core.database.MovieDatabase
import com.example.share.core.database.dao.GenreDao
import com.example.share.core.database.dao.MovieDao
import com.example.share.core.database.dao.MovieDetailCrossRefDao
import com.example.share.core.database.dao.MovieDetailDao
import com.example.share.core.database.dao.ProductionCompanyDao
import com.example.share.core.database.dao.ProductionCountryDao
import com.example.share.core.database.dao.SearchCacheDao
import com.example.share.core.database.dao.SpokenLanguageDao
import com.example.share.core.database.dao.TrendingCacheDao
import com.example.share.core.database.platformModule
import org.koin.dsl.module

 internal val databaseModule = module {
    single<MovieDatabase> {
        val constructor = get<AppDatabaseConstructor>()
        constructor.initialize()
    }
    single<MovieDao> { get<MovieDatabase>().movieDao() }
    single<MovieDetailDao> { get<MovieDatabase>().movieDetailDao() }
    single<GenreDao> { get<MovieDatabase>().genreDao() }
    single<ProductionCompanyDao> { get<MovieDatabase>().productionCompanyDao() }
    single<ProductionCountryDao> { get<MovieDatabase>().productionCountryDao() }
    single<SpokenLanguageDao> { get<MovieDatabase>().spokenLanguageDao() }
    single<MovieDetailCrossRefDao> { get<MovieDatabase>().movieDetailCrossRefDao() }
    single<TrendingCacheDao> { get<MovieDatabase>().trendingCacheDao() }
    single<SearchCacheDao> { get<MovieDatabase>().searchCacheDao() }
}
val coreDatabaseModule = module {
    includes(databaseModule, platformModule())
}