package com.example.share.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_cache")
data class SearchCacheEntity(
    @PrimaryKey
    val searchQuery: String,
    val movieIds: String, // Comma-separated movie IDs
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    @ColumnInfo(name = "cached_at")
    val cachedAt: Long = kotlinx.datetime.Clock.System.now().epochSeconds
)