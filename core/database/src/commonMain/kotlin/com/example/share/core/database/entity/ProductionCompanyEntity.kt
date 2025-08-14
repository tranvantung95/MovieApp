package com.example.share.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "production_companies")
data class ProductionCompanyEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val logoPath: String?,
    val originCountry: String
)