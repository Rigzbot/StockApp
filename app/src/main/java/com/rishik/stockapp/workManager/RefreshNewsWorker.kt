package com.rishik.stockapp.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.rishik.stockapp.database.getDatabase
import com.rishik.stockapp.repository.NewsRepository
import retrofit2.HttpException

class RefreshNewsWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {
        companion object {
            const val WORK_NAME = "RefreshNewsWorker"
        }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = NewsRepository(database)

        return try {
            repository.refreshNews()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}