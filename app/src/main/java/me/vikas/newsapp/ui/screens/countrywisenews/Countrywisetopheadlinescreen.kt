package me.vikas.newsapp.ui.screens.countrywisenews

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
import me.vikas.newsapp.data.model.countrynews.Country
import me.vikas.newsapp.ui.base.UiState
import me.vikas.newsapp.ui.screens.countrywisenews.components.CountryItem
import me.vikas.newsapp.ui.screens.newssource.components.NewsSourceErrorState
import me.vikas.newsapp.ui.screens.newssource.components.NewsSourceLoadingState
import me.vikas.newsapp.ui.theme.NewsAppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryWiseTopHeadRoutes(
    countryWiseNewsViewModel: CountryWiseNewsViewModel = hiltViewModel(),
    onCountryClick: (countryCode: String) -> Unit
) {
    val uiState by countryWiseNewsViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Country",
                        color = NewsAppColors.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = NewsAppColors.PrimaryColor
                )
            )
        }) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(NewsAppColors.SurfaceLight)
        ) {
            when (uiState) {

                is UiState.Loading -> {
                    NewsSourceLoadingState("Fetching Countries...")
                }

                is UiState.Error -> {
                    val errorMsg = (uiState as UiState.Error).message
                    NewsSourceErrorState(
                        errorMessage = errorMsg,
                        onRetry = { countryWiseNewsViewModel.fetchCountryList() })
                }

                is UiState.Success -> {
                    val countries = (uiState as UiState.Success<List<Country>>).data
                    CountryList(
                        countries = countries, onCountryClick = onCountryClick
                    )
                }
            }
        }
    }


}


// Country LazyColumn
@Composable
fun CountryList(
    countries: List<Country>, onCountryClick: (countryCode: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = countries, key = { it.code }) { country ->
            CountryItem(
                country = country, onCountryClick = { onCountryClick(country.code) })
        }
    }
}