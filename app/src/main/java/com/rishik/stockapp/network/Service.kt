package com.rishik.stockapp.network

import com.rishik.stockapp.BuildConfig
import com.rishik.stockapp.domain.*
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = BuildConfig.API_KEY

interface StockService {

    companion object {
        const val BASE_URL = "https://finnhub.io/api/v1/"
    }

    @GET("news?category=general&token=$API_KEY")
    suspend fun getNewsList(): List<News>

    @GET("stock/symbol?exchange=US&token=$API_KEY")
    suspend fun getStockNames(): List<Stock>

    @GET("/quote?token=$API_KEY")
    suspend fun getCurrentPrice(@Query("symbol") symbol: String): StockPrice

    @GET("/stock/metric?metric=all&token=$API_KEY")
    suspend fun getCompanyFinancials(@Query("symbol") symbol: String): StockMetric

    @GET("/stock/profile2?token=$API_KEY")
    suspend fun getCompanyProfile(@Query("symbol") symbol: String): StockDetail

    @GET("/stock/recommendation?token=$API_KEY")
    suspend fun getRecommendations(@Query("symbol") symbol: String): List<Recommendation>

    @GET("/company-news?token=$API_KEY")
    suspend fun getCompanyNews(
        @Query("symbol") symbol: String,
        @Query("from") from: String,
        @Query("to") to: String
    ): List<StockNews>

    @GET("/stock/insider-transactions?token=$API_KEY&limit=4")
    suspend fun getInsiderTransactions(@Query("symbol") symbol: String): InsiderTransactions
}