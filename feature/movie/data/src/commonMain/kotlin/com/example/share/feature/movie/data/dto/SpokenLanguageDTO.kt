package com.example.share.feature.movie.data.dto

import kotlinx.serialization.SerialName

data class SpokenLanguageDTO(
    @SerialName("iso_639_1")
    val iso6391: String,
    val name: String,
    @SerialName("english_name")
    val englishName: String
) {
}