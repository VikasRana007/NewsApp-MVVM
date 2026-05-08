package me.vikas.newsapp.data.local

import kotlinx.coroutines.flow.Flow
import me.vikas.newsapp.data.local.entity.source.NewsSourceEntity
import me.vikas.newsapp.data.local.entity.synctime.SyncTopHeadlineTime
import me.vikas.newsapp.data.local.entity.topheadlines.ArticleEntity
import javax.inject.Inject

class AppDatabaseService @Inject constructor(
    private val appDatabase: AppDatabase
) : DatabaseService {

    override suspend fun getAllArticles(): Flow<List<ArticleEntity>> {
        return appDatabase.articleDao().getAllArticles()
    }

    override suspend fun deleteAllAndInsertAllArticles(articles: List<ArticleEntity>) {
        appDatabase.articleDao().deleteAndInsertArticles(articles)
    }

    override suspend fun getAllSources(): Flow<List<NewsSourceEntity>> {
        return appDatabase.sourceDao().getAllSources()
    }

    override suspend fun deleteAllAndInsertAllSources(sources: List<NewsSourceEntity>) {
        return appDatabase.sourceDao().deleteAndInsertSources(sources)
    }

    override suspend fun saveLastSyncTime() {
        appDatabase.syncTimeDao().insertOrUpdate(
            SyncTopHeadlineTime(
                lastSyncTime = System.currentTimeMillis()
            )
        )
    }

    override suspend fun getLastSyncTime(): Long {
        return appDatabase.syncTimeDao().getLastSyncTime("TOP_HEADLINES")

    }


}