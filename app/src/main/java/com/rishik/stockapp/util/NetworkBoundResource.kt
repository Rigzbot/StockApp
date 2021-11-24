package com.rishik.stockapp.util

import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>, //get value from DB
    crossinline fetch: suspend () -> RequestType, //get value from API
    crossinline saveFetchResult: suspend (RequestType) -> Unit, //save value in DB
    crossinline shouldFetch: (ResultType) -> Boolean = { true }, //check if cached data is stale
) = flow {
    val data = query().first()

    val flow = if(shouldFetch(data)) {
        emit(Resource.Loading(data)) //display cached data while new data is getting fetched

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            query().map { Resource.Error(throwable, it)}
        }
    } else {
        query().map { Resource.Success(it) } //current cached data is latest
    }

    emitAll(flow)
}