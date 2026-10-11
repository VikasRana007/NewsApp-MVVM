package me.vikas.newsapp.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.data.repository.TopHeadlineRepository
import me.vikas.newsapp.domain.usecase.topheadline.GetTopHeadlinesUseCase
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.ui.screens.topheadline.TopHeadlineViewModel
import me.vikas.newsapp.utils.AppConstant.COUNTRY
import me.vikas.newsapp.utils.DispatcherProvider
import me.vikas.newsapp.utils.NetworkHelper
import me.vikas.newsapp.utils.TestDispatcherProvider
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TopHeadlineViewModelTest {

    @Mock
    private lateinit var topHeadlineRepository: TopHeadlineRepository

    private lateinit var getTopHeadlinesUseCase: GetTopHeadlinesUseCase

    private lateinit var dispatcherProvider: DispatcherProvider

    private val savedStateHandle = SavedStateHandle(
        mapOf("countryCode" to "us")            // key must match ViewModel
    )

    @Mock
    private lateinit var networkHelper : NetworkHelper


    @Before
    fun setup() {
        dispatcherProvider = TestDispatcherProvider()
        doReturn(true).`when`(networkHelper).isInternetConnected()
        getTopHeadlinesUseCase = GetTopHeadlinesUseCase(topHeadlineRepository)
    }

    @Suppress("UnusedFlow")
    @Test
    fun fetchNews_whenRepositoryResponseSuccess_shouldSetSuccessUiState() {
        runTest {
            val articles: List<Article> = emptyList()

            doReturn(flowOf(articles)).`when`(topHeadlineRepository).getTopHeadlines(COUNTRY)

            val viewModel =
                TopHeadlineViewModel(getTopHeadlinesUseCase, dispatcherProvider,
                    networkHelper, savedStateHandle)

            viewModel.uiState.test {

                val first = awaitItem()

                if (first is UiState.Loading) {
                    val success = awaitItem() as UiState.Success
                    assertEquals(articles, success.data)
                } else {
                    val success = first as UiState.Success
                    assertEquals(articles, success.data)
                }
                cancelAndIgnoreRemainingEvents()
            }
            verify(
                topHeadlineRepository, times(1)
            ).getTopHeadlines(COUNTRY)

        }
    }

    @Suppress("UnusedFlow")
    @Test
    fun fetchNews_whenRepositoryResponseError_shouldSetErrorUiState() {
        runTest {
            val errorMessage = "Some thing went wrong, Please try again."
            doReturn(flow<List<Article>> {
                throw IllegalStateException(errorMessage)
            }).`when`(topHeadlineRepository).getTopHeadlines(COUNTRY)

            val viewModel =
                TopHeadlineViewModel(getTopHeadlinesUseCase, dispatcherProvider,
                    networkHelper, savedStateHandle)
            viewModel.uiState.test {
                val first = awaitItem()

                val errorState = if (first is UiState.Loading) {
                    awaitItem()
                } else {
                    first
                }

                assertEquals(UiState.Error(errorMessage), errorState)
                cancelAndIgnoreRemainingEvents()
            }

            verify(
                topHeadlineRepository, times(1)
            ).getTopHeadlines(COUNTRY)

        }
    }
}
