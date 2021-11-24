package com.rishik.stockapp.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

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
data class Stocks(
    @PrimaryKey
    val symbol: String,
    val description: String
)