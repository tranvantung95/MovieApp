package com.example.share.feature.movie.domain.model

import com.example.share.core.domain.DomainModel

data class SpokenLanguage(  val englishName: String,
                            val iso6391: String,
                            val name: String): DomainModel
