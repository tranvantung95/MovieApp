package com.example.share.feature.movie.data.dto

data class TrendingMoviesResponse(
    val page: Int,
    val results: List<MovieDTO>,
    val totalPages: Int,
    val totalResults: Int
)