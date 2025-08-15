package com.example.share.feature.movie.domain

import com.example.share.feature.movie.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface GetMovieDetailUseCase {
    suspend operator fun invoke(movieId: Int): Flow<Result<MovieDetail>>
}

class GetMovieDetailUseCaseImpl(
    private val movieRepository: MovieGateway
) : GetMovieDetailUseCase {

    override suspend fun invoke(movieId: Int): Flow<Result<MovieDetail>> {
        return movieRepository.getMovieDetail(movieId)
    }
}
