package com.rishik.stockapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rishik.stockapp.domain.News

@Entity
data class DatabaseNews constructor(
    @PrimaryKey
    val url: String,
    val headline: String,
    val date: Long,
    val imageUrl: String,
    val source: String
)

fun List<DatabaseNews>.asDomainModel(): List<News> {
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