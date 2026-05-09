package me.vikas.newsapp.data.repository.paging_repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.vikas.newsapp.data.api.NetworkService
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.utils.AppConstant.PAGE_SIZE
import javax.inject.Inject

class TopHeadlinePagingRepository @Inject constructor(
    private val networkService: NetworkService
) {

    fun getPagingTopHeadlines(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ), pagingSourceFactory = {
                TopHeadlinePagingSource(networkService)
            }).flow
    }
}