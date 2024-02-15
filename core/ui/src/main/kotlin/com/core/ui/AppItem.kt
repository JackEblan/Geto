package com.core.ui

import android.graphics.drawable.Drawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun AppItem(
    modifier: Modifier = Modifier,
    icon: Drawable?,
    packageName: String,
    label: String,
    onItemClick: (String, String) -> Unit
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .testTag("applist:appItem")
        .clickable {
            onItemClick(
                packageName, label
            )
        }
        .padding(10.dp), verticalAlignment = Alignment.CenterVertically) {

        AsyncImage(
            model = icon, contentDescription = null, modifier = Modifier.size(50.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label, style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = packageName, style = MaterialTheme.typography.bodySmall
            )
        }
    }
}