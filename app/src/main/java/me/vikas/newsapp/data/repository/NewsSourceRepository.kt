package me.vikas.newsapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.vikas.newsapp.data.api.NetworkService
import me.vikas.newsapp.data.local.DatabaseService
import me.vikas.newsapp.data.model.news_source.Source
import me.vikas.newsapp.utils.AppConstant.LANGUAGE
import javax.inject.Inject

class NewsSourceRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService) {

    /**
     * Production Repository Must Validate API Status & UI State must manage on View Model
     * Repository purpose only for domain data.
     */
    fun getNewsSource(languageCode: String): Flow<List<Source>> = flow {
        val response = networkService.getNewsSource(languageCode)
        if (response.status == "ok") {
            emit(response.sources)
        } else {
            throw Exception(response.message)
        }
    }
}