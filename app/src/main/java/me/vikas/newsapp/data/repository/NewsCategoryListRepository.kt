package me.vikas.newsapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.vikas.newsapp.data.api.NetworkService
import me.vikas.newsapp.data.model.topheadline.Article
import javax.inject.Inject

class NewsCategoryListRepository @Inject constructor(
    private val networkService: NetworkService
) {
    fun getNewsSourceCategory(source: String): Flow<List<Article>> = flow {
        val response = networkService.getNewsSourceCategory(source)
        if (response.status == "ok") {
            emit(response.articles)
        } else {
            throw Exception(response.message)
        }
    }
}