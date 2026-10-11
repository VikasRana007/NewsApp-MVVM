package me.vikas.newsapp.domain.usecase.source

import kotlinx.coroutines.flow.Flow
import me.vikas.newsapp.data.model.news_source.Source
import me.vikas.newsapp.data.repository.NewsSourceRepository
import javax.inject.Inject

class GetNewsSourcesUseCase @Inject constructor(
    private val newsSourceRepository: NewsSourceRepository
) {
    operator fun invoke(language: String): Flow<List<Source>> {
        return newsSourceRepository.getNewsSource(language)
    }
}
