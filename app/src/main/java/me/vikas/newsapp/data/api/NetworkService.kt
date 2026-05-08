package me.vikas.newsapp.data.api

import me.vikas.newsapp.data.model.news_source.NewsSourceResponse
import me.vikas.newsapp.data.model.topheadline.TopHeadlinesResponse
import me.vikas.newsapp.utils.AppConstant.EVERYTHING
import me.vikas.newsapp.utils.AppConstant.NEWS_SOURCE
import me.vikas.newsapp.utils.AppConstant.TOP_HEADLINES
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    // To get Top Headlines Country wise
    @GET(TOP_HEADLINES)
    suspend fun getTopHeadlines(@Query("country") country: String): TopHeadlinesResponse

    // To get Paginated Top Headlines Country wise
    @GET(TOP_HEADLINES)
    suspend fun getPagingTopHeadlines(
        @Query("country") country: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): TopHeadlinesResponse

    // To Get News Source in Specific Language
    @GET(NEWS_SOURCE)
    suspend fun getNewsSource(@Query("language") language: String): NewsSourceResponse

    // To get the Top headlines of Specific News Source
    @GET(TOP_HEADLINES)
    suspend fun fetchSourceWiseHeadlineList(@Query("sources") source: String): TopHeadlinesResponse

    @GET(EVERYTHING)
    suspend fun getNews(@Query("q") query: String): TopHeadlinesResponse

}