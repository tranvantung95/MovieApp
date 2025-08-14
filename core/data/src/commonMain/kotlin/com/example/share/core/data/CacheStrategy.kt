package com.example.share.core.data

interface CacheStrategy<T> {
    suspend fun getCacheTime(): Long?
    suspend fun isExpired(cacheTime: Long?): Boolean
    suspend fun fetchFromApi(): T
    suspend fun saveToDatabase(data: T)
}