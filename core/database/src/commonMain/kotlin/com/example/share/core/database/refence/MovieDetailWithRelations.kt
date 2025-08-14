package com.example.share.core.database.refence

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.share.core.database.entity.GenreEntity
import com.example.share.core.database.entity.MovieDetailEntity
import com.example.share.core.database.entity.ProductionCompanyEntity
import com.example.share.core.database.entity.ProductionCountryEntity
import com.example.share.core.database.entity.SpokenLanguageEntity

data class MovieDetailWithRelations(
    @Embedded val movieDetail: MovieDetailEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "genreId",
        associateBy = Junction(MovieDetailGenreCrossRef::class)
    )
    val genres: List<GenreEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "companyId",
        associateBy = Junction(MovieDetailProductionCompanyCrossRef::class)
    )
    val productionCompanies: List<ProductionCompanyEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "countryIso",
        associateBy = Junction(MovieDetailProductionCountryCrossRef::class)
    )
    val productionCountries: List<ProductionCountryEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "languageIso",
        associateBy = Junction(MovieDetailSpokenLanguageCrossRef::class)
    )
    val spokenLanguages: List<SpokenLanguageEntity>
)