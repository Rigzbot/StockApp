package com.rishik.stockapp.network

import com.rishik.stockapp.database.DatabaseNews
import com.rishik.stockapp.database.DatabaseStocks
import com.rishik.stockapp.domain.News
import com.rishik.stockapp.domain.Stocks
import com.squareup.moshi.JsonClass

/**
 * Convert Network results to database objects
 */

//News network object
@JsonClass(generateAdapter = true)
data class NetworkNews(
    val headline: String,
    val datetime: Long,
    val image: String,
    val source: String,
    val url: String
)

fun List<NetworkNews>.asNewsDomainModel(): List<News> {
    return map {
        News(
            headline = it.headline,
            date = it.datetime,
            imageUrl = it.image,
            source = it.source,
            url = it.url
        )
    }
}

fun List<NetworkNews>.asNewsDatabaseModel(): Array<DatabaseNews> {
    return map {
        DatabaseNews(
            headline = it.headline,
            date = it.datetime,
            imageUrl = it.image,
            source = it.source,
            url = it.url
        )
    }.toTypedArray()
}

//Stock name network object
@JsonClass(generateAdapter = true)
data class NetworkStocks(
    val symbol: String,
    val description: String
)

fun List<NetworkStocks>.asStocksDomainModel(): List<Stocks> {
    return map {
        Stocks(
            symbol = it.symbol,
            description = it.description
        )
    }
}

fun List<NetworkStocks>.asStocksDatabaseModel(): Array<DatabaseStocks> {
    return map {
        DatabaseStocks(
            symbol = it.symbol,
            description = it.description
        )
    }.toTypedArray()
}







