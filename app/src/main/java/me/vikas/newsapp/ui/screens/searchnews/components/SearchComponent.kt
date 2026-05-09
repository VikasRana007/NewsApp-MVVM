package me.vikas.newsapp.ui.screens.searchnews.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.ui.screens.topheadline.NewsArticleCard
import me.vikas.newsapp.ui.theme.NewsAppColors

// Search Results LazyColumn
@Composable
fun SearchResultList(
    articles: List<Article>, onArticleClick: (Article) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = articles, key = { it.url }) { article ->
            NewsArticleCard(
                article = article, onClick = { onArticleClick(article) })
        }
    }
}


// Initial Empty State — before user types
@Composable
fun SearchEmptyPrompt() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = NewsAppColors.PrimaryColor.copy(alpha = 0.3f),
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Search for News",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = NewsAppColors.PrimaryVariant.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "Type at least 3 characters to get results",
            fontSize = 13.sp,
            color = NewsAppColors.PrimaryVariant.copy(alpha = 0.4f),
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}

// No Results State
@Composable
fun SearchNoResultState(query: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = NewsAppColors.PrimaryColor.copy(alpha = 0.3f),
            modifier = Modifier.size(56.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "No results found",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = NewsAppColors.PrimaryVariant
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "No news found for \"$query\"",
            fontSize = 13.sp,
            color = NewsAppColors.PrimaryVariant.copy(alpha = 0.45f),
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
    }
}