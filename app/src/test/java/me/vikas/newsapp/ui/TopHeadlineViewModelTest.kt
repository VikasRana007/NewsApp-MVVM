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

    private lateinit var dispatcherProvider: DispatcherProvider

    private val savedStateHandle = SavedStateHandle(
        mapOf("countryCode" to "us")            // key must match ViewModel
    )

    @Mock
    private lateinit var networkHelper : NetworkHelper


    @Before
    fun setup() {
        dispatcherProvider = TestDispatcherProvider()
    }

    @Suppress("UnusedFlow")
    @Test
    fun fetchNews_whenRepositoryResponseSuccess_shouldSetSuccessUiState() {
        runTest {
            val articles: List<Article> = emptyList()

            doReturn(flowOf(articles)).`when`(topHeadlineRepository).getTopHeadlines(COUNTRY)

            val viewModel =
                TopHeadlineViewModel(topHeadlineRepository, dispatcherProvider,
                    networkHelper, savedStateHandle)

            viewModel.uiState.test {

                // ❗ Do NOT assume first is Loading
                val first = awaitItem()

                // Very Important  Below we used if else condition to avoid the race condition of
                // fetch news
                // because in the View Model we are directly calling the fetchNews() inside from
                // init Block  // we can also use => advanceUntilIdle  but for our view model
                // scenario the current design is ok

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
                TopHeadlineViewModel(topHeadlineRepository, dispatcherProvider,
                    networkHelper, savedStateHandle)
            viewModel.uiState.test {

                //1. Initial State
                assert(awaitItem() is UiState.Loading)

                //2. Error State
                assertEquals(
                    UiState.Error(errorMessage), awaitItem() as UiState.Error
                )
                cancelAndIgnoreRemainingEvents()
            }

            verify(
                topHeadlineRepository, times(1)
            ).getTopHeadlines(COUNTRY)

        }
    }

    /**
     * If we want to do something extra task after Complete the Test Task then we
     * can use @After Annotation.  as like Tear Down something.
     */
}