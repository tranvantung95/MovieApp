package com.example.movieapp

import com.example.share.feature.movie.domain.GetMoviesUseCaseIos
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform

object ShareKoin {
    fun initKoin() {
        startKoin {
            modules(sharedModule + platformModule)
        }
    }

}

object ShareUseCase {
    fun getGetMoviesUseCaseIos(): GetMoviesUseCaseIos {
        return KoinPlatform.getKoin().get()
    }
}