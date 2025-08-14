package com.example.share.core.database.refence

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.share.core.database.entity.MovieDetailEntity
import com.example.share.core.database.entity.SpokenLanguageEntity

@Entity(
    tableName = "movie_detail_spoken_languages",
    primaryKeys = ["movieId", "languageIso"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDetailEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = SpokenLanguageEntity::class,
            parentColumns = ["iso6391"],
            childColumns = ["languageIso"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MovieDetailSpokenLanguageCrossRef(
    val movieId: Int,
    val languageIso: String
)