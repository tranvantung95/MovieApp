package com.example.share.feature.movie.domain.model

data class Collection(
    val id: Int,
    val name: String,
    val posterPath: String?,
    val backdropPath: String?
)