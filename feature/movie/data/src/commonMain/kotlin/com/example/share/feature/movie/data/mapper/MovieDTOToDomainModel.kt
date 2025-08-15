package com.example.share.feature.movie.data.mapper

import com.example.share.core.data.DTOToDomainModel
import com.example.share.feature.movie.data.dto.MovieDTO
import com.example.share.feature.movie.domain.model.Movie

class MovieDTOToDomainModel : DTOToDomainModel<MovieDTO, Movie> {
    override fun mapToDomain(dto: MovieDTO): Movie {
        return Movie(
            id = dto.id,
            title = dto.title,
            originalTitle = dto.originalTitle,
            overview = dto.overview ?: "", // Handle nullable overview
            posterPath = dto.posterPath,
            backdropPath = dto.backdropPath,
            releaseDate = dto.releaseDate ?: "", // Handle nullable release date
            voteAverage = dto.voteAverage,
            voteCount = dto.voteCount,
            popularity = dto.popularity,
            adult = dto.adult,
            originalLanguage = dto.originalLanguage,
            genreIds = dto.genreIds,
            video = dto.video
        )
    }
}