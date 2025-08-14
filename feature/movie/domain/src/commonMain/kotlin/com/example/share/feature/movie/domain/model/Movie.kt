package com.example.share.feature.movie.domain.model

import com.example.share.core.domain.DomainModel

data class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val adult: Boolean,
    val originalLanguage: String,
    val genreIds: List<Int>,
    val video: Boolean
) : DomainModel