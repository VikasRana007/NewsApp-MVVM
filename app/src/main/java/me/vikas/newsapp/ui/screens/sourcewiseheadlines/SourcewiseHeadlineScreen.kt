package me.vikas.newsapp.ui.screens.sourcewiseheadlines

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.ui.screens.newssource.components.NewsSourceErrorState
import me.vikas.newsapp.ui.screens.newssource.components.NewsSourceLoadingState
import me.vikas.newsapp.ui.screens.topheadline.NewsArticleCard
import me.vikas.newsapp.ui.theme.NewsAppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SourceWiseHeadlineRoutes(
    sourceWiseHeadlineViewModel: SourceWiseHeadlineViewModel = hiltViewModel(),
    onNewsClick: (newsUrl: String) -> Unit,
    onBackClick: () -> Unit
) {

    val uiState by sourceWiseHeadlineViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                Text(
                    text = "Source Headlines",
                    color = NewsAppColors.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            }, navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = NewsAppColors.White
                    )
                }
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


            when (uiState) {
                is UiState.Loading -> {
                    NewsSourceLoadingState("Fetching Headlines...")
                }

                is UiState.Success -> {
                    val articles = (uiState as UiState.Success<List<Article>>).data
                    SourceWiseArticleList(articles, onNewsClick)
                }

                is UiState.Error -> {
                    val errorMessage = (uiState as UiState.Error).message
                    NewsSourceErrorState(
                        errorMessage,
                        onRetry = { sourceWiseHeadlineViewModel.fetchSourceWiseHeadlineList() })
                }
            }
        }
    }
}

// Articles LazyColumn
@Composable
fun SourceWiseArticleList(
    articles: List<Article>, onNewsClick: (url: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(items = articles, key = { it.url }   // stable key
        ) { article ->
            NewsArticleCard(
                article = article, onClick = {
                    onNewsClick(article.url)
                })
        }
    }
}