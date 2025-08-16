package com.example.share.feature.movie.data

import com.example.share.core.data.CacheManager
import com.example.share.core.database.dao.MovieDao
import com.example.share.core.database.dao.MovieDetailDao
import com.example.share.core.database.entity.MovieDetailEntity
import com.example.share.core.database.refence.MovieDetailWithRelations
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
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
    override fun getTrendingMovies(): Flow<Result<List<Movie>>> = flow<Result<List<Movie>>> {
        cacheManager.refreshIfNeeded(
            "getTrendingMovies", strategy = trendingMoviesStrategy
        )
        val result = movieDao.getMovies().filterNotNull().map {
            Result.success(movieMapper.mapToDomainList(it))
        }
        emitAll(result)
    }.catch {
        emit(Result.failure(it))
    }

    override fun searchMovies(query: String): Flow<Result<List<Movie>>> =
        flow {
            val movie = apiClient.getData("search", "movie", queryParams = mapOf("query" to query))
                .body<TrendingMoviesResponse>()

            emit(Result.success(movieDomainMapper.mapToDomainList(movie.results)))

        }.catch {
            emit(Result.failure(it))
        }


    override fun getMovieDetail(movieId: Int): Flow<Result<MovieDetail?>> {
        return flow {
            val strategy = movieDetailStrategyFactory.create(movieId = movieId)
            cacheManager.refreshIfNeeded("getMovieDetail", strategy)
            emitAll(
                movieDetailDao.getMovieDetailWithRelations(movieId = movieId)
                    .map { data ->
                        Result.success(data?.let { movieDetailEntityToDomainMapper.mapToDomain(it) })
                    }
            )
        }.catch { e ->
            emit(Result.failure(e))
        }
    }
}
