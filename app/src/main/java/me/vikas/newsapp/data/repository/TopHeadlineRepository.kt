package me.vikas.newsapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.vikas.newsapp.data.api.NetworkService
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.di.ActivityScope
import javax.inject.Inject
import javax.inject.Singleton

@ActivityScope
class TopHeadlineRepository @Inject constructor(private val networkService: NetworkService) {

    /**
     * Production Repository Must Validate API Status & UI State must manage on View Model
     * Repository purpose only for domain data.
     */
    fun getTopHeadlines(country: String): Flow<List<Article>> = flow {
        val response = networkService.getTopHeadlines(country)
        if (response.status == "ok") {
            emit(response.articles)
        } else {
            throw Exception(response.message)
        }
    }

}