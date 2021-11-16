package com.rishik.stockapp.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rishik.stockapp.database.getNewsDatabase
import com.rishik.stockapp.database.getStockDatabase
import com.rishik.stockapp.repository.Repository
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getNewsDatabase(applicationContext)
        val databaseStocks = getStockDatabase(applicationContext)
        val repository = Repository(database, databaseStocks)

        return try {
            repository.refreshNews()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}