package me.vikas.newsapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


// ─────────────────────────────────────────────
// Raw Colors  (mirrors your colors.xml)
// ─────────────────────────────────────────────
object NewsAppColors {
    val PrimaryColor        = Color(0xFF124559)   // #124559
    val PrimaryVariant      = Color(0xFF01161E)   // #01161E
    val SecondaryColor      = Color(0xFFAEC3B0)   // #AEC3B0

    val Black               = Color(0xFF000000)   // #FF000000
    val White               = Color(0xFFFFFFFF)   // #FFFFFFFF

    // Derived surface/background tones built from your palette
    val SurfaceLight        = Color(0xFFF4F8F5)   // very light tint of secondaryColor
    val SurfaceDark         = Color(0xFF0D2A35)   // mid-point between primary & primaryVariant
    val OnPrimaryLight      = Color(0xFFFFFFFF)   // white text on primary
    val OnPrimaryDark       = Color(0xFFFFFFFF)   // white text on dark primary too
    val PrimaryContainer    = Color(0xFFCCDFE3)   // light tint of primary for containers
    val OnPrimaryContainer  = Color(0xFF01161E)   // primaryVariant for text on containers
    val SecondaryContainer  = Color(0xFFD6E8D8)   // lighter shade of secondaryColor
    val OnSecondaryContainer= Color(0xFF124559)   // primaryColor on secondary containers
}

// ─────────────────────────────────────────────
// Light Color Scheme
// ─────────────────────────────────────────────
private val LightColorScheme = lightColorScheme(
    primary              = NewsAppColors.PrimaryColor,
    onPrimary            = NewsAppColors.OnPrimaryLight,
    primaryContainer     = NewsAppColors.PrimaryContainer,
    onPrimaryContainer   = NewsAppColors.OnPrimaryContainer,

    secondary            = NewsAppColors.SecondaryColor,
    onSecondary          = NewsAppColors.PrimaryVariant,
    secondaryContainer   = NewsAppColors.SecondaryContainer,
    onSecondaryContainer = NewsAppColors.OnSecondaryContainer,

    background           = NewsAppColors.SurfaceLight,
    onBackground         = NewsAppColors.Black,

    surface              = NewsAppColors.White,
    onSurface            = NewsAppColors.PrimaryVariant,
    onSurfaceVariant     = NewsAppColors.PrimaryColor,

    inverseSurface       = NewsAppColors.PrimaryVariant,
    inverseOnSurface     = NewsAppColors.White,
    inversePrimary       = NewsAppColors.SecondaryColor,
)

// ─────────────────────────────────────────────
// Dark Color Scheme
// ─────────────────────────────────────────────
private val DarkColorScheme = darkColorScheme(
    primary              = NewsAppColors.SecondaryColor,      // muted green as primary in dark
    onPrimary            = NewsAppColors.PrimaryVariant,
    primaryContainer     = NewsAppColors.PrimaryColor,
    onPrimaryContainer   = NewsAppColors.White,

    secondary            = NewsAppColors.PrimaryColor,
    onSecondary          = NewsAppColors.White,
    secondaryContainer   = NewsAppColors.SurfaceDark,
    onSecondaryContainer = NewsAppColors.SecondaryColor,

    background           = NewsAppColors.PrimaryVariant,
    onBackground         = NewsAppColors.White,

    surface              = NewsAppColors.SurfaceDark,
    onSurface            = NewsAppColors.White,
    onSurfaceVariant     = NewsAppColors.SecondaryColor,

    inverseSurface       = NewsAppColors.White,
    inverseOnSurface     = NewsAppColors.PrimaryVariant,
    inversePrimary       = NewsAppColors.PrimaryColor,
)

// ─────────────────────────────────────────────
// NewsAppTheme  — drop-in replacement for the
// default generated Theme.kt
// ─────────────────────────────────────────────
@Composable
fun NewsAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = MaterialTheme.typography,   // keep default M3 typography; swap if needed
        content     = content
    )
}