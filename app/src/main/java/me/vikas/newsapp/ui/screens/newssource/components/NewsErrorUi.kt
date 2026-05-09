package me.vikas.newsapp.ui.screens.newssource.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.vikas.newsapp.ui.theme.NewsAppColors

// Error State with Retry Button
@Composable
fun NewsSourceErrorState(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = null,
            tint = NewsAppColors.PrimaryColor.copy(alpha = 0.4f),
            modifier = Modifier.size(56.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Something went wrong",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = NewsAppColors.PrimaryVariant
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = errorMessage,
            fontSize = 12.sp,
            color = NewsAppColors.PrimaryVariant.copy(alpha = 0.45f),
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onRetry,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = NewsAppColors.PrimaryColor
            ),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 12.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
                tint = NewsAppColors.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Retry", color = NewsAppColors.White, fontWeight = FontWeight.SemiBold
            )
        }
    }
}
