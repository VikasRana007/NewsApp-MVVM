package me.vikas.newsapp.data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import me.vikas.newsapp.data.repository.OfflineTopHeadlineRepository
import me.vikas.newsapp.utils.AppConstant

@HiltWorker
class NewsUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: OfflineTopHeadlineRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("Worker", "Started News Sync")
        return try {
            // API → map → delete + insert → emit DB
            repository.getArticles(AppConstant.COUNTRY).first() // Triggers execution
            Log.d("Worker", "Sync Success")
            Result.success()
        } catch (e: Exception) {
            Log.e("Worker", "Sync Failed: ${e.message}")
            Result.retry()
        }

    }


}