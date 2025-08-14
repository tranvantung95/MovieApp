package com.example.share.feature.movie.domain

import com.example.share.feature.movie.domain.model.MovieDetail

interface GetMovieDetailUseCase {
    suspend operator fun invoke(movieId: Int): Result<MovieDetail>
}

class GetMovieDetailUseCaseImpl(
    private val movieRepository: MovieGateway
) : GetMovieDetailUseCase {

    override suspend fun invoke(movieId: Int): Result<MovieDetail> {
        return movieRepository.getMovieDetail(movieId)
    }
}
