package me.vikas.newsapp.ui.screens.pagination

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.ui.base.ShowError
import me.vikas.newsapp.ui.base.ShowLoading
import me.vikas.newsapp.ui.screens.topheadline.NewsArticleCard
import me.vikas.newsapp.ui.theme.NewsAppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaginationTopHeadlineRoutes(
    onNewsClick: (url: String) -> Unit,
    paginationTopHeadlineViewModel: PaginationTopHeadlineViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {

    val articles = paginationTopHeadlineViewModel.uiState.collectAsLazyPagingItems()

    Scaffold(topBar = {
        TopAppBar(
            title = {
            Text(
                text = "Pagination Top Headlines", style = MaterialTheme.typography.titleLarge
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
    }, content = { padding ->
        Column(modifier = Modifier.padding(padding)) {
            TopHeadlineScreen(articles, onNewsClick)
        }
    })

}

@Composable
fun TopHeadlineScreen(articles: LazyPagingItems<Article>, onNewsClick: (url: String) -> Unit) {

    NewsArticleList(articles, onNewsClick)

    articles.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                ShowLoading()
            }

            loadState.refresh is LoadState.Error -> {
                val error = articles.loadState.refresh as LoadState.Error
                ShowError(error.error.localizedMessage!!)
            }

            loadState.append is LoadState.Loading -> {
                ShowLoading()
            }

            loadState.append is LoadState.Error -> {
                val error = articles.loadState.append as LoadState.Error
                ShowError(error.error.localizedMessage!!)
            }
        }
    }

}


@Composable
fun NewsArticleList(articles: LazyPagingItems<Article>, onNewsClick: (url: String) -> Unit) {
    LazyColumn {
        items(articles.itemCount, key = { index -> articles[index]!!.url }) { index ->
            Article(articles[index]!!, onNewsClick)
        }
    }
}


@Composable
fun Article(article: Article, onNewsClick: (url: String) -> Unit) {
    NewsArticleCard(article, onNewsClick)
}