package me.vikas.newsapp.ui.screens.newssource

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.vikas.newsapp.data.model.news_source.Source
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.ui.screens.newssource.components.NewsSourceErrorState
import me.vikas.newsapp.ui.screens.newssource.components.NewsSourceItem
import me.vikas.newsapp.ui.screens.newssource.components.NewsSourceLoadingState
import me.vikas.newsapp.ui.theme.NewsAppColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsSourceRoutes(
    onSourceClick: (id: String) -> Unit,
    newsSourceViewModel: NewsSourceViewModel,
    onRetry: () -> Unit = { newsSourceViewModel.fetchNewsSource() },
) {

    //  Single UiState<List<Source>> collect 
    val uiState by newsSourceViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "News Sources",
                        color = NewsAppColors.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NewsAppColors.PrimaryColor
                )
            )
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(NewsAppColors.SurfaceLight)
        ) {
            NewsSourceData(uiState, onSourceClick, onRetry)
        }
    }
}

@Composable
fun NewsSourceData(uiState: UiState<List<Source>>,
                   onSourceClick: (id: String) -> Unit,
                   onRetry: () -> Unit ) {
    // Fetch State from UiState
    when (uiState) {
        is UiState.Loading -> {
            NewsSourceLoadingState("Fetching Sources...")
        }

        is UiState.Success -> {
            NewsSourceList(
                sources = uiState.data,
                onSourceClick
            )
        }

        is UiState.Error -> {
            val errorMessage = uiState.message
            NewsSourceErrorState(
                errorMessage = errorMessage,
                onRetry = onRetry
            )
        }
    }
}


// Sources LazyColumn
@Composable
fun NewsSourceList(
    sources: List<Source>,
    onSourceClick: (sourceId: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = sources,
            key = { it.id }) { source ->
            NewsSourceItem(
                source = source,
                onSourceClick = {
                    onSourceClick(source.id)
                })
        }
    }
}