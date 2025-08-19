package com.example.share.feature.movie.data.localdatasource

import com.example.share.core.database.dao.MovieDao
import com.example.share.core.database.dao.TrendingCacheDao
import com.example.share.core.database.entity.MovieEntity
import com.example.share.feature.movie.data.dto.MovieDTO
import com.example.share.feature.movie.data.mapper.MovieEntityMapper
import kotlinx.coroutines.flow.Flow

class MovieLocalDataSourceImpl(
    private val movieDao: MovieDao,
    private val trendingCacheDao: TrendingCacheDao,
    private val movieEntityMapper: MovieEntityMapper,
) : MovieLocalDataSource {
    override fun getTrendingMovies(): Flow<List<MovieEntity>> {
        return movieDao.getMovies()
    }

    override suspend fun saveTrendingMovies(movies: List<MovieDTO>) {
        movieDao.insertMovies(movieEntityMapper.mapToEntityList(movies))
    }

    override suspend fun getTrendingExpiredTime(): Long {
        return trendingCacheDao.getTrendingCacheTime() ?: 0
    }

}