package com.example.share.feature.movie.data

import com.example.share.core.data.CacheStrategy
import com.example.share.core.database.DatabaseTransactionExecutor
import com.example.share.core.database.dao.GenreDao
import com.example.share.core.database.dao.MovieDetailCrossRefDao
import com.example.share.core.database.dao.MovieDetailDao
import com.example.share.core.database.dao.ProductionCompanyDao
import com.example.share.core.database.dao.ProductionCountryDao
import com.example.share.core.database.dao.SpokenLanguageDao
import com.example.share.core.network.ApiClient
import com.example.share.feature.movie.data.dto.MovieDetailDTO
import com.example.share.feature.movie.data.mapper.MovieDetailEntityMapper

class MovieDetailStrategyFactoryImpl(
    private val apiClient: ApiClient,
    private val movieDetailEntityMapper: MovieDetailEntityMapper,
    private val movieDetailDao: MovieDetailDao,
    private val genreDao: GenreDao,
    private val productionCompanyDao: ProductionCompanyDao,
    private val productionCountryDao: ProductionCountryDao,
    private val spokenLanguageDao: SpokenLanguageDao,
    private val movieDetailCrossRefDao: MovieDetailCrossRefDao,
    private val transaction: DatabaseTransactionExecutor
) : MovieDetailStrategyFactory {
    override fun create(movieId: Int): CacheStrategy<MovieDetailDTO> {
       return MovieDetailStrategy(
          movieId = movieId,
          apiClient = apiClient,
          movieDetailEntityMapper = movieDetailEntityMapper,
          transaction = transaction,
          movieDetailDao = movieDetailDao,
          genreDao = genreDao,
          productionCompanyDao = productionCompanyDao,
          productionCountryDao = productionCountryDao,
          spokenLanguageDao = spokenLanguageDao,
          movieDetailCrossRefDao = movieDetailCrossRefDao
      )
    }
}