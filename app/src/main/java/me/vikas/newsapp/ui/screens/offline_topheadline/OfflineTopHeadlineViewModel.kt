package me.vikas.newsapp.ui.screens.offline_topheadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import me.vikas.newsapp.data.local.entity.topheadlines.ArticleEntity
import me.vikas.newsapp.data.repository.OfflineTopHeadlineRepository
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.utils.AppConstant.COUNTRY
import me.vikas.newsapp.utils.DispatcherProvider
import me.vikas.newsapp.utils.NetworkHelper
import javax.inject.Inject

@HiltViewModel
class OfflineTopHeadlineViewModel @Inject constructor(
    private val offlineTopHeadlineRepository: OfflineTopHeadlineRepository,
    private val dispatcherProvider: DispatcherProvider,
    networkHelper: NetworkHelper
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<ArticleEntity>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<ArticleEntity>>> = _uiState


    init {
        if (networkHelper.isInternetConnected()) {
            fetchArticlesConditionally()
        } else {
            fetchArticlesDirectlyFromDB()
        }
    }

//    init {
//        viewModelScope.launch {
//            if (networkHelper.isInternetConnected() && shouldFetch()) {
//                fetchArticles()
//            } else {
//                fetchArticlesDirectlyFromDB()
//            }
//        }
//    }

    private fun fetchArticlesConditionally() {
        viewModelScope.launch {
            if (shouldFetch()) {
                collectArticlesFlow(offlineTopHeadlineRepository.getArticles(COUNTRY))
            } else {
                collectArticlesFlow(offlineTopHeadlineRepository.getArticlesDirectlyFromDB())
            }
        }
    }


    private fun collectArticlesFlow(flow: Flow<List<ArticleEntity>>) {
        viewModelScope.launch {
            flow.flowOn(dispatcherProvider.io).catch { e ->
                _uiState.value = UiState.Error(e.toString())
            }.collectLatest {
                _uiState.value = UiState.Success(it)
            }
        }
    }

    private fun fetchArticlesDirectlyFromDB() {
        viewModelScope.launch {
            offlineTopHeadlineRepository.getArticlesDirectlyFromDB().flowOn(dispatcherProvider.io)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collectLatest {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    private suspend fun shouldFetch(): Boolean {
        return offlineTopHeadlineRepository.shouldFetch()
    }

}