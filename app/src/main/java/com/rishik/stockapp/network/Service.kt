package com.rishik.stockapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.rishik.stockapp.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://finnhub.io/api/v1/"
private const val API_KEY = BuildConfig.API_KEY

interface StockService {
    @GET("news?category=general&token=$API_KEY")
    fun getNewsListAsync(): Deferred<List<NetworkNews>>

    @GET("stock/symbol?exchange=US&token=$API_KEY")
    fun getStockNamesAsync(): Deferred<List<NetworkStocks>>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object Network {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val stockService: StockService = retrofit.create(StockService::class.java)
}
