package com.example.share.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.share.core.database.entity.TrendingCacheEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendingCacheDao {
    @Query("SELECT * FROM trending_cache WHERE id = 'trending_today'")
    fun getTrendingCache(): Flow<TrendingCacheEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingCache(cache: TrendingCacheEntity)

    @Query("DELETE FROM trending_cache")
    suspend fun clearTrendingCache()

    // Check if trending cache is still valid (e.g., within 6 hours)
    @Query("SELECT * FROM trending_cache WHERE id = 'trending_today' AND cached_at > :validTimestamp")
    suspend fun getValidTrendingCache(validTimestamp: Long): TrendingCacheEntity?

    @Query("SELECT cached_at FROM trending_cache WHERE id = 'trending_today'")
    suspend fun getTrendingCacheTime(): Long?
}