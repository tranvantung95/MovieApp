package com.example.share.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.share.core.database.dao.GenreDao
import com.example.share.core.database.dao.MovieDao
import com.example.share.core.database.dao.MovieDetailCrossRefDao
import com.example.share.core.database.dao.MovieDetailDao
import com.example.share.core.database.dao.ProductionCompanyDao
import com.example.share.core.database.dao.ProductionCountryDao
import com.example.share.core.database.dao.SearchCacheDao
import com.example.share.core.database.dao.SpokenLanguageDao
import com.example.share.core.database.dao.TrendingCacheDao
import com.example.share.core.database.entity.GenreEntity
import com.example.share.core.database.entity.MovieDetailEntity
import com.example.share.core.database.entity.MovieEntity
import com.example.share.core.database.entity.ProductionCompanyEntity
import com.example.share.core.database.entity.ProductionCountryEntity
import com.example.share.core.database.entity.SearchCacheEntity
import com.example.share.core.database.entity.SpokenLanguageEntity
import com.example.share.core.database.entity.TrendingCacheEntity
import com.example.share.core.database.refence.MovieDetailGenreCrossRef
import com.example.share.core.database.refence.MovieDetailProductionCompanyCrossRef
import com.example.share.core.database.refence.MovieDetailProductionCountryCrossRef
import com.example.share.core.database.refence.MovieDetailSpokenLanguageCrossRef
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module

@Database(
    entities = [
        MovieEntity::class,
        MovieDetailEntity::class,
        GenreEntity::class,
        ProductionCompanyEntity::class,
        ProductionCountryEntity::class,
        SpokenLanguageEntity::class,
        TrendingCacheEntity::class,
        SearchCacheEntity::class,
        MovieDetailGenreCrossRef::class,
        MovieDetailProductionCompanyCrossRef::class,
        MovieDetailProductionCountryCrossRef::class,
        MovieDetailSpokenLanguageCrossRef::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieDetailDao(): MovieDetailDao
    abstract fun genreDao(): GenreDao
    abstract fun productionCompanyDao(): ProductionCompanyDao
    abstract fun productionCountryDao(): ProductionCountryDao
    abstract fun spokenLanguageDao(): SpokenLanguageDao
    abstract fun movieDetailCrossRefDao(): MovieDetailCrossRefDao
    abstract fun trendingCacheDao(): TrendingCacheDao
    abstract fun searchCacheDao(): SearchCacheDao
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<MovieDatabase> {
    override fun initialize(): MovieDatabase

}
expect fun platformModule(): Module

fun getRoomDatabase(
    builder: RoomDatabase.Builder<MovieDatabase>
): MovieDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}