package com.example.share.core.data

import kotlinx.datetime.Clock

object CacheDuration {
    const val TRENDING_MOVIES = 1 * 60 * 60 * 1000L
    const val MOVIE_DETAIL = 1 * 60 * 60 * 1000L


    fun isExpired(cacheTime: Long?, duration: Long): Boolean {
        if (cacheTime == null) return true
        return Clock.System.now().epochSeconds.times(1000L) - cacheTime > duration
    }
}