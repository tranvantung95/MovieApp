package com.example.share.feature.movie.data.dto

data class SearchMoviesResponse(
    val page: Int,
    val results: List<MovieDTO>,
    val totalPages: Int,
    val totalResults: Int
) {
}