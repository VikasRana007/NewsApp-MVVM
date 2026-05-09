package me.vikas.newsapp.ui.screens.languagewisenews.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Translate
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
import me.vikas.newsapp.data.model.languagenews.Language
import me.vikas.newsapp.ui.theme.NewsAppColors

@Composable
fun LanguageItem(
    language: Language,
    onLanguageClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 5.dp)
            .clickable { onLanguageClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = NewsAppColors.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 40.dp)   // minHeight = 40dp like XML
                .padding(horizontal = 8.dp, vertical = 16.dp), // margin 8dp + marginTop 16dp
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // ── Left: language_name (start aligned) ──
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Language icon box
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(NewsAppColors.PrimaryColor.copy(alpha = 0.08f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Translate,
                        contentDescription = null,
                        tint = NewsAppColors.PrimaryColor,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // language_name — 16sp, primaryColor, maxLines 2
                Text(
                    text = language.languageName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = NewsAppColors.PrimaryColor,  // textColor="@color/primaryColor"
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // ── Right: language_code + chevron ────────
            // language_code — 14sp, primaryColor, end aligned
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Language code chip
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(NewsAppColors.SecondaryColor.copy(alpha = 0.30f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = language.languageCode.uppercase(),
                        fontSize = 14.sp,               // 14sp like XML language_code
                        fontWeight = FontWeight.Medium,
                        color = NewsAppColors.PrimaryColor  // textColor="@color/primaryColor"
                    )
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = null,
                    tint = NewsAppColors.PrimaryColor.copy(alpha = 0.4f),
                    modifier = Modifier.size(13.dp)
                )
            }
        }
    }
}