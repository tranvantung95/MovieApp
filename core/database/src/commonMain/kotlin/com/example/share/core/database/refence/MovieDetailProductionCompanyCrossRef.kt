package com.example.share.core.database.refence

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.share.core.database.entity.MovieDetailEntity
import com.example.share.core.database.entity.ProductionCompanyEntity

@Entity(
    tableName = "movie_detail_production_companies",
    primaryKeys = ["movieId", "companyId"],
    foreignKeys = [
        ForeignKey(
            entity = MovieDetailEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductionCompanyEntity::class,
            parentColumns = ["id"],
            childColumns = ["companyId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MovieDetailProductionCompanyCrossRef(
    val movieId: Int,
    val companyId: Int
)