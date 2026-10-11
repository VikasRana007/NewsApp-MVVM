package me.vikas.newsapp.domain.usecase.topheadline

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.data.repository.TopHeadlineRepository
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(
    private val topHeadlineRepository: TopHeadlineRepository
) {
    operator fun invoke(country: String): Flow<List<Article>> {
        return topHeadlineRepository.getTopHeadlines(country).map { articles ->
            articles.filter { article ->
                // Practical Example: Filter out articles that don't have an image URL
                !article.urlToImage.isNullOrEmpty()
            }.map { article ->
                // Practical Example: Modify the data before it reaches the UI
                article.copy(
                    // If title contains a hyphen (often used for source name), keep only the actual title
                    title = article.title.substringBeforeLast(" - "),
                    // Ensure description is never null so UI doesn't have to check
                    description = article.description ?: "No description available for this article."
                )
            }
        }
    }
}
