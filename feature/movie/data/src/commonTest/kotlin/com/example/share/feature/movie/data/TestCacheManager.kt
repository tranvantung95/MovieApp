package com.example.share.feature.movie.data

import com.example.share.core.data.CacheManager
import com.example.share.core.data.CacheStrategy

class TestCacheManager : CacheManager() {
    override suspend fun <T> refreshIfNeeded(key: String, strategy: CacheStrategy<T>) {
        val cacheTime = strategy.getCacheTime()
        if (strategy.isExpired(cacheTime)) {
            val freshData = strategy.fetchFromApi()
            strategy.saveToDatabase(freshData)
        }
    }
}