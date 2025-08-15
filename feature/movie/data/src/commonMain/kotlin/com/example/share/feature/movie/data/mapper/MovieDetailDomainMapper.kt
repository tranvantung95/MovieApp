package com.example.share.feature.movie.data.mapper

import com.example.share.core.data.EntityToDomainModel
import com.example.share.core.database.refence.MovieDetailWithRelations
import com.example.share.feature.movie.domain.model.Collection
import com.example.share.feature.movie.domain.model.Genre
import com.example.share.feature.movie.domain.model.MovieDetail
import com.example.share.feature.movie.domain.model.ProductionCompany
import com.example.share.feature.movie.domain.model.ProductionCountry
import com.example.share.feature.movie.domain.model.SpokenLanguage
import kotlinx.serialization.json.Json

class MovieDetailDomainMapper : EntityToDomainModel<MovieDetailWithRelations, MovieDetail> {
    private val json = Json { ignoreUnknownKeys = true }
    override fun mapToDomain(entity: MovieDetailWithRelations): MovieDetail {
        val movieDetail = entity.movieDetail
        val belongsToCollection = if (movieDetail.belongsToCollectionId != null) {
            Collection(
                id = movieDetail.belongsToCollectionId ?: 0,
                name = movieDetail.belongsToCollectionName ?: "",
                posterPath = movieDetail.belongsToCollectionPoster,
                backdropPath = movieDetail.belongsToCollectionBackdrop
            )
        } else null

        val originCountries = try {
            json.decodeFromString<List<String>>(movieDetail.originCountries)
        } catch (e: Exception) {
            emptyList()
        }

        return MovieDetail(
            adult = movieDetail.adult,
            backdropPath = movieDetail.backdropPath,
            belongsToCollection = belongsToCollection,
            budget = movieDetail.budget,
            genres = entity.genres.map { Genre(id = it.id, name = it.name) },
            homepage = movieDetail.homepage,
            id = movieDetail.id,
            imdbId = movieDetail.imdbId,
            originCountry = originCountries,
            originalLanguage = movieDetail.originalLanguage,
            originalTitle = movieDetail.originalTitle,
            overview = movieDetail.overview.orEmpty(),
            popularity = movieDetail.popularity,
            posterPath = movieDetail.posterPath,
            productionCompanies = entity.productionCompanies.map {
                ProductionCompany(
                    id = it.id,
                    logoPath = it.logoPath,
                    name = it.name,
                    originCountry = it.originCountry
                )
            },
            productionCountries = entity.productionCountries.map {
                ProductionCountry(
                    iso31661 = it.iso31661,
                    name = it.name
                )
            },
            releaseDate = movieDetail.releaseDate.orEmpty(),
            revenue = movieDetail.revenue,
            runtime = movieDetail.runtime ?: 0,
            spokenLanguages = entity.spokenLanguages.map {
                SpokenLanguage(
                    englishName = it.englishName,
                    iso6391 = it.iso6391,
                    name = it.name
                )
            },
            status = movieDetail.status,
            tagline = movieDetail.tagline,
            title = movieDetail.title,
            video = movieDetail.video,
            voteAverage = movieDetail.voteAverage,
            voteCount = movieDetail.voteCount
        )
    }
}