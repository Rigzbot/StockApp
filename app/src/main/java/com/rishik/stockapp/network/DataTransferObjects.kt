package com.rishik.stockapp.network

import com.rishik.stockapp.database.DatabaseNews
import com.rishik.stockapp.domain.News
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkNews(
    val headline: String,
    val datetime: Long,
    val image: String,
    val source: String,
    val url: String
)

/**
 * Convert Network results to database objects
 */
fun NetworkNews.asDomainModel(): News {
    return News(
            headline = headline,
            date = datetime,
            imageUrl = image,
            source = source,
            url = url
        )
}

fun NetworkNews.asDatabaseModel(): DatabaseNews {
    return DatabaseNews(
            headline = headline,
            date = datetime,
            imageUrl = image,
            source = source,
            url = url
        )
}



