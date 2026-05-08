package me.vikas.newsapp.data.local

import kotlinx.coroutines.flow.Flow
import me.vikas.newsapp.data.local.entity.source.NewsSourceEntity
import me.vikas.newsapp.data.local.entity.topheadlines.ArticleEntity

interface DatabaseService {

     suspend fun getAllArticles(): Flow<List<ArticleEntity>>

    suspend fun deleteAllAndInsertAllArticles(articles: List<ArticleEntity>)

    suspend fun getAllSources(): Flow<List<NewsSourceEntity>>

    suspend fun deleteAllAndInsertAllSources(sources: List<NewsSourceEntity>)

    suspend fun saveLastSyncTime()

    suspend fun getLastSyncTime(): Long?

}