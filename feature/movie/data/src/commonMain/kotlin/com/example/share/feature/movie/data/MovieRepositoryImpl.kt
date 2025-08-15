package com.example.share.feature.movie.data

import com.example.share.core.data.CacheManager
import com.example.share.core.database.dao.MovieDao
import com.example.share.core.database.dao.MovieDetailDao
import com.example.share.core.database.entity.MovieDetailEntity
import com.example.share.core.network.ApiClient
import com.example.share.feature.movie.data.dto.TrendingMoviesResponse
import com.example.share.feature.movie.data.mapper.MovieDTOToDomainModel
import com.example.share.feature.movie.data.mapper.MovieDetailDomainMapper
import com.example.share.feature.movie.data.mapper.MovieEntityMapper
import com.example.share.feature.movie.data.mapper.MovieMapper
import com.example.share.feature.movie.domain.MovieGateway
import com.example.share.feature.movie.domain.model.Movie
import com.example.share.feature.movie.domain.model.MovieDetail
import io.ktor.client.call.body
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val apiClient: ApiClient,
    private val movieDao: MovieDao,
    private val movieDetailDao: MovieDetailDao,
    private val movieMapper: MovieMapper,
    private val trendingMoviesStrategy: TrendingMoviesStrategy,
    private val cacheManager: CacheManager,
    private val movieDomainMapper: MovieDTOToDomainModel,
    private val movieDetailEntityToDomainMapper: MovieDetailDomainMapper,
    private val movieDetailStrategyFactory: MovieDetailStrategyFactory,
) : MovieGateway {
    override suspend fun getTrendingMovies(): Flow<Result<List<Movie>>> {
        return try {
            cacheManager.refreshIfNeeded(
                "getTrendingMovies", strategy = trendingMoviesStrategy
            )
            movieDao.getMovies().filterNotNull().map {
                Result.success(movieMapper.mapToDomainList(it))
            }
        } catch (e: Exception) {
            flow {
                Result.failure<List<Movie>>(e)
            }
        }
    }

    override suspend fun searchMovies(query: String): Flow<Result<List<Movie>>> {
        return try {
            val movie = apiClient.getData("search", "movie", queryParams = mapOf("query" to query))
                .body<TrendingMoviesResponse>()
            flow { Result.success(movieDomainMapper.mapToDomainList(movie.results)) }
        } catch (e: Exception) {
            flow {
                Result.failure<List<Movie>>(e)
            }
        }
    }

    override suspend fun getMovieDetail(movieId: Int): Flow<Result<MovieDetail>> {
        return try {
            val strategy = movieDetailStrategyFactory.create(movieId = movieId)
            cacheManager.refreshIfNeeded("getMovieDetail", strategy)
            movieDetailDao.getMovieDetailWithRelations(movieId = movieId).filterNotNull().map {
                Result.success(movieDetailEntityToDomainMapper.mapToDomain(it))
            }
        } catch (e: Exception) {
            flow { Result.failure<MovieDetail>(e) }
        }


    }
}