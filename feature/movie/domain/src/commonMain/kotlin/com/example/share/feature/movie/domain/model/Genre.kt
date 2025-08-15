package com.example.share.feature.movie.domain.model

import com.example.share.core.domain.DomainModel

data class Genre(
    val id: Int,
    val name: String
) : DomainModel