package com.example.share.feature.movie.data.dto

import kotlinx.serialization.SerialName

data class CollectionDTO(
    val id: Int,
    val name: String,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("backdrop_path")
    val backdropPath: String?
)