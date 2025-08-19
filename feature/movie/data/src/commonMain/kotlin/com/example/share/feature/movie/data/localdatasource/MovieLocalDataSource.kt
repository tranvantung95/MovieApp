package com.example.share.feature.movie.data.localdatasource

import com.example.share.core.database.entity.MovieEntity
import com.example.share.feature.movie.data.dto.MovieDTO
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    fun getTrendingMovies(): Flow<List<MovieEntity>>
    suspend fun saveTrendingMovies(movies: List<MovieDTO>)
    suspend fun getTrendingExpiredTime(): Long
}