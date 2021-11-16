package com.rishik.stockapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * News data base [url, headline, date, imageUrl, source]
 */
@Dao
interface NewsDao {
    @Query("SELECT * FROM databasenews")
    fun getNews(): LiveData<List<DatabaseNews>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg news: DatabaseNews)

    @Query("DELETE FROM databasenews")
    fun deleteAll()
}

@Database(entities = [DatabaseNews::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao
}

private lateinit var NEWS_INSTANCE: NewsDatabase

fun getNewsDatabase(context: Context): NewsDatabase {
    synchronized(NewsDatabase::class.java) {
        if (!::NEWS_INSTANCE.isInitialized) {
            NEWS_INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                NewsDatabase::class.java,
                "news"
            ).build()
        }
    }
    return NEWS_INSTANCE
}

/**
 * Stock names Data base [symbol, description]
 */
@Dao
interface StocksDao {
    @Query("SELECT * FROM databasestocks LIMIT 10")
    fun getStocks(): LiveData<List<DatabaseStocks>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg stocks: DatabaseStocks)

    @Query("DELETE FROM databasestocks")
    fun deleteAll()
}

@Database(entities = [DatabaseStocks::class], version = 1)
abstract class StocksDatabase: RoomDatabase() {
    abstract val stocksDao: StocksDao
}

private lateinit var STOCKS_INSTANCE: StocksDatabase

fun getStockDatabase(context: Context): StocksDatabase {
    synchronized(StocksDatabase::class.java) {
        if (!::STOCKS_INSTANCE.isInitialized) {
            STOCKS_INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                StocksDatabase::class.java,
                "stocks"
            ).build()
        }
    }
    return STOCKS_INSTANCE
}