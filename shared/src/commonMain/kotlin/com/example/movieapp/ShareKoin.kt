package com.example.movieapp

import com.example.share.core.database.platformModule
import org.koin.core.context.startKoin

object ShareKoin {
    fun initKoin(){
        startKoin {
            modules(sharedModule + platformModule)
        }
    }
}