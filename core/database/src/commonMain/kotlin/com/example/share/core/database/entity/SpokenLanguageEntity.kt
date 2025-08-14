package com.example.share.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spoken_languages")
data class SpokenLanguageEntity(
    @PrimaryKey
    val iso6391: String,
    val name: String,
    val englishName: String
)