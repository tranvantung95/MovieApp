package com.example.share.feature.movie.data

import com.example.share.core.data.CacheDuration
import com.example.share.core.data.netWorkBoundResource
import com.example.share.feature.movie.data.localdatasource.MovieDetailLocalDataSource
import com.example.share.feature.movie.data.localdatasource.MovieLocalDataSource
import com.example.share.feature.movie.data.mapper.MovieDTOToDomainModel
import com.example.share.feature.movie.data.mapper.MovieDetailDomainMapper
import com.example.share.feature.movie.data.mapper.MovieMapper
import com.example.share.feature.movie.data.remotedatasource.MovieDetailRemoteDataSource
import com.example.share.feature.movie.data.remotedatasource.MovieRemoteDataSource
import com.example.share.feature.movie.domain.MovieGateway
import com.example.share.feature.movie.domain.model.Movie
import com.example.share.feature.movie.domain.model.MovieDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MovieRepositoryImpl(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieDetailNetworkDataSource: MovieDetailRemoteDataSource,
    private val movieDetailLocalDataSource: MovieDetailLocalDataSource,
    private val movieMapper: MovieMapper,
    private val movieDomainMapper: MovieDTOToDomainModel,
    private val movieDetailEntityToDomainMapper: MovieDetailDomainMapper,
) : MovieGateway {
    override fun getTrendingMovies(): Flow<Result<List<Movie>>> = netWorkBoundResource(query = {
        movieLocalDataSource.getTrendingMovies().map { movieEntity ->
            movieMapper.mapToDomainList(movieEntity)
        }
    }, fetch = {
        movieRemoteDataSource.getTrendingMovies()
    }, saveFetchResult = {
        movieLocalDataSource.saveTrendingMovies(it)
    }, shouldFetch = {
        CacheDuration.isExpired(
            movieLocalDataSource.getTrendingExpiredTime(),
            CacheDuration.TRENDING_MOVIES
        )
    })

    override fun searchMovies(query: String): Flow<Result<List<Movie>>> =
        flow {
            val movie = movieRemoteDataSource.searchMovies(query = query)
            emit(Result.success(movieDomainMapper.mapToDomainList(movie)))
        }.catch {
            emit(Result.failure(it))
        }

    override fun getMovieDetail(movieId: Int): Flow<Result<MovieDetail?>> {
        return netWorkBoundResource(query = {
            movieDetailLocalDataSource.getMovieDetail(movieId = movieId).map { movieWithRelation ->
                movieWithRelation?.let {
                    movieDetailEntityToDomainMapper.mapToDomain(it)
                } ?: run { null }
            }
        }, fetch = {
            movieDetailNetworkDataSource.getMovieDetail(movieId)
        }, saveFetchResult = {
            movieDetailLocalDataSource.saveMovieDetail(it)
        }, shouldFetch = {
            CacheDuration.isExpired(
                movieDetailLocalDataSource.getMovieDetailExpiredTime(movieId = movieId),
                CacheDuration.MOVIE_DETAIL
            )
        })
    }
}
