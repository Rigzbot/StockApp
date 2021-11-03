package com.rishik.stockapp.domain

/**
 * Basic News Object
 */
data class News(
    val headline: String,
    val date: Long,
    val imageUrl: String,
    val source: String,
    val url: String
)