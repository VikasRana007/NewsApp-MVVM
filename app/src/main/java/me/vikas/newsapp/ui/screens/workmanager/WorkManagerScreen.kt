package me.vikas.newsapp.ui.screens.workmanager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import me.vikas.newsapp.data.worker.NewsUpdateWorker
import me.vikas.newsapp.ui.screens.topheadline.TopHeadlineViewModel
import me.vikas.newsapp.ui.theme.NewsAppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkManagerRoute(
    //topHeadlineViewModel: TopHeadlineViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                Text(
                    text = "Work Manager Testing", style = MaterialTheme.typography.titleLarge.copy(
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
//            val context = LocalContext.current // ONLY here
//           Button(onClick = {
//               topHeadlineViewModel.startWorkManager(context)
//           }) {
//               Text(text = "Start Work Manager")
//           }
        }
    }
}

