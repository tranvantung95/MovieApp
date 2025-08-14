package com.example.share.core.database.refence

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.share.core.database.entity.GenreEntity
import com.example.share.core.database.entity.MovieDetailEntity

@Entity(
    tableName = "movie_detail_genres",
    primaryKeys = ["movieId", "genreId"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDetailEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GenreEntity::class,
            parentColumns = ["id"],
            childColumns = ["genreId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MovieDetailGenreCrossRef(
    val movieId: Int,
    val genreId: Int
)