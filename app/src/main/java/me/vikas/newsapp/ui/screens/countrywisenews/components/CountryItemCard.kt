package me.vikas.newsapp.ui.screens.countrywisenews.components

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
import me.vikas.newsapp.data.model.countrynews.Country
import me.vikas.newsapp.ui.theme.NewsAppColors


// Single Country Item Card => CardView + country_name TextView
@Composable
fun CountryItem(
    country: Country, onCountryClick: () -> Unit, modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 5.dp)
            .clickable { onCountryClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = NewsAppColors.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 40.dp)    // minHeight = 40dp like XML
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // ── Left: Flag emoji + Country Name ──
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Country flag emoji box
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(NewsAppColors.PrimaryColor.copy(alpha = 0.08f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = countryCodeToFlag(country.code), fontSize = 22.sp
                    )
                }

                // Country Name — bold, primaryColor (same as XML)
                Text(
                    text = country.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = NewsAppColors.PrimaryColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // ── Right: Country code chip + chevron ──
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(NewsAppColors.SecondaryColor.copy(alpha = 0.30f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = country.code.uppercase(),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = NewsAppColors.PrimaryVariant
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


// Country code → Flag Emoji converter
// "in" → 🇮🇳 , "us" → 🇺🇸 , "gb" → 🇬🇧
fun countryCodeToFlag(countryCode: String): String {
    return countryCode.uppercase()
        .map { char -> Character.toChars(char.code + 0x1F1A5).concatToString() }.joinToString("")
}