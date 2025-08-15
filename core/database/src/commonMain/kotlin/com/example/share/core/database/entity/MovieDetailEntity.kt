package com.example.share.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.share.core.database.EntityModel

@Entity(tableName = "movie_details")
data class MovieDetailEntity(
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
    val budget: Long,
    val revenue: Long,
    val runtime: Int?,
    val status: String,
    val tagline: String?,
    val homepage: String?,
    val imdbId: String?,
    @ColumnInfo(name = "belongs_to_collection_id")
    val belongsToCollectionId: Int?,
    @ColumnInfo(name = "belongs_to_collection_name")
    val belongsToCollectionName: String?,
    @ColumnInfo(name = "belongs_to_collection_poster")
    val belongsToCollectionPoster: String?,
    @ColumnInfo(name = "belongs_to_collection_backdrop")
    val belongsToCollectionBackdrop: String?,
    @ColumnInfo(name = "origin_countries")
    val originCountries: String, // JSON string: ["US", "FI"]
    @ColumnInfo(name = "created_at")
    val createdAt: Long = kotlinx.datetime.Clock.System.now().epochSeconds,
    val lastUpdated: Long
)  : EntityModel