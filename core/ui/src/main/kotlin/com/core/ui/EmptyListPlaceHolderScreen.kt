package com.core.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun EmptyListPlaceHolderScreen(
    modifier: Modifier = Modifier, icon: ImageVector, text: String
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            colorFilter = ColorFilter.tint(
                MaterialTheme.colorScheme.onSurface
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = text)
    }
}