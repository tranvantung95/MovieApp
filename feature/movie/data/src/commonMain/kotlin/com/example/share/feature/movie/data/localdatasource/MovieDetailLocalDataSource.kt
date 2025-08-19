package com.example.share.feature.movie.data.localdatasource

import com.example.share.core.database.refence.MovieDetailWithRelations
import com.example.share.feature.movie.data.dto.MovieDetailDTO
import kotlinx.coroutines.flow.Flow

interface MovieDetailLocalDataSource {
   suspend fun  saveMovieDetail(movieDetailDTO: MovieDetailDTO)
   fun getMovieDetail(movieId: Int): Flow<MovieDetailWithRelations?>
   suspend fun getMovieDetailExpiredTime(movieId : Int) : Long
}