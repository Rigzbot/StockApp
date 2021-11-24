package com.rishik.stockapp.network

import com.rishik.stockapp.BuildConfig
import com.rishik.stockapp.domain.News
import com.rishik.stockapp.domain.Stock
import retrofit2.http.GET

private const val API_KEY = BuildConfig.API_KEY

interface StockService {

    companion object {
        const val BASE_URL = "https://finnhub.io/api/v1/"
    }

    @GET("news?category=general&token=$API_KEY")
    suspend fun getNewsListAsync(): List<News>

    @GET("stock/symbol?exchange=US&token=$API_KEY")
    suspend fun getStockNamesAsync(): List<Stock>
}