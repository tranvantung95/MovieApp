package com.example.share.core.database

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

@Suppress("KotlinNoActualForExpect")
actual class AppDatabaseConstructor(val application: Application) :
    RoomDatabaseConstructor<MovieDatabase> {
    actual override fun initialize(): MovieDatabase {
        return getDatabaseBuilder(application).build()
    }

    private fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<MovieDatabase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath("movie.db")
        return Room.databaseBuilder<MovieDatabase>(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}
