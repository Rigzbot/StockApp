package com.rishik.stockapp.domain

/**
 * Holds simple data classes
 */

data class News(
    val headline: String,
    val date: Long,
    val imageUrl: String,
    val source: String,
    val url: String
)

data class Stocks(
    val symbol: String,
    val description: String
)