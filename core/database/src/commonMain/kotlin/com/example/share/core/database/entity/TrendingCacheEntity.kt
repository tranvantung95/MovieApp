package com.example.share.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Clock

@Entity(tableName = "trending_cache")
data class TrendingCacheEntity(
    @PrimaryKey
    val id: String = "trending_today", // Single row identifier
    val movieIds: String, // Comma-separated movie IDs: "648878,936108,1078605"
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    @ColumnInfo(name = "cached_at")
    val cachedAt: Long = Clock.System.now().epochSeconds
)