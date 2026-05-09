package me.vikas.newsapp.ui.screens.newssource

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import me.vikas.newsapp.data.model.news_source.Source
import me.vikas.newsapp.data.repository.NewsSourceRepository
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.utils.AppConstant.LANGUAGE
import me.vikas.newsapp.utils.DispatcherProvider
import javax.inject.Inject

@HiltViewModel
class NewsSourceViewModel @Inject constructor(
    private val newsSourceRepository: NewsSourceRepository,
    private val dispatcherProvider: DispatcherProvider,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Source>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Source>>> = _uiState

    private val languageCode: String = savedStateHandle.get<String>("languageCode") ?: ""

    init {
        if (languageCode.isNotBlank()) {
            LANGUAGE = languageCode
        }
        fetchNewsSource()
    }

    fun fetchNewsSource() {
        viewModelScope.launch(dispatcherProvider.main) {
            newsSourceRepository.getNewsSource(LANGUAGE)
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                _uiState.value = UiState.Error(e.message ?: "Unknown Error")
            }.collect {
                _uiState.value = UiState.Success(it)
            }
        }
    }
}