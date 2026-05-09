package me.vikas.newsapp.ui.screens.searchnews

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.data.repository.NewsSearchRepository
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.utils.AppConstant.DEBOUNCE_TIME
import me.vikas.newsapp.utils.AppConstant.MIN_SEARCH_CHARACTERS
import me.vikas.newsapp.utils.DispatcherProvider
import javax.inject.Inject

@HiltViewModel
class NewsSearchViewModel @Inject constructor(
    private val newsSearchRepository: NewsSearchRepository,
    private val dispatcherProvider: DispatcherProvider,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Success(emptyList()))
    val uiState: StateFlow<UiState<List<Article>>> = _uiState
    private val query = MutableStateFlow("")


    init {
        optimizedNewsSearchFlow()
    }


    fun searchNews(searchQuery: String) {
        query.value = searchQuery
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private fun optimizedNewsSearchFlow() {
        viewModelScope.launch(dispatcherProvider.main) {
            query.debounce(DEBOUNCE_TIME).filter {
                if (it.isNotEmpty() && (it.length >= MIN_SEARCH_CHARACTERS)) {
                    return@filter true
                } else {
                    _uiState.value = UiState.Success(emptyList())
                    return@filter false
                }
            }.distinctUntilChanged().flatMapLatest {
                    _uiState.value = UiState.Loading
                    return@flatMapLatest newsSearchRepository.getNews(it)
                        .flowOn(dispatcherProvider.io)
                        .catch { e ->
                            _uiState.value = UiState.Error(e.toString())
                        }
                }.flowOn(dispatcherProvider.io).collect {
                    _uiState.value = UiState.Success(it)
                }

        }
    }

    /**
     * It is also Unit Test Verified, in case of Unit Testing just
     * send the normal fake string.
     */
    fun openUrl(customUrl: String, context: Context) {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(context, customUrl.toUri())
    }

}