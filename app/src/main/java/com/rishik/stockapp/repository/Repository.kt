package com.rishik.stockapp.repository

import androidx.room.withTransaction
import com.rishik.stockapp.database.NewsDatabase
import com.rishik.stockapp.database.StocksDatabase
import com.rishik.stockapp.network.StockService
import com.rishik.stockapp.util.networkBoundResource
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: StockService,
    private val dbNews: NewsDatabase,
    private val dbStocks: StocksDatabase
) {
    private val newsDao = dbNews.newsDao()
    private val stocksDao = dbStocks.stocksDao()

    fun getStocks() = networkBoundResource(
        query = {
            stocksDao.getStocks()
        },
        fetch = {
            api.getStockNames()
        },
        saveFetchResult = {
            dbStocks.withTransaction {
                stocksDao.deleteAll()
                stocksDao.insertAll(it)
            }
        }
    )

    fun getSmallStocks() = stocksDao.getStocksSmall()

    fun getNews() = networkBoundResource(
        query = {
            newsDao.getNews()
        },
        fetch = {
            api.getNewsList()
        },
        saveFetchResult = {
            //either both will work or none
            dbNews.withTransaction {
                newsDao.deleteAll()
                newsDao.insertAll(it)
            }
        }
    )
}
