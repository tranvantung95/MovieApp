package com.example.share.core.database.refence

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.share.core.database.entity.MovieDetailEntity
import com.example.share.core.database.entity.ProductionCountryEntity

@Entity(
    tableName = "movie_detail_production_countries",
    primaryKeys = ["movieId", "countryIso"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDetailEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductionCountryEntity::class,
            parentColumns = ["iso31661"],
            childColumns = ["countryIso"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MovieDetailProductionCountryCrossRef(
    val movieId: Int,
    val countryIso: String
)
