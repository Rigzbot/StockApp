package com.rishik.stockapp.network

import com.rishik.stockapp.database.DatabaseNews
import com.rishik.stockapp.domain.News
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
fun List<NetworkNews>.asDomainModel(): List<News> {
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

fun List<NetworkNews>.asDatabaseModel(): Array<DatabaseNews> {
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



