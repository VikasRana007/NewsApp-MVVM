package me.vikas.newsapp.domain.usecase.search

import kotlinx.coroutines.flow.Flow
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.data.repository.NewsSearchRepository
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(
    private val newsSearchRepository: NewsSearchRepository
) {
    operator fun invoke(query: String): Flow<List<Article>> {
        return newsSearchRepository.getNews(query)
    }
}
