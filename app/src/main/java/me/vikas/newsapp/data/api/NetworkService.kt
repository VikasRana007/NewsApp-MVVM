package me.vikas.newsapp.data.api

import me.vikas.newsapp.data.model.news_source.NewsSourceResponse
import me.vikas.newsapp.data.model.topheadline.TopHeadlinesResponse
import me.vikas.newsapp.utils.AppConstant.EVERYTHING
import me.vikas.newsapp.utils.AppConstant.NEWS_SOURCE
import me.vikas.newsapp.utils.AppConstant.TOP_HEADLINES
import retrofit2.http.GET
import retrofit2.http.Query


interface NetworkService {

    @GET(TOP_HEADLINES)
    suspend fun getTopHeadlines(@Query("country") country: String): TopHeadlinesResponse

    @GET(NEWS_SOURCE)
    suspend fun getNewsSource(@Query("language") language: String): NewsSourceResponse

    @GET(TOP_HEADLINES)
    suspend fun getNewsSourceCategory(@Query("sources") source: String): TopHeadlinesResponse

    @GET(EVERYTHING)
    suspend fun getNews(@Query("q") query: String): TopHeadlinesResponse

}