package me.vikas.newsapp.domain.usecase.pagination

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.data.repository.paging_repository.TopHeadlinePagingRepository
import javax.inject.Inject

class GetPaginationTopHeadlinesUseCase @Inject constructor(
    private val topHeadlinePagingRepository: TopHeadlinePagingRepository
) {
    operator fun invoke(): Flow<PagingData<Article>> {
        return topHeadlinePagingRepository.getPagingTopHeadlines()
    }
}
