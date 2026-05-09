package me.vikas.newsapp.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DownloadForOffline
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Source
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Main Dashboard Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    appName: String = "News App",
    onTopHeadlinesClick: () -> Unit = {},
    onOfflineTopHeadlinesClick: () -> Unit = {},
    onPaginationTopHeadlinesClick: () -> Unit = {},
    onNewsSourcesClick: () -> Unit = {},
    onCountriesClick: () -> Unit = {},
    onLanguagesClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
//    onWorkManager: () -> Unit = {}
) {
    val dashboardItems = listOf(
        DashboardItem("Top Headlines", Icons.Default.Newspaper, onTopHeadlinesClick),
        DashboardItem("Offline Top Headlines", Icons.Default.DownloadForOffline,onOfflineTopHeadlinesClick),
        DashboardItem("Pagination Top Headlines", Icons.Default.Newspaper, onPaginationTopHeadlinesClick),
        DashboardItem("News Sources", Icons.Default.Source, onNewsSourcesClick),
        DashboardItem("Country", Icons.Default.Public, onCountriesClick),
        DashboardItem("Language", Icons.Default.Translate, onLanguagesClick),
        DashboardItem("Search", Icons.Default.Search, onSearchClick)
//        DashboardItem("Work Manager Task", Icons.Default.Task, onWorkManager)
    )

    Scaffold(
        topBar = {
            // Equivalent of MaterialToolbar with colorPrimary background
            TopAppBar(
                title = {
                    Text(
                        text = appName,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ), modifier = Modifier.height(82.dp)
            )
        }) { innerPadding ->

        // Equivalent of dashboard_background gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
        ) {
            // Vertically scrollable column — mirrors MotionLayout stacked card constraints
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)  // cards stacked like in XML
            ) {
                dashboardItems.forEachIndexed { index, item ->
                    AnimatedDashboardCard(
                        item = item,
                        animationDelay = index * 120
                    // staggered entry like MotionLayout transition
                    )
                }
            }
        }
    }
}


// Preview
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardScreenPreview() {
    MaterialTheme {
        DashboardScreen()
    }
}