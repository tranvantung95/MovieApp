package com.example.share.feature.movie.data

import com.example.share.core.data.CacheManager
import com.example.share.core.database.MovieDatabase
import com.example.share.core.network.ApiClient
import com.example.share.feature.movie.data.mapper.MovieEntityMapper
import com.example.share.feature.movie.data.mapper.MovieMapper
import com.example.share.feature.movie.domain.MovieGateway
import com.example.share.feature.movie.domain.model.Movie
import com.example.share.feature.movie.domain.model.MovieDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val movieDatabase: MovieDatabase,
    private val apiClient: ApiClient,
    private val scope: CoroutineScope,
    private val movieEntityMapper: MovieEntityMapper,
    private val movieMapper: MovieMapper
) : MovieGateway {
    private val cacheManager = CacheManager(scope = scope)

    override suspend fun getTrendingMovies(): Flow<Result<List<Movie>>> {
        return try {
            cacheManager.refreshIfNeeded(
                "getTrendingMovies", strategy = TrendingMoviesStrategy(
                    database = movieDatabase,
                    apiClient = apiClient,
                    movieEntityMapper = movieEntityMapper
                )
            )
            movieDatabase.movieDao().getMovies().filterNotNull().map {
                Result.success(movieMapper.mapToDomainList(it))
            }
        } catch (e: Exception) {
            flow { Result.failure<List<Movie>>(e) }
        }
    }

    override suspend fun searchMovies(query: String): Flow<Result<List<Movie>>> {
        return flowOf()
    }

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        return Result.failure(IllegalStateException("not implement"))
    }
}