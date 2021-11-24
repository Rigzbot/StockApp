package com.rishik.stockapp.workManager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.rishik.stockapp.repository.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import retrofit2.HttpException

@HiltWorker
class RefreshDataWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val repository: Repository,
) : Worker(appContext, params) {

    companion object {
        const val TAG = "RefreshDataWorker"
    }

    override fun doWork(): Result {
        return try {
            repository.getNews()
            repository.getStocks()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}