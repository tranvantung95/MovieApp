package com.example.share.core.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@Suppress("KotlinNoActualForExpect")
actual class AppDatabaseConstructor : RoomDatabaseConstructor<MovieDatabase> {
    actual override fun initialize(): MovieDatabase {
       return getDatabaseBuilder().build()
    }
    private fun getDatabaseBuilder(): RoomDatabase.Builder<MovieDatabase> {
        val dbFilePath = documentDirectory() + "/movie.db"
        return Room.databaseBuilder<MovieDatabase>(
            name = dbFilePath,
        )
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        return requireNotNull(documentDirectory?.path)
    }
}