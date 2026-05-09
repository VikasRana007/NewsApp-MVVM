package me.vikas.newsapp.ui.screens.dashboard

import androidx.compose.ui.graphics.vector.ImageVector

// Data model for each dashboard card
data class DashboardItem(
    val title: String, val icon: ImageVector, val onClick: () -> Unit = {}
)