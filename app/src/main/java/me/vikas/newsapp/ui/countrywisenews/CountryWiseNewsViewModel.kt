package me.vikas.newsapp.ui.countrywisenews

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import me.vikas.newsapp.data.model.countrynews.Country
import me.vikas.newsapp.data.repository.CountryRepository
import me.vikas.newsapp.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class CountryWiseNewsViewModel @Inject constructor(
    private val countryRepository: CountryRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<Country>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Country>>> = _uiState

    init {
        fetchCountryList()
    }

    private fun fetchCountryList() {
        viewModelScope.launch {
            countryRepository.getCountries().catch { e ->
                _uiState.value = UiState.Error(e.toString())
            }.collect {
                _uiState.value = UiState.Success(it)
            }
        }
    }
}