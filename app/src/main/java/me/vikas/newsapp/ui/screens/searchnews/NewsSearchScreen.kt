package me.vikas.newsapp.ui.screens.searchnews

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import me.vikas.newsapp.data.model.topheadline.Article
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.ui.screens.newssource.components.NewsSourceErrorState
import me.vikas.newsapp.ui.screens.newssource.components.NewsSourceLoadingState
import me.vikas.newsapp.ui.screens.searchnews.components.SearchEmptyPrompt
import me.vikas.newsapp.ui.screens.searchnews.components.SearchNoResultState
import me.vikas.newsapp.ui.screens.searchnews.components.SearchResultList
import me.vikas.newsapp.ui.theme.NewsAppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsSearchRoute(newsSearchViewModel: NewsSearchViewModel = hiltViewModel()) {

    val uiState by newsSearchViewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Search News",
                        color = NewsAppColors.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NewsAppColors.PrimaryColor
                )
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(NewsAppColors.SurfaceLight)
        ) {

            // Search Bar SearchView in Activity
            SearchBar(
                query = searchQuery, onQueryChange = { newText ->
                searchQuery = newText
                newsSearchViewModel.searchNews(newText)   // debounce ViewModel handle karega
            }, onClear = {
                searchQuery = ""
                newsSearchViewModel.searchNews("")
                focusManager.clearFocus()
            }, focusRequester = focusRequester
            )


            // Inner Content
            Box(
                modifier = Modifier.fillMaxSize()
            ) {

                when (uiState) {
                    // Loading
                    is UiState.Loading -> {
                        NewsSourceLoadingState("News Searching...")
                    }
                    // Error
                    is UiState.Error -> {
                        val errorMsg = (uiState as UiState.Error).message
                        NewsSourceErrorState(errorMsg, onRetry = {
                            newsSearchViewModel.searchNews(searchQuery)
                        })
                    }
                    // Success
                    is UiState.Success -> {
                        val articles = (uiState as UiState.Success<List<Article>>).data
                        if (articles.isEmpty() && searchQuery.isEmpty()) {
                            // Initial empty state
                            SearchEmptyPrompt()
                        } else if (articles.isEmpty()) {
                            // No results found
                            SearchNoResultState(searchQuery)
                        } else {
                            // Results list
                            SearchResultList(articles, onArticleClick = {
                                newsSearchViewModel.openUrl(it.url, context)
                            })
                        }
                    }
                }
            }
        }
    }

    // Auto Focus search bar on open
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

// Search Bar => SearchView + onQueryTextChange
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .focusRequester(focusRequester),
        placeholder = {
            Text(
                text = "Search for news...",
                color = NewsAppColors.PrimaryVariant.copy(alpha = 0.45f)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = NewsAppColors.PrimaryColor
            )
        },
        trailingIcon = {
            // Clear button — visible only when query is not empty
            AnimatedVisibility(
                visible = query.isNotEmpty(), enter = fadeIn(), exit = fadeOut()
            ) {
                IconButton(onClick = onClear) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = NewsAppColors.PrimaryColor
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = NewsAppColors.PrimaryColor,
            unfocusedBorderColor = NewsAppColors.PrimaryColor.copy(alpha = 0.35f),
            focusedTextColor = NewsAppColors.PrimaryVariant,
            unfocusedTextColor = NewsAppColors.PrimaryVariant,
            cursorColor = NewsAppColors.PrimaryColor,
            focusedContainerColor = NewsAppColors.White,
            unfocusedContainerColor = NewsAppColors.White
        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = { onQueryChange(query) })
    )
}


