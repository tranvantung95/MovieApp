package com.example.share.feature.movie.data.dto

import com.example.share.core.data.DTOModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDTO(
    val id: Int,
    val title: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    val popularity: Double,
    val adult: Boolean,
    @SerialName("original_language")
    val originalLanguage: String,
    val video: Boolean,
    @SerialName("genre_ids")
    val genreIds: List<Int>,
    @SerialName("media_type")
    val mediaType: String? = null
) : DTOModel