package com.rishik.stockapp.database

import androidx.room.*
import com.rishik.stockapp.domain.News
import com.rishik.stockapp.domain.Stock
import kotlinx.coroutines.flow.Flow

/**
 * News data base [url, headline, datetime, image, source]
 */
@Dao
interface NewsDao {
    @Query("SELECT * FROM databasenews")
    fun getNews(): Flow<List<News>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<News>)

    @Query("DELETE FROM databasenews")
    suspend fun deleteAll()
}

@Database(entities = [News::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}

/**
 * Stock names Data base [symbol, description]
 */
@Dao
interface StocksDao {
    @Query("SELECT symbol, description FROM databasestocks")
    fun getStocks(): Flow<List<Stock>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stocks: List<Stock>)

    @Query("DELETE FROM databasestocks")
    suspend fun deleteAll()

    @Query("SELECT symbol, description FROM databasestocks LIMIT 8")
    fun getStocksSmall(): Flow<List<Stock>>

    @Query("UPDATE databasestocks SET time = :currTime where symbol = :symbol")
    suspend fun updateRecent(currTime: Long, symbol: String)

    @Query("SELECT symbol, description FROM databasestocks ORDER BY time LIMIT 8")
    fun getRecent(): Flow<List<Stock>>
}

@Database(entities = [Stock::class], version = 2)
abstract class StocksDatabase: RoomDatabase() {
    abstract fun stocksDao(): StocksDao
}

