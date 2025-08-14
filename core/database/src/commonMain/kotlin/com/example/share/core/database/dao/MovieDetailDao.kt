package com.example.share.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.share.core.database.entity.MovieDetailEntity
import com.example.share.core.database.refence.MovieDetailWithRelations
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDetailDao {
    @Query("SELECT * FROM movie_details WHERE id = :movieId")
    suspend fun getMovieDetailById(movieId: Int): Flow<MovieDetailEntity?>

    @Transaction
    @Query("SELECT * FROM movie_details WHERE id = :movieId")
    suspend fun getMovieDetailWithRelations(movieId: Int): MovieDetailWithRelations?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieDetail(movieDetail: MovieDetailEntity)

    @Query("DELETE FROM movie_details WHERE id = :movieId")
    suspend fun deleteMovieDetailById(movieId: Int)

    @Query("SELECT * FROM movie_details WHERE id = :movieId AND created_at > :validTimestamp")
    suspend fun getCachedMovieDetail(movieId: Int, validTimestamp: Long): MovieDetailEntity?

    @Query("SELECT created_at FROM movie_details WHERE id = :movieId")
    suspend fun getMovieDetailCacheTime(movieId: Int): Long?
}