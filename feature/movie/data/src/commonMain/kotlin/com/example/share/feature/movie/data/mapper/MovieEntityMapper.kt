package com.example.share.feature.movie.data.mapper

import com.example.share.core.data.DTOToEntityModel
import com.example.share.core.database.entity.MovieEntity
import com.example.share.feature.movie.data.dto.MovieDTO

class MovieEntityMapper : DTOToEntityModel<MovieDTO, MovieEntity> {
    override fun mapToEntity(dto: MovieDTO): MovieEntity {
        return MovieEntity(
            id = dto.id,
            title = dto.title,
            originalTitle = dto.originalTitle,
            overview = dto.overview,
            posterPath = dto.posterPath,
            backdropPath = dto.backdropPath,
            releaseDate = dto.releaseDate,
            voteAverage = dto.voteAverage,
            voteCount = dto.voteCount,
            popularity = dto.popularity,
            adult = dto.adult,
            originalLanguage = dto.originalLanguage,
            video = dto.video,
            genreIds = dto.genreIds.joinToString(","),
            mediaType = dto.mediaType ?: "movie"
        )
    }
}