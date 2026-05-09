package me.vikas.newsapp.ui.screens.topheadline

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
import me.vikas.newsapp.data.repository.TopHeadlineRepository
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.utils.AppConstant.COUNTRY
import me.vikas.newsapp.utils.DispatcherProvider
import me.vikas.newsapp.utils.NetworkHelper
import javax.inject.Inject

@HiltViewModel
class TopHeadlineViewModel @Inject constructor(
    private val topHeadlineRepository: TopHeadlineRepository,
    private val dispatcherProvider: DispatcherProvider,
    networkHelper: NetworkHelper,
    savedStateHandle: SavedStateHandle     // Hilt Automatically will inject this
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Article>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Article>>> = _uiState

    private val countryCode: String = savedStateHandle.get<String>("countryCode") ?: ""

    init {
        if (networkHelper.isInternetConnected()) {
            fetchTopHeadlines()
        } else {

//            From here Local Database will be fetched.
            _uiState.value = UiState.Error("No Internet Connection")
        }
        //fetchTopHeadlines()
    }


    fun fetchTopHeadlines() {

        if (countryCode.isNotBlank()) {
            COUNTRY = countryCode
        }
        viewModelScope.launch(dispatcherProvider.main) {
            topHeadlineRepository.getTopHeadlines(COUNTRY).flowOn(dispatcherProvider.io)
                .catch { e ->
                    _uiState.value = UiState.Error(e.message ?: "Unknown Error")
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

/*    fun startWorkManager(context: Context) {
        val request = OneTimeWorkRequestBuilder<NewsUpdateWorker>().setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            ).setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL, 10, TimeUnit.SECONDS
            ).addTag("news_sync_manual").build()

        WorkManager.getInstance(context).enqueueUniqueWork(
                "news_manual_sync", ExistingWorkPolicy.KEEP, request
            )
    }*/
}