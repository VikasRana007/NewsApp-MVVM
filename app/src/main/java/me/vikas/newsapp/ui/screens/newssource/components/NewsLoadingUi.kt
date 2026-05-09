package me.vikas.newsapp.ui.screens.newssource.components

import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Source
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.vikas.newsapp.ui.theme.NewsAppColors

// Animated Loading State — 3 bouncing dots
@Composable
fun NewsSourceLoadingState(loadingMessage: String) {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val imageVector: ImageVector = when (loadingMessage) {
            "Fetching Sources..." -> {
                Icons.Default.Source
            }
            "Fetching Countries..." -> {
                Icons.Default.Public
            }
            "Fetching Language..." -> {
                Icons.Default.Language
            }
            "News Searching..." -> {
                Icons.Default.Search
            }
            "Fetching Top Headlines..." -> {
                Icons.Default.Newspaper
            }
            else -> {
                Icons.Default.Newspaper
            }
        }

        Icon(
            imageVector = imageVector,
            contentDescription = null,
            tint = NewsAppColors.PrimaryColor.copy(alpha = 0.5f),
            modifier = Modifier.size(52.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(0, 180, 360).forEach { delayMs ->
                val alpha by infiniteTransition.animateFloat(
                    initialValue = 0.2f, targetValue = 1f, animationSpec = infiniteRepeatable(
                        animation = tween(600, delayMillis = delayMs, easing = EaseInOutSine),
                        repeatMode = RepeatMode.Reverse
                    ), label = "dotAlpha$delayMs"
                )
                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .clip(CircleShape)
                        .background(NewsAppColors.PrimaryColor.copy(alpha = alpha))
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = loadingMessage,
            fontSize = 13.sp,
            color = NewsAppColors.PrimaryVariant.copy(alpha = 0.5f),
            fontWeight = FontWeight.Light
        )
    }
}