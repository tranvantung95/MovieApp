package com.example.share.core.data

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

object CacheDuration {
    const val TRENDING_MOVIES = 1 * 60 * 60 * 1000L
    const val MOVIE_DETAIL = 1 * 60 * 60 * 1000L

    @OptIn(ExperimentalTime::class)
    fun isExpired(cacheTime: Long?, duration: Long): Boolean {
        if (cacheTime == null) return true
        return Clock.System.now().epochSeconds - cacheTime > duration
    }
}