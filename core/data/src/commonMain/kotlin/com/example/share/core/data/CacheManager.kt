package com.example.share.core.data

import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
}

class CacheManager {
    private val refreshJobs = mutableMapOf<String, Job>()
    suspend fun <T> refreshIfNeeded(
        key: String,
        strategy: CacheStrategy<T>
    ) {
        refreshJobs[key]?.cancel()
        try {
            coroutineScope {
                refreshJobs[key] = launch {
                    val cacheTime = strategy.getCacheTime()
                    if (strategy.isExpired(cacheTime)) {
                        val freshData = strategy.fetchFromApi()
                        strategy.saveToDatabase(freshData)
                    }
                }
                refreshJobs[key]?.join()
            }
        } catch (e: Exception) {
            println("CacheManager \"Failed to refresh $key\", $e")
            throw e
        } finally {
            refreshJobs.remove(key)
        }
    }

    fun cancelAllRefresh() {
        refreshJobs.values.forEach { it.cancel() }
        refreshJobs.clear()
    }
}