package com.example.share.feature.movie.data

import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class TrendingMoviesTest {
    private lateinit var testCacheDao: TestTrendingCacheDao
    private lateinit var testStrategy: TestTrendingMoviesStrategy
    private lateinit var testCacheManager: TestCacheManager

    @BeforeTest
    fun setup() {
        testCacheDao = TestTrendingCacheDao()
        testStrategy = TestTrendingMoviesStrategy(testCacheDao)
        testCacheManager = TestCacheManager()
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `when cache is expired - should call API and save data`() = runTest {
        val expiredTime = Clock.System.now().epochSeconds - (2 * 60 * 60 * 1000L)
        testCacheDao.setCacheTime(expiredTime)
        testStrategy.reset()

        testCacheManager.refreshIfNeeded("test", testStrategy)

        assertTrue(testStrategy.apiCalled, "API should be called when cache is expired")
        assertTrue(testStrategy.dataSaved, "Data should be saved when cache is expired")
    }

    @OptIn(ExperimentalTime::class)
    @Test
    fun `when cache is fresh - should NOT call API`() = runTest {
        val freshTime = Clock.System.now().epochSeconds - (30 * 60 * 1000L)
        testCacheDao.setCacheTime(freshTime)
        testStrategy.reset()

        testCacheManager.refreshIfNeeded("test", testStrategy)

        assertTrue(!testStrategy.apiCalled, "API should NOT be called when cache is fresh")
        assertTrue(!testStrategy.dataSaved, "Data should NOT be saved when cache is fresh")
    }

    @Test
    fun `when cache is null - should call API and save data`() = runTest {
        testCacheDao.setCacheTime(null)
        testStrategy.reset()

        testCacheManager.refreshIfNeeded("test", testStrategy)

        assertTrue(testStrategy.apiCalled, "API should be called when cache is null")
        assertTrue(testStrategy.dataSaved, "Data should be saved when cache is null")
    }

}