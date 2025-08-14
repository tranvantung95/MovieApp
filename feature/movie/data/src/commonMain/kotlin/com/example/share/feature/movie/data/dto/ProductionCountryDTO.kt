package com.example.share.feature.movie.data.dto

import kotlinx.serialization.SerialName

data class ProductionCountryDTO(
    @SerialName("iso_3166_1")
    val iso31661: String,
    val name: String
) {
}