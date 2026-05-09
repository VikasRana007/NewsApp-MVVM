package me.vikas.newsapp.ui.screens.pagination

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.data.repository.paging_repository.TopHeadlinePagingRepository
import javax.inject.Inject

@HiltViewModel
class PaginationTopHeadlineViewModel @Inject constructor(
    private val topHeadlinePagingRepository: TopHeadlinePagingRepository
): ViewModel(){
    private val _uiState = MutableStateFlow<PagingData<Article>>(value = PagingData.empty())
    val uiState: StateFlow<PagingData<Article>> = _uiState

    init {
        getNews()
    }

    private fun getNews() {
        viewModelScope.launch {
            topHeadlinePagingRepository.getPagingTopHeadlines()
                .collect {
                    _uiState.value = it
                }
        }
    }
}