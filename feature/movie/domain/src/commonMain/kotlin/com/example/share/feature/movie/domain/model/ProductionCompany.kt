package com.example.share.feature.movie.domain.model

import com.example.share.core.domain.DomainModel

data class ProductionCompany(
    val id: Int,
    val logoPath: String?,
    val name: String,
    val originCountry: String) : DomainModel

