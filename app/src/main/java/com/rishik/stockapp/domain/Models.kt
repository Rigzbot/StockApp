package com.rishik.stockapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Holds simple data classes
 */

@Entity(tableName = "databasenews")
data class News(
    @PrimaryKey
    val url: String,
    val headline: String,
    val datetime: Long,
    val image: String,
    val source: String
)

@Entity(tableName = "databasestocks")
data class Stock(
    @PrimaryKey
    val symbol: String,
    val description: String,
    val time: Long? = null
)

data class StockPrice(
    @SerializedName("c")val currentPrice: Double, //current price
    @SerializedName("d")val priceChange: Double, //change
    @SerializedName("dp")val priceChangePercent: Double, //percent change
)

data class StockDetail(
    val finnhubIndustry: String,
    val logo: String,
    val currency: String,
    val exchange: String
)

data class Recommendation(
    val buy: Int,
    val hold: Int,
    val period: String,
    val sell: Int,
    val strongBuy: String,
    val strongSell: String
)

data class StockNews(
    val datetime: Long,
    val headline: String,
    val image: String,
    val source: String,
    val url: String
)

data class InsiderTransactions(
    val data: List<Insider>
)

//transaction codes : https://www.sec.gov/about/forms/form4data.pdf
data class Insider(
    val name: String,
    val change: Long,
    val transactionDate: String,
    val transactionCode: String,
    val transactionPrice: String
)

/**
 * Stock Metric data class { metric {...}, series { quarterly {[], [], []} }
 */

data class StockMetric(
    val metric: MetricData,
    val series: Type
)

data class MetricData(
    @SerializedName("10DayAverageTradingVolume") val avgTradingVolume: String,
    @SerializedName("52WeekHigh") val high52: String,
    @SerializedName("52WeekLow") val low52: String,
    val bookValuePerShareAnnul: String,
    val currentDividendYieldTTM: String,
    val epsGrowthTTMYoy: String,
    val grossMarginAnnual: String,
    val marketCapitalization: String,
    val pbAnnual: String,
    val netProfitMarginTTM: String,
    val peNormalizedAnnual: String,
    val roiAnnual: String,
)

data class Type(
    val annual: Annual
)

data class Annual(
    val currentRatio: List<Ratio>,
    val ebitPerShare: List<Ratio>,
    val netMargin: List<Ratio>,
    val netDebtToTotalEquity: List<Ratio>,
    val salesPerShare: List<Ratio>
)

data class Ratio(
    val period: String,
    val v: Double
)

