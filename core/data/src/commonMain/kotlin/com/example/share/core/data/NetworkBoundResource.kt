package com.example.share.core.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

fun <ResultType, RequestType> netWorkBoundResource(
    query: () -> Flow<ResultType>,
    fetch: suspend () -> RequestType,
    saveFetchResult: suspend (RequestType) -> Unit,
    shouldFetch: suspend (ResultType) -> Boolean = {
        true
    }
): Flow<Result<ResultType>> = flow {
    val initData = query().first()
    if (shouldFetch(initData)) {
        try {
            val restWorkResult = fetch()
            saveFetchResult(restWorkResult)
            query().collect {
                emit(Result.success(it))
            }
        } catch (e: Throwable) {
            emit(Result.failure(e))
        }
    } else {
        query().collect {
            emit(Result.success(it))
        }
    }
}