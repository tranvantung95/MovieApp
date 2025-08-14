package com.example.share.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.share.core.database.refence.MovieDetailGenreCrossRef
import com.example.share.core.database.refence.MovieDetailProductionCompanyCrossRef
import com.example.share.core.database.refence.MovieDetailProductionCountryCrossRef
import com.example.share.core.database.refence.MovieDetailSpokenLanguageCrossRef

@Dao
interface MovieDetailCrossRefDao {
    // Genre relationships
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGenres(crossRefs: List<MovieDetailGenreCrossRef>)

    @Query("DELETE FROM movie_detail_genres WHERE movieId = :movieId")
    suspend fun deleteMovieGenres(movieId: Int)

    // Production Company relationships
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieProductionCompanies(crossRefs: List<MovieDetailProductionCompanyCrossRef>)

    @Query("DELETE FROM movie_detail_production_companies WHERE movieId = :movieId")
    suspend fun deleteMovieProductionCompanies(movieId: Int)

    // Production Country relationships
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieProductionCountries(crossRefs: List<MovieDetailProductionCountryCrossRef>)

    @Query("DELETE FROM movie_detail_production_countries WHERE movieId = :movieId")
    suspend fun deleteMovieProductionCountries(movieId: Int)

    // Spoken Language relationships
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieSpokenLanguages(crossRefs: List<MovieDetailSpokenLanguageCrossRef>)

    @Query("DELETE FROM movie_detail_spoken_languages WHERE movieId = :movieId")
    suspend fun deleteMovieSpokenLanguages(movieId: Int)
}