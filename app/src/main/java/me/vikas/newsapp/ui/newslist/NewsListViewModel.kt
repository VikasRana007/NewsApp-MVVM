package me.vikas.newsapp.ui.newslist

import android.content.Context
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.data.repository.NewsCategoryListRepository
import me.vikas.newsapp.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val newsCategoryListRepository: NewsCategoryListRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    fun fetchNewsCategoryList(source: String) {
        viewModelScope.launch {
            newsCategoryListRepository.getNewsSourceCategory(source).catch { e ->
                _uiState.value = UiState.Error(e.toString())
            }.collect {
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