package com.example.share.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class MovieDatabase : RoomDatabase()  {

}
@Suppress("KotlinNoActualForExpect")
expect class AppDatabaseConstructor : RoomDatabaseConstructor<MovieDatabase> {
    override fun initialize(): MovieDatabase

}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<MovieDatabase>
): MovieDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}