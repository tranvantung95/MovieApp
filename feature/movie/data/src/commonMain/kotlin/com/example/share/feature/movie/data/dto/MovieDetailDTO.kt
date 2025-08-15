package com.example.share.feature.movie.data.dto

import com.example.share.core.data.DTOModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailDTO(
    val id: Int,
    val title: String,
    @SerialName("original_title")
    val originalTitle: String,
    val overview: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("backdrop_path")
    val backdropPath: String?,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("vote_average")
    val voteAverage: Double,
    @SerialName("vote_count")
    val voteCount: Int,
    val popularity: Double,
    val adult: Boolean,
    @SerialName("original_language")
    val originalLanguage: String,
    val video: Boolean,
    val budget: Long,
    val revenue: Long,
    val runtime: Int?,
    val status: String,
    val tagline: String?,
    val homepage: String?,
    @SerialName("imdb_id")
    val imdbId: String?,
    @SerialName("belongs_to_collection")
    val belongsToCollection: CollectionDTO?,
    @SerialName("origin_country")
    val originCountry: List<String>,
    val genres: List<GenreDTO>,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyDTO>,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountryDTO>,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguageDTO>
) : DTOModel