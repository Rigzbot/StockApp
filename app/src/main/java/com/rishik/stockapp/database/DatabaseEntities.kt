package com.rishik.stockapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rishik.stockapp.domain.News
import com.rishik.stockapp.domain.Stocks

@Entity
data class DatabaseNews constructor(
    @PrimaryKey
    val url: String,
    val headline: String,
    val date: Long,
    val imageUrl: String,
    val source: String
)

fun List<DatabaseNews>.asNewsDomainModel(): List<News> {
    return map {
        News(
            url = it.url,
            headline = it.headline,
            date = it.date,
            imageUrl = it.imageUrl,
            source = it.source
        )
    }
}

@Entity
class DatabaseStocks constructor(
    @PrimaryKey
    val symbol: String,
    val description: String
)

fun List<DatabaseStocks>.asStockDomainModel(): List<Stocks> {
    return map {
        Stocks(
            symbol = it.symbol,
            description = it.description
        )
    }
}