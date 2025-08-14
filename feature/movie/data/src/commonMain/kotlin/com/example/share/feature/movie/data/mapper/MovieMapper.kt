package com.example.share.feature.movie.data.mapper

import com.example.share.core.data.EntityToDomainModel
import com.example.share.core.database.entity.MovieEntity
import com.example.share.feature.movie.domain.model.Movie

class MovieMapper : EntityToDomainModel<MovieEntity, Movie> {
    override fun mapToDomain(entity: MovieEntity) : Movie{
        return Movie(
            id = entity.id,
            title = entity.title,
            originalTitle = entity.originalTitle,
            overview = entity.overview ?: "", // Convert nullable to non-null with default
            posterPath = entity.posterPath,
            backdropPath = entity.backdropPath,
            releaseDate = entity.releaseDate ?: "", // Convert nullable to non-null with default
            voteAverage = entity.voteAverage,
            voteCount = entity.voteCount,
            popularity = entity.popularity,
            adult = entity.adult,
            originalLanguage = entity.originalLanguage,
            genreIds = parseGenreIds(entity.genreIds),
            video = entity.video
        )
    }
    private fun parseGenreIds(genreIdsString: String): List<Int> {
        return if (genreIdsString.isEmpty()) {
            emptyList()
        } else {
            genreIdsString.split(",").mapNotNull { it.trim().toIntOrNull() }
        }
    }
}