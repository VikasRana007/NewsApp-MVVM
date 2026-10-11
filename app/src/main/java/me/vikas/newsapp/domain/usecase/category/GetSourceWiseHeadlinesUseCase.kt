package me.vikas.newsapp.domain.usecase.category

import kotlinx.coroutines.flow.Flow
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.data.repository.NewsCategoryListRepository
import javax.inject.Inject

class GetSourceWiseHeadlinesUseCase @Inject constructor(
    private val newsCategoryListRepository: NewsCategoryListRepository
) {
    operator fun invoke(sourceId: String): Flow<List<Article>> {
        return newsCategoryListRepository.fetchSourceWiseHeadlineList(sourceId)
    }
}
