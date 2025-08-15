package com.example.share.feature.movie.data

import com.example.share.core.data.CacheStrategy
import com.example.share.feature.movie.data.dto.MovieDetailDTO

interface MovieDetailStrategyFactory {
    fun create(movieId: Int): CacheStrategy<MovieDetailDTO>
}