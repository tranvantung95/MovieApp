package com.example.share.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.share.core.database.entity.SearchCacheEntity


@Dao
interface SearchCacheDao {
    @Query("SELECT * FROM search_cache WHERE searchQuery = :searchQuery")
    suspend fun getSearchCache(searchQuery: String): SearchCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchCache(cache: SearchCacheEntity)

    @Query("DELETE FROM search_cache WHERE searchQuery = :searchQuery")
    suspend fun deleteSearchCache(searchQuery: String)

    @Query("DELETE FROM search_cache WHERE cached_at < :expiredTimestamp")
    suspend fun deleteExpiredSearchCache(expiredTimestamp: Long)

    // Check if search cache is still valid (e.g., within 1 hour)
    @Query("SELECT * FROM search_cache WHERE searchQuery = :searchQuery AND cached_at > :validTimestamp")
    suspend fun getValidSearchCache(searchQuery: String, validTimestamp: Long): SearchCacheEntity?
}