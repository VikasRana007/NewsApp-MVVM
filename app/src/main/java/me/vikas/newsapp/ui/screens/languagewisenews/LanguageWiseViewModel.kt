package me.vikas.newsapp.ui.screens.languagewisenews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import me.vikas.newsapp.data.model.languagenews.Language
import me.vikas.newsapp.data.repository.LanguageRepository
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.utils.DispatcherProvider
import javax.inject.Inject

@HiltViewModel
class LanguageWiseViewModel @Inject constructor(
    private val languageRepository: LanguageRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Language>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Language>>> = _uiState

    init {
        fetchLanguageList()
    }


    internal fun fetchLanguageList() {
        viewModelScope.launch(dispatcherProvider.main) {
            languageRepository.getLanguage()
                .flowOn(dispatcherProvider.io)
                .catch { e ->
                _uiState.value = UiState.Error(e.toString())
            }.collect {
                _uiState.value = UiState.Success(it)
            }
        }
    }
}