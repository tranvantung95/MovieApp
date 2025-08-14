package com.example.share.core.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
}
class CacheManager(private val scope: CoroutineScope) {
    private val refreshJobs = mutableMapOf<String, Job>()

    fun <T> refreshIfNeeded(
        key: String,
        strategy: CacheStrategy<T>
    ) {
        refreshJobs[key]?.cancel()
        refreshJobs[key] = scope.launch {
            try {
                val cacheTime = strategy.getCacheTime()
                if (strategy.isExpired(cacheTime)) {
                    val freshData = strategy.fetchFromApi()
                    strategy.saveToDatabase(freshData)
                }
            } catch (e: Exception) {
                println("CacheManager \"Failed to refresh $key\", $e")
                throw e
            } finally {
                refreshJobs.remove(key)
            }
        }
    }

    fun cancelAllRefresh() {
        refreshJobs.values.forEach { it.cancel() }
        refreshJobs.clear()
    }
}