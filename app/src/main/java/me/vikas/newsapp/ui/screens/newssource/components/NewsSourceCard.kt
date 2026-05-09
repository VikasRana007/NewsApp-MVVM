package me.vikas.newsapp.ui.screens.newssource.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Source
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.vikas.newsapp.data.model.news_source.Source
import me.vikas.newsapp.ui.theme.NewsAppColors


// Single Source Item Card
@Composable
fun NewsSourceItem(
    source: Source,
    onSourceClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 5.dp)
            .clickable { onSourceClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = NewsAppColors.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // ── Left: Icon + Source info ──────────
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(NewsAppColors.PrimaryColor.copy(alpha = 0.10f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Source,
                        contentDescription = null,
                        tint = NewsAppColors.PrimaryColor,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    // Source Name
                    Text(
                        text = source.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = NewsAppColors.PrimaryVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    // Category + Country + Language chips
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        SourceChip(label = source.category)
                        SourceChip(label = source.country.uppercase())
                        SourceChip(label = source.language.uppercase())
                    }
                }
            }

            // ── Right: Chevron
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = null,
                tint = NewsAppColors.PrimaryColor.copy(alpha = 0.5f),
                modifier = Modifier.size(14.dp)
            )
        }
    }
}


// Small chip — category / country / language
@Composable
fun SourceChip(label: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(NewsAppColors.SecondaryColor.copy(alpha = 0.30f))
            .padding(horizontal = 6.dp, vertical = 2.dp)
    ) {
        Text(
            text = label,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = NewsAppColors.PrimaryVariant
        )
    }
}