package com.example.share.core.database.refence

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.example.share.core.database.EntityModel
import com.example.share.core.database.entity.GenreEntity
import com.example.share.core.database.entity.MovieDetailEntity
import com.example.share.core.database.entity.ProductionCompanyEntity
import com.example.share.core.database.entity.ProductionCountryEntity
import com.example.share.core.database.entity.SpokenLanguageEntity

data class MovieDetailWithRelations(
    @Embedded val movieDetail: MovieDetailEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id", // This refers to the junction table column
        associateBy = Junction(
            MovieDetailGenreCrossRef::class,
            parentColumn = "movieId",    // Junction column that matches parent
            entityColumn = "genreId"     // Junction column that matches child entity
        )
    )
    val genres: List<GenreEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id", // This refers to the junction table column
        associateBy = Junction(
            MovieDetailProductionCompanyCrossRef::class,
            parentColumn = "movieId",
            entityColumn = "companyId"
        )
    )
    val productionCompanies: List<ProductionCompanyEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "iso31661", // This refers to the junction table column
        associateBy = Junction(
            MovieDetailProductionCountryCrossRef::class,
            parentColumn = "movieId",
            entityColumn = "countryIso"
        )
    )
    val productionCountries: List<ProductionCountryEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "iso6391", // This refers to the junction table column
        associateBy = Junction(
            MovieDetailSpokenLanguageCrossRef::class,
            parentColumn = "movieId",
            entityColumn = "languageIso"
        )
    )
    val spokenLanguages: List<SpokenLanguageEntity>
) : EntityModel