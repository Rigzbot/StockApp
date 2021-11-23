package com.rishik.stockapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.rishik.stockapp.database.*
import com.rishik.stockapp.domain.News
import com.rishik.stockapp.domain.Stocks
import com.rishik.stockapp.network.Network
import com.rishik.stockapp.network.asNewsDatabaseModel
import com.rishik.stockapp.network.asStocksDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Repository(private val database: NewsDatabase, private val databaseStocks: StocksDatabase) {

    /**
     * List of news that can be showed on screen
     */
    val news: LiveData<List<News>> = Transformations.map(database.newsDao.getNews()) {
        it.asNewsDomainModel()
    }

    val stocks: LiveData<List<Stocks>> = Transformations.map(databaseStocks.stocksDao.getStocks()) {
        it.asStockDomainModel()
    }

    /**
     * Refresh the news stored in the offline cache, delete old news.
     *
     * This function uses the IO dispatcher to ensure the database insert operation happens on
     * the IO dispatcher.
     *
     * To actually load the news for use, observe [news]
     */
    suspend fun refreshNews() {
        try {
            withContext(Dispatchers.IO) {
                val newsList = Network.stockService.getNewsListAsync().await()
                database.newsDao.deleteAll()
                database.newsDao.insertAll(*newsList.asNewsDatabaseModel())
            }
        } catch (e: Exception) {
            Log.d("NetworkHelper", "Error is: ${e.localizedMessage}")
        }
    }

    suspend fun refreshStocks() {
        try {
            withContext(Dispatchers.IO) {
                val stocksList = Network.stockService.getStockNamesAsync().await()
                databaseStocks.stocksDao.deleteAll()
                databaseStocks.stocksDao.insertAll(*stocksList.asStocksDatabaseModel())
            }
        } catch (e: Exception) {
            Log.d("NetworkHelper", "Error is: ${e.localizedMessage}")
        }
    }
}
