package me.vikas.newsapp.ui.screens.offline_topheadline

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import me.vikas.newsapp.data.local.entity.topheadlines.ArticleEntity
import me.vikas.newsapp.ui.theme.NewsAppColors


// Root News Card  (CardView) as in View System

@Composable
fun NewsArticleCard(
    article: ArticleEntity, onClick: (url: String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 6.dp)
            .clickable {
                if (article.url.isNotEmpty()) {
                    onClick(article.url)
                }
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = NewsAppColors.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            //  Banner image (AppCompatImageView equivalent) 
            NewsArticleBanner(imageUrl = article.urlToImage)

            // All text fields below the image 
            NewsArticleTextSection(
                title = article.title,
                description = article.description,
                source = article.source.name,
                publishedAt = article.publishedAt
            )
        }
    }
}

// Banner Image Section
@Composable
fun NewsArticleBanner(
    imageUrl: String?, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(NewsAppColors.PrimaryColor.copy(alpha = 0.08f))
    ) {
        SubcomposeAsyncImage(
            model = imageUrl,
            contentDescription = "News Image",
            contentScale = ContentScale.Crop,   // centerCrop equivalent
            modifier = Modifier.fillMaxSize()
        ) {
            when (painter.state) {

                //  Loading shimmer placeholder 
                is AsyncImagePainter.State.Loading -> {
                    ShimmerBannerPlaceholder()
                }

                //  Error / null URL fallback
                is AsyncImagePainter.State.Error, is AsyncImagePainter.State.Empty -> {
                    BannerErrorPlaceholder()
                }

                //  Success — show the image
                else -> SubcomposeAsyncImageContent()
            }
        }

        // Subtle gradient scrim at bottom for text readability
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, Color.Black.copy(alpha = 0.25f)
                        )
                    )
                )
        )
    }
}

// Shimmer placeholder while image loads
@Composable
fun ShimmerBannerPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        NewsAppColors.PrimaryColor.copy(alpha = 0.08f),
                        NewsAppColors.PrimaryColor.copy(alpha = 0.18f),
                        NewsAppColors.PrimaryColor.copy(alpha = 0.08f),
                    )
                )
            )
    )
}

// Error / no image fallback
@Composable
fun BannerErrorPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NewsAppColors.PrimaryColor.copy(alpha = 0.06f)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Newspaper,
                contentDescription = null,
                tint = NewsAppColors.PrimaryColor.copy(alpha = 0.35f),
                modifier = Modifier.size(48.dp)
            )
            Text(
                text = "No Image Available",
                fontSize = 12.sp,
                color = NewsAppColors.PrimaryColor.copy(alpha = 0.4f)
            )
        }
    }
}

// Text Section
// Equivalent of the three AppCompatTextViews:
//   title (cabin_bold, 16sp)
//   description (light_thin, 14sp)
//   source (light_thin, 12sp)
@Composable
fun NewsArticleTextSection(
    title: String, description: String?, source: String?, publishedAt: String? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(top = 4.dp, bottom = 8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        //  Title  (cabin_bold equivalent → FontWeight.Bold) 
        NewsArticleTitle(title = title)

        //  Description (light_thin equivalent → FontWeight.Light) 
        description?.let {
            NewsArticleDescription(description = it)
        }

        //  Source + published date row ───────────────────────
        NewsArticleSourceRow(source = source, publishedAt = publishedAt)
    }
}

// Title  — android:fontFamily="@font/cabin_bold"
@Composable
fun NewsArticleTitle(
    title: String, modifier: Modifier = Modifier
) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,          // cabin_bold equivalent
        color = NewsAppColors.PrimaryVariant,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier.fillMaxWidth()
    )
}


// Description  — android:fontFamily="@font/light_thin"

@Composable
fun NewsArticleDescription(
    description: String, modifier: Modifier = Modifier
) {
    Text(
        text = description,
        fontSize = 14.sp,
        fontWeight = FontWeight.Light,         // light_thin equivalent
        color = NewsAppColors.PrimaryVariant.copy(alpha = 0.75f),
        maxLines = 3,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier.fillMaxWidth()
    )
}


// Source row  — android:fontFamily="@font/light_thin", 12sp
// Enhanced: source chip + published date

@Composable
fun NewsArticleSourceRow(
    source: String?, publishedAt: String?, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Source chip
        source?.let {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(NewsAppColors.PrimaryColor.copy(alpha = 0.10f))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = it,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light,
                    color = NewsAppColors.PrimaryColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Published date (optional)
        publishedAt?.let {
            Text(
                text = it,
                fontSize = 11.sp,
                fontWeight = FontWeight.Light,
                color = NewsAppColors.PrimaryVariant.copy(alpha = 0.45f)
            )
        }
    }
}