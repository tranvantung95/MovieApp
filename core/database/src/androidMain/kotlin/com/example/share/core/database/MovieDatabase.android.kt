package com.example.share.core.database

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import org.koin.core.module.Module
import org.koin.dsl.module

@Suppress("KotlinNoActualForExpect")
actual class AppDatabaseConstructor(private val application: Application) :
    RoomDatabaseConstructor<MovieDatabase> {
    actual override fun initialize(): MovieDatabase {
        return getDatabaseBuilder(application).build()
    }


}
actual fun platformModule(): Module {
    return module {
        single<MovieDatabase> {
            val builder = getDatabaseBuilder(context = get())
            getRoomDatabase(builder)
        }
    }
}

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<MovieDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("movie.db")
    return Room.databaseBuilder<MovieDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}