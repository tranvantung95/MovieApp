package com.example.share.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spoken_languages")
data class SpokenLanguageEntity(
    @PrimaryKey
    @ColumnInfo("iso6391")
    val iso6391: String,
    val name: String,
    val englishName: String
)