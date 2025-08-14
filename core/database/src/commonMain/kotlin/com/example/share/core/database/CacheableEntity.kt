package com.example.share.core.database

interface CacheableEntity {
    val id: Int
    val lastUpdated: Long
    val isExpired: Boolean
        get() = kotlinx.datetime.Clock.System.now().epochSeconds - lastUpdated > getCacheExpiration()

    fun getCacheExpiration(): Long = 60 * 60 * 1000L
}