package me.vikas.newsapp.ui

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import me.vikas.newsapp.data.model.news_source.Source
import me.vikas.newsapp.data.repository.NewsSourceRepository
import me.vikas.newsapp.domain.usecase.source.GetNewsSourcesUseCase
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.ui.screens.newssource.NewsSourceViewModel
import me.vikas.newsapp.utils.AppConstant.LANGUAGE
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
class NewsSourceViewModelTest {

    @Mock
    private lateinit var newsSourceRepository: NewsSourceRepository

    private lateinit var getNewsSourcesUseCase: GetNewsSourcesUseCase

    private lateinit var dispatcherProvider: DispatcherProvider

    private val savedStateHandle = SavedStateHandle(
        mapOf("languageCode" to "en")            // key must match ViewModel
    )

    @Mock
    private lateinit var networkHelper: NetworkHelper

    @Before
    fun setUp() {
        dispatcherProvider = TestDispatcherProvider()
        getNewsSourcesUseCase = GetNewsSourcesUseCase(newsSourceRepository)
    }


    @Suppress("UnusedFlow")
    @Test
    fun fetchNewsSource_whenRepositoryResponseSuccess_shouldSetSuccessUIState() {
        runTest {
            val sources: List<Source> = emptyList()

            doReturn(flowOf(sources)).`when`(newsSourceRepository).getNewsSource(LANGUAGE)

            val viewModel = NewsSourceViewModel(
                getNewsSourcesUseCase,
                dispatcherProvider,
                savedStateHandle
            )

            viewModel.uiState.test {
                val first = awaitItem()

                if (first is UiState.Loading) {
                    val success = awaitItem() as UiState.Success
                    assertEquals(sources, success.data)
                } else {
                    val success = first as UiState.Success
                    assertEquals(sources, success.data)
                }
                cancelAndIgnoreRemainingEvents()
            }
            verify(
                newsSourceRepository, times(1)
            ).getNewsSource(LANGUAGE)
        }

    }

    @Test
    fun fetchNewsSource_whenRepositoryResponseError_shouldSetErrorUIState() {
        val errorMessage = "Some thing went wrong, Please try again."
        runTest {
            doReturn(flow<List<Source>> {
                throw IllegalStateException(errorMessage)
            }).`when`(newsSourceRepository).getNewsSource(LANGUAGE)

            val viewModel = NewsSourceViewModel(
                getNewsSourcesUseCase,
                dispatcherProvider,
                savedStateHandle
            )

            advanceUntilIdle()

            viewModel.uiState.test {
                val first = awaitItem()

                val errorState = if (first is UiState.Loading) {
                    awaitItem()
                } else {
                    first
                }

                assert(errorState is UiState.Error)
                assertEquals(errorMessage, (errorState as UiState.Error).message)
                cancelAndIgnoreRemainingEvents()
            }
            verify(
                newsSourceRepository, times(1)
            ).getNewsSource(LANGUAGE)
        }
    }
}
