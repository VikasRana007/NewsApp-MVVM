package me.vikas.newsapp.ui.screens.topheadline

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.domain.usecase.topheadline.GetTopHeadlinesUseCase
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.utils.AppConstant.COUNTRY
import me.vikas.newsapp.utils.DispatcherProvider
import me.vikas.newsapp.utils.NetworkHelper
import javax.inject.Inject

@HiltViewModel
class TopHeadlineViewModel @Inject constructor(
    getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    dispatcherProvider: DispatcherProvider,
    networkHelper: NetworkHelper,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val countryCode: String = savedStateHandle.get<String>("countryCode") ?: "us"

    init {
        if (countryCode.isNotBlank()) {
            COUNTRY = countryCode
        }
    }

    val uiState: StateFlow<UiState<List<Article>>> = if (networkHelper.isInternetConnected()) {
        getTopHeadlinesUseCase(COUNTRY)
            .map<List<Article>, UiState<List<Article>>> { UiState.Success(it) }
            .catch { e -> emit(UiState.Error(e.message ?: "Unknown Error")) }
            .flowOn(dispatcherProvider.io)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = UiState.Loading
            )
    } else {
        MutableStateFlow<UiState<List<Article>>>(
            UiState.Error("Please Check Internet Connection")
        ).asStateFlow()
    }

    fun fetchTopHeadlines() {
        // Now handled reactively by the uiState initialization, 
        // kept here to satisfy NavGraph retry lambda compilation
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