package com.example.share.feature.movie.data

import com.example.share.core.database.dao.TrendingCacheDao
import com.example.share.core.database.entity.TrendingCacheEntity
import kotlinx.coroutines.flow.Flow

class TestTrendingCacheDao(private var cacheTime: Long? = null) : TrendingCacheDao {
    override suspend fun getTrendingCacheTime(): Long? = cacheTime
    override fun getTrendingCache(): Flow<TrendingCacheEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun insertTrendingCache(cache: TrendingCacheEntity) {}
    override suspend fun clearTrendingCache() {
        TODO("Not yet implemented")
    }

    override suspend fun getValidTrendingCache(validTimestamp: Long): TrendingCacheEntity? {
        TODO("Not yet implemented")
    }

    fun setCacheTime(time: Long?) { cacheTime = time }
}