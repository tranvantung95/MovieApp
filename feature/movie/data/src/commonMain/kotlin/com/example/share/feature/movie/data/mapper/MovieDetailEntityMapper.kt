package com.example.share.feature.movie.data.mapper

import com.example.share.core.data.json
import com.example.share.core.database.entity.GenreEntity
import com.example.share.core.database.entity.MovieDetailEntity
import com.example.share.core.database.entity.ProductionCompanyEntity
import com.example.share.core.database.entity.ProductionCountryEntity
import com.example.share.core.database.entity.SpokenLanguageEntity
import com.example.share.feature.movie.data.dto.MovieDetailDTO
import kotlinx.datetime.Clock

class MovieDetailEntityMapper {
    fun apiMovieDetailToEntities(apiDetail: MovieDetailDTO): MovieDetailEntities {
        val movieDetail = MovieDetailEntity(
            id = apiDetail.id,
            title = apiDetail.title,
            originalTitle = apiDetail.originalTitle,
            overview = apiDetail.overview,
            posterPath = apiDetail.posterPath,
            backdropPath = apiDetail.backdropPath,
            releaseDate = apiDetail.releaseDate,
            voteAverage = apiDetail.voteAverage,
            voteCount = apiDetail.voteCount,
            popularity = apiDetail.popularity,
            adult = apiDetail.adult,
            originalLanguage = apiDetail.originalLanguage,
            video = apiDetail.video,
            budget = apiDetail.budget,
            revenue = apiDetail.revenue,
            runtime = apiDetail.runtime,
            status = apiDetail.status,
            tagline = apiDetail.tagline,
            homepage = apiDetail.homepage,
            imdbId = apiDetail.imdbId,
            belongsToCollectionId = apiDetail.belongsToCollection?.id,
            belongsToCollectionName = apiDetail.belongsToCollection?.name,
            belongsToCollectionPoster = apiDetail.belongsToCollection?.posterPath,
            belongsToCollectionBackdrop = apiDetail.belongsToCollection?.backdropPath,
            originCountries = json.encodeToString(apiDetail.originCountry),
            lastUpdated = Clock.System.now().epochSeconds
        )

        val genres = apiDetail.genres.map { GenreEntity(it.id, it.name) }
        val productionCompanies = apiDetail.productionCompanies.map {
            ProductionCompanyEntity(it.id, it.name, it.logoPath, it.originCountry)
        }
        val productionCountries = apiDetail.productionCountries.map {
            ProductionCountryEntity(it.iso31661, it.name)
        }
        val spokenLanguages = apiDetail.spokenLanguages.map {
            SpokenLanguageEntity(it.iso6391, it.name, it.englishName)
        }

        return MovieDetailEntities(
            movieDetail = movieDetail,
            genres = genres,
            productionCompanies = productionCompanies,
            productionCountries = productionCountries,
            spokenLanguages = spokenLanguages
        )
    }
}

data class MovieDetailEntities(
    val movieDetail: MovieDetailEntity,
    val genres: List<GenreEntity>,
    val productionCompanies: List<ProductionCompanyEntity>,
    val productionCountries: List<ProductionCountryEntity>,
    val spokenLanguages: List<SpokenLanguageEntity>
)