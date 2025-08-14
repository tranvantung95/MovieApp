package com.example.share.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "production_countries")
data class ProductionCountryEntity(
    @PrimaryKey
    @ColumnInfo("iso31661")
    val iso31661: String,
    val name: String
)