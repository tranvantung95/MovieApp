package com.example.share.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class GenreEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    val name: String
)