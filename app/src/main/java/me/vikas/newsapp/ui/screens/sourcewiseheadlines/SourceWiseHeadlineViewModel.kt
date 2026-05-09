package me.vikas.newsapp.ui.screens.sourcewiseheadlines

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.data.repository.NewsCategoryListRepository
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.utils.DispatcherProvider
import javax.inject.Inject

@HiltViewModel
class SourceWiseHeadlineViewModel @Inject constructor(
    private val newsCategoryListRepository: NewsCategoryListRepository,
    private val dispatcherProvider: DispatcherProvider,
    savedStateHandle: SavedStateHandle     // Hilt Automatically will inject this
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    private val sourceId: String = savedStateHandle.get<String>("sourceId") ?: ""


    init {
        fetchSourceWiseHeadlineList()
    }

    fun fetchSourceWiseHeadlineList() {
        viewModelScope.launch(dispatcherProvider.main) {
            newsCategoryListRepository.fetchSourceWiseHeadlineList(sourceId)
                .flowOn(dispatcherProvider.io).catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}