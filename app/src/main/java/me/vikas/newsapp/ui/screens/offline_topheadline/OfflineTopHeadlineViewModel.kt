package me.vikas.newsapp.ui.screens.offline_topheadline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.vikas.newsapp.data.local.entity.topheadlines.ArticleEntity
import me.vikas.newsapp.domain.usecase.offline.GetOfflineTopHeadlinesUseCase
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.utils.AppConstant.COUNTRY
import me.vikas.newsapp.utils.DispatcherProvider
import javax.inject.Inject

@HiltViewModel
class OfflineTopHeadlineViewModel @Inject constructor(
    getOfflineTopHeadlinesUseCase: GetOfflineTopHeadlinesUseCase,
    dispatcherProvider: DispatcherProvider
) : ViewModel() {

    val uiState: StateFlow<UiState<List<ArticleEntity>>> = getOfflineTopHeadlinesUseCase(COUNTRY)
        .map<List<ArticleEntity>, UiState<List<ArticleEntity>>> { UiState.Success(it) }
        .catch { e -> emit(UiState.Error(e.toString())) }
        .flowOn(dispatcherProvider.io)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UiState.Loading
        )
}