package com.rishik.stockapp.di

import android.app.Application
import androidx.room.Room
import com.rishik.stockapp.database.NewsDatabase
import com.rishik.stockapp.database.StocksDatabase
import com.rishik.stockapp.network.StockService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(StockService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): StockService =
        retrofit.create(StockService :: class.java)

    @Provides
    @Singleton
    fun provideNewsDatabase(app: Application): NewsDatabase =
        Room.databaseBuilder(app, NewsDatabase::class.java, "news_database")
            .build()

    @Provides
    @Singleton
    fun provideStocksDatabase(app: Application): StocksDatabase =
        Room.databaseBuilder(app, StocksDatabase::class.java, "stocks_database")
            .fallbackToDestructiveMigration()
            .build()
}