package com.rishik.stockapp.database

import androidx.room.*
import com.rishik.stockapp.domain.News
import com.rishik.stockapp.domain.Stocks
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
    @Query("SELECT * FROM databasestocks LIMIT 10")
    fun getStocks(): Flow<List<Stocks>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stocks: List<Stocks>)

    @Query("DELETE FROM databasestocks")
    suspend fun deleteAll()
}

@Database(entities = [Stocks::class], version = 1)
abstract class StocksDatabase: RoomDatabase() {
    abstract fun stocksDao(): StocksDao
}
