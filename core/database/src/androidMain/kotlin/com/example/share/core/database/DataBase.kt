package com.example.share.core.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<MovieDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("movie.db")
    return Room.databaseBuilder<MovieDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}
actual fun platformModule(): Module {
    return module {
        single<MovieDatabase> {
            val builder = getDatabaseBuilder(context = get())
            getRoomDatabase(builder)
        }
    }
}

