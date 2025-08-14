package com.example.movieapp.android

import android.app.Application
import com.example.movieapp.platformModule
import com.example.movieapp.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MovieApplication)
            modules(sharedModule + platformModule + viewModelModule)
        }
    }
}