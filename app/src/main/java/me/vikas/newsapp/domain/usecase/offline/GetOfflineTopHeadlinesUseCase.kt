package me.vikas.newsapp.domain.usecase.offline

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import me.vikas.newsapp.data.local.entity.topheadlines.ArticleEntity
import me.vikas.newsapp.data.repository.OfflineTopHeadlineRepository
import me.vikas.newsapp.utils.NetworkHelper
import javax.inject.Inject

class GetOfflineTopHeadlinesUseCase @Inject constructor(
    private val offlineTopHeadlineRepository: OfflineTopHeadlineRepository,
    private val networkHelper: NetworkHelper
) {
    operator fun invoke(country: String): Flow<List<ArticleEntity>> {
        return flow {
            val isConnected = networkHelper.isInternetConnected()
            val isCacheStale = offlineTopHeadlineRepository.shouldFetch()

            if (isConnected && isCacheStale) {
                // Fetch from API, which saves to DB, then returns DB flow
                emitAll(offlineTopHeadlineRepository.getArticles(country))
            } else {
                // Just return the DB flow
                emitAll(offlineTopHeadlineRepository.getArticlesDirectlyFromDB())
            }
        }
    }
}
