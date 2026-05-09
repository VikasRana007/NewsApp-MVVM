package me.vikas.newsapp.data

import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import me.vikas.newsapp.data.api.NetworkService
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.data.model.topheadline.Source
import me.vikas.newsapp.data.model.topheadline.TopHeadlinesResponse
import me.vikas.newsapp.data.repository.TopHeadlineRepository
import me.vikas.newsapp.utils.AppConstant.COUNTRY
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TopHeadlineRepositoryTest {

    @Mock
    private lateinit var networkService: NetworkService

    private lateinit var topHeadlineRepository: TopHeadlineRepository

    @Before
    fun setUp() {
        topHeadlineRepository = TopHeadlineRepository(networkService)
    }

    @Test
    fun getTopHeadline_whenNetworkServiceResponseSuccess_shouldReturnSuccess() {
        runTest {
            val sources = Source(
                id = "sourceId", name = "sourceName"
            )

            val article = Article(
                author = "Vikas Singh",
                content = "This is content",
                description = "This is description",
                publishedAt = "2023-09-01",
                source = sources,
                title = "This is title",
                url = "https://newsapi.org/general",
                urlToImage = "https://newsapi.org/general"
            )

            val articles = mutableListOf<Article>()

            articles.add(article)

            val topHeadlineResponse = TopHeadlinesResponse(
                articles = articles, status = "ok", totalResults = 1
            )

            doReturn(topHeadlineResponse).`when`(networkService).getTopHeadlines(
                COUNTRY
            )

            topHeadlineRepository.getTopHeadlines(COUNTRY).test {
                assertEquals(topHeadlineResponse.articles, awaitItem())
                cancelAndIgnoreRemainingEvents()
            }

            verify(
                networkService, times(1)
            ).getTopHeadlines(COUNTRY)

        }
    }

    @Test
    fun getTopHeadline_whenNetworkServiceResponseError_shouldReturnError() {
        runTest {
            val errorMessage = "Error in Top Headlines"
            doThrow(RuntimeException(errorMessage)).`when`(networkService).getTopHeadlines(COUNTRY)

            topHeadlineRepository.getTopHeadlines(COUNTRY).test {
                assertEquals(errorMessage, awaitError().message)
                cancelAndIgnoreRemainingEvents()

                verify(
                    networkService, times(1)
                ).getTopHeadlines(COUNTRY)
            }
        }
    }
}