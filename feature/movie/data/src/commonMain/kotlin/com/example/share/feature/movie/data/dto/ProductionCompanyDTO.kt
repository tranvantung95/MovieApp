package com.example.share.feature.movie.data.dto

import kotlinx.serialization.SerialName

data class ProductionCompanyDTO(
    val id: Int,
    val name: String,
    @SerialName("logo_path")
    val logoPath: String?,
    @SerialName("origin_country")
    val originCountry: String
) {
}