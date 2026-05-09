package me.vikas.newsapp.data.repository

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import me.vikas.newsapp.data.api.NetworkService
import me.vikas.newsapp.data.local.DatabaseService
import me.vikas.newsapp.data.local.entity.topheadlines.ArticleEntity
import me.vikas.newsapp.data.model.topheadline.toArticleEntity
import javax.inject.Inject

class OfflineTopHeadlineRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getArticles(country: String): Flow<List<ArticleEntity>> {
        return flow { emit(networkService.getTopHeadlines(country)) }.map {
                it.articles.map { apiArticle -> apiArticle.toArticleEntity() }
            }.flatMapConcat { articles ->
                flow {
                    databaseService.deleteAllAndInsertAllArticles((articles))
                    databaseService.saveLastSyncTime()
                    emit(Unit)  // No Need to emit something here about DB Write
                }
            }.flatMapConcat {
                databaseService.getAllArticles()
            }
    }

    suspend fun getArticlesDirectlyFromDB(): Flow<List<ArticleEntity>> {
        return databaseService.getAllArticles()
    }

    suspend fun shouldFetch(): Boolean {
        val lastSyncTime = databaseService.getLastSyncTime()
        return if (lastSyncTime == null) {
            true
        } else {
            System.currentTimeMillis() - lastSyncTime > 6 * 60 * 60 * 1000
        }
    }

}