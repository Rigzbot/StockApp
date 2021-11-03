package com.rishik.stockapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.rishik.stockapp.database.NewsDatabase
import com.rishik.stockapp.database.asDomainModel
import com.rishik.stockapp.domain.News
import com.rishik.stockapp.network.Network
import com.rishik.stockapp.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

private const val bloomUrl = "https://data.bloomberglp.com/company/sites/2/2019/01/logobbg-wht.png"
private const val marketWatchUrl = "https://mw3.wsj.net/mw5/content/logos/mw_logo_social.png"

class NewsRepository(private val database: NewsDatabase) {

    /**
     * List of news that can be showed on screen
     */
    val news: LiveData<List<News>> = Transformations.map(database.newsDao.getNews()) {
        it.asDomainModel()
    }

    /**
     * Refresh the news stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert operation happens on
     * the IO dispatcher.
     *
     * To actually load the news for use, observe [news]
     */
    suspend fun refreshNews() {
        withContext(Dispatchers.IO) {
            val newsList = Network.newsList.getNewsListAsync().await()
            database.newsDao.insertAll(*newsList.asDatabaseModel())
        }
    }
}