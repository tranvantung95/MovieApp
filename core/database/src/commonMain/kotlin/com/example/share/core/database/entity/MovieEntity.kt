package com.example.share.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.share.core.database.EntityModel


@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val adult: Boolean,
    val originalLanguage: String,
    val video: Boolean,
    @ColumnInfo(name = "genre_ids")
    val genreIds: String, // Stored as comma-separated string: "37,35,80"
    val mediaType: String = "movie",
) : EntityModel