package me.vikas.newsapp.ui.newssource

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import me.vikas.newsapp.data.model.news_source.Source
import me.vikas.newsapp.data.repository.NewsSourceRepository
import me.vikas.newsapp.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class NewsSourceViewModel @Inject constructor(
    private val newsSourceRepository: NewsSourceRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Source>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Source>>> = _uiState

    init {
        fetchNewsSource()
    }

    fun fetchNewsSource() {
        viewModelScope.launch {
            newsSourceRepository.getNewsSource().catch { e ->
                _uiState.value = UiState.Error(e.toString())
            }.collect {
                _uiState.value = UiState.Success(it)
            }
        }
    }
}