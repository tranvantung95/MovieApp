package com.example.share.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.share.core.database.entity.GenreEntity

@Dao
interface GenreDao {
    @Query("SELECT * FROM genres")
    suspend fun getAllGenres(): List<GenreEntity>

    @Query("SELECT * FROM genres WHERE id = :genreId")
    suspend fun getGenreById(genreId: Int): GenreEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genre: GenreEntity)
}