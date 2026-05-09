package me.vikas.newsapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.vikas.newsapp.data.api.NetworkService
import me.vikas.newsapp.data.model.topheadline.Article
import javax.inject.Inject

class NewsSearchRepository @Inject constructor(private val networkService: NetworkService) {
    fun getNews(query: String): Flow<List<Article>> = flow {
        val response = networkService.getNews(query)
        if (response.status == "ok") {
            emit(response.articles)
        } else {
            throw Exception(response.message)
        }
    }
}
