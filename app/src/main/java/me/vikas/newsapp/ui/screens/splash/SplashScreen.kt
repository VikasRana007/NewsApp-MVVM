package me.vikas.newsapp.ui.screens.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import me.vikas.newsapp.ui.theme.NewsAppColors

// Splash Screen  (2–3 sec, then calls onSplashFinished)
@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit = {}
) {
    // Animation state flags 
    var showBackground by remember { mutableStateOf(false) }
    var showOrbitIcons by remember { mutableStateOf(false) }
    var showCenterIcon by remember { mutableStateOf(false) }
    var showTitle by remember { mutableStateOf(false) }
    var showTagline by remember { mutableStateOf(false) }
    var showDots by remember { mutableStateOf(false) }

    //  Sequenced entry  (total ~2.6 s before navigate)
    LaunchedEffect(Unit) {
        showBackground = true
        delay(200)
        showOrbitIcons = true
        delay(300)
        showCenterIcon = true
        delay(250)
        showTitle = true
        delay(200)
        showTagline = true
        delay(300)
        showDots = true
        delay(1200)   // wait for loading dots to finish
        onSplashFinished()
    }

    // Background gradient
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        NewsAppColors.PrimaryColor, NewsAppColors.PrimaryVariant
                    ), radius = 1200f
                )
            ), contentAlignment = Alignment.Center
    ) {

        // Decorative blurred background circles ─
        DecorativeCircles(visible = showBackground)

        //  Content column 
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {

            //  Orbiting news icons ring 
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier.size(220.dp)
            ) {
                // Outer ring: orbiting icons
                OrbitingNewsIcons(visible = showOrbitIcons)

                // Center pulsing logo icon
                CenterLogoIcon(visible = showCenterIcon)
            }

            Spacer(modifier = Modifier.height(36.dp))

            //  App name 
            AnimatedVisibility(
                visible = showTitle, enter = slideInVertically(
                    initialOffsetY = { it }, animationSpec = tween(500, easing = EaseOutCubic)
                ) + fadeIn(tween(500))
            ) {
                Text(
                    text = "NewsWave",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = NewsAppColors.White,
                    letterSpacing = 3.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            //  Tagline
            AnimatedVisibility(
                visible = showTagline, enter = fadeIn(tween(600))
            ) {
                Text(
                    text = "Stay Informed. Stay Ahead.",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = NewsAppColors.SecondaryColor.copy(alpha = 0.85f),
                    letterSpacing = 1.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(52.dp))

            //  Loading dots
            AnimatedVisibility(
                visible = showDots, enter = fadeIn(tween(400))
            ) {
                LoadingDots()
            }
        }
    }
}
