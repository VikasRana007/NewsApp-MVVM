package me.vikas.newsapp.ui.screens.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import me.vikas.newsapp.ui.theme.NewsAppColors
import me.vikas.newsapp.ui.theme.NewsAppTheme
import kotlin.math.cos
import kotlin.math.sin

// Orbiting News Icons Ring
@Composable
fun OrbitingNewsIcons(visible: Boolean) {
    data class OrbitItem(
        val icon: ImageVector, val label: String, val angleDeg: Float, val radius: Dp
    )

    val orbitItems = listOf(
        OrbitItem(Icons.Default.Newspaper, "Headlines", 0f, 90.dp),
        OrbitItem(Icons.Default.Public, "World", 72f, 90.dp),
        OrbitItem(Icons.AutoMirrored.Filled.TrendingUp, "Trending", 144f, 90.dp),
        OrbitItem(Icons.Default.Translate, "Language", 216f, 90.dp),
        OrbitItem(Icons.Default.Search, "Search", 288f, 90.dp),
    )

    // Continuous slow rotation of the whole ring
    val infiniteTransition = rememberInfiniteTransition(label = "orbit")
    val ringRotation by infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 360f, animationSpec = infiniteRepeatable(
            animation = tween(12000, easing = LinearEasing), repeatMode = RepeatMode.Restart
        ), label = "ringRotation"
    )

    val density = LocalDensity.current  // ← hoist it here, outside forEachIndexed

    orbitItems.forEachIndexed { index, item ->
        val angleDeg = item.angleDeg + ringRotation
        val angleRad = Math.toRadians(angleDeg.toDouble())

        // ✅ Use hoisted density, not `with(LocalDensity)` inside lambda
        val radiusPx = with(density) { item.radius.toPx() }
        val offsetX = (radiusPx * cos(angleRad)).toFloat().dp
        val offsetY = (radiusPx * sin(angleRad)).toFloat().dp

        // Staggered appear delay per icon
        var iconVisible by remember { mutableStateOf(false) }
        LaunchedEffect(visible) {
            if (visible) {
                delay(index * 100L)
                iconVisible = true
            }
        }

        AnimatedVisibility(
            visible = iconVisible,
            enter = scaleIn(tween(400, easing = EaseOutBack)) + fadeIn(tween(400)),
            modifier = Modifier.offset(x = offsetX, y = offsetY)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(NewsAppColors.SecondaryColor.copy(alpha = 0.18f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.label,
                    tint = NewsAppColors.SecondaryColor,
                    modifier = Modifier
                        .size(20.dp)
                        // Counter-rotate icon so it stays upright while ring spins
                        .rotate(-ringRotation)
                )
            }
        }
    }
}

// Center Pulsing Logo Icon
@Composable
fun CenterLogoIcon(visible: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.08f, animationSpec = infiniteRepeatable(
            animation = tween(900, easing = EaseInOutSine), repeatMode = RepeatMode.Reverse
        ), label = "pulse"
    )

    var centerVisible by remember { mutableStateOf(false) }
    LaunchedEffect(visible) { if (visible) centerVisible = true }

    AnimatedVisibility(
        visible = centerVisible,
        enter = scaleIn(tween(500, easing = EaseOutBack)) + fadeIn(tween(400))
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .scale(pulseScale)
                .clip(RoundedCornerShape(22.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            NewsAppColors.SecondaryColor,
                            NewsAppColors.SecondaryColor.copy(alpha = 0.7f)
                        )
                    )
                ), contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Newspaper,
                contentDescription = "News Logo",
                tint = NewsAppColors.PrimaryVariant,
                modifier = Modifier.size(44.dp)
            )
        }
    }
}

// Three Animated Loading Dots
@Composable
fun LoadingDots() {
    val infiniteTransition = rememberInfiniteTransition(label = "dots")

    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        listOf(0, 200, 400).forEach { delayMs ->
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0.2f, targetValue = 1f, animationSpec = infiniteRepeatable(
                    animation = tween(600, delayMillis = delayMs, easing = EaseInOutSine),
                    repeatMode = RepeatMode.Reverse
                ), label = "dotAlpha$delayMs"
            )
            val scale by infiniteTransition.animateFloat(
                initialValue = 0.7f, targetValue = 1f, animationSpec = infiniteRepeatable(
                    animation = tween(600, delayMillis = delayMs, easing = EaseInOutSine),
                    repeatMode = RepeatMode.Reverse
                ), label = "dotScale$delayMs"
            )
            Box(
                modifier = Modifier
                    .size(9.dp)
                    .scale(scale)
                    .alpha(alpha)
                    .clip(CircleShape)
                    .background(NewsAppColors.SecondaryColor)
            )
        }
    }
}

// Decorative blurred ambient circles (depth)
@Composable
fun DecorativeCircles(visible: Boolean) {
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f, animationSpec = tween(800), label = "bgCircles"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(alpha)
    ) {
        // Top-right ambient glow
        Box(
            modifier = Modifier
                .size(280.dp)
                .offset(x = 80.dp, y = (-60).dp)
                .clip(CircleShape)
                .background(NewsAppColors.PrimaryColor.copy(alpha = 0.5f))
                .blur(60.dp)
                .align(Alignment.TopEnd)
        )
        // Bottom-left ambient glow
        Box(
            modifier = Modifier
                .size(220.dp)
                .offset(x = (-50).dp, y = 60.dp)
                .clip(CircleShape)
                .background(NewsAppColors.SecondaryColor.copy(alpha = 0.1f))
                .blur(50.dp)
                .align(Alignment.BottomStart)
        )
    }
}

// Preview
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    NewsAppTheme {
        SplashScreen()
    }
}