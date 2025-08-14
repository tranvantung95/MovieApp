package com.example.share.core.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@Suppress("KotlinNoActualForExpect")
actual class AppDatabaseConstructor : RoomDatabaseConstructor<MovieDatabase> {
    actual override fun initialize(): MovieDatabase {
        return getDatabaseBuilder().build()
    }
}

fun getDatabaseBuilder(): RoomDatabase.Builder<MovieDatabase> {
    val dbFilePath = documentDirectory() + "/movie.db"
    return Room.databaseBuilder<MovieDatabase>(
        name = dbFilePath,
    )
}

@OptIn(ExperimentalForeignApi::class)
fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )
    return requireNotNull(documentDirectory?.path)
}

actual fun platformModule(): Module {
    return module {
        single<MovieDatabase> {
            val builder = getDatabaseBuilder()
            getRoomDatabase(builder)
        }
    }
}