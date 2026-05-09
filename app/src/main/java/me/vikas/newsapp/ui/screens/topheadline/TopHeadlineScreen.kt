package me.vikas.newsapp.ui.screens.topheadline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.ui.screens.newssource.components.NewsSourceErrorState
import me.vikas.newsapp.ui.screens.newssource.components.NewsSourceLoadingState
import me.vikas.newsapp.ui.theme.NewsAppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopHeadlineRoutes(
    onNewsClick: (url: String) -> Unit,
    topHeadlineViewModel: TopHeadlineViewModel,
    onBackClick: () -> Unit,
    onRetry: () -> Unit = { topHeadlineViewModel.fetchTopHeadlines() }
) {

    val uiState by topHeadlineViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                Text(
                    text = "Top Headlines", style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ), color = NewsAppColors.White
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
                containerColor = NewsAppColors.PrimaryColor,
                titleContentColor = MaterialTheme.colorScheme.background
            )
            )
        }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TopHeadLineData(uiState, onNewsClick, onRetry)
        }
    }
}


@Composable
fun TopHeadLineData(
    uiState: UiState<List<Article>>,
    onNewsClick: (url: String) -> Unit,
    onRetry: () -> Unit
) {
    when (uiState) {
        is UiState.Success -> {
            NewsArticleList(article = uiState.data, onNewsClick)
        }

        is UiState.Loading -> {
            NewsSourceLoadingState("Fetching Top Headlines...")
        }

        is UiState.Error -> {
            // ShowError(uiState.message)
            val errorMessage = uiState.message
            NewsSourceErrorState(
                errorMessage = errorMessage, onRetry = onRetry
            )
        }
    }
}

@Composable
fun NewsArticleList(article: List<Article>, onNewsClick: (url: String) -> Unit) {
    LazyColumn {
        items(article, key = { article -> article.url }) { article ->
            Article(article, onNewsClick)
        }
    }
}


@Composable
fun Article(article: Article, onNewsClick: (url: String) -> Unit) {
    NewsArticleCard(article, onNewsClick)
}
