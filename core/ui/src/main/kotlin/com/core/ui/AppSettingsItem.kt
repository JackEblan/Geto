package com.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppSettingsItem(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    label: String,
    settingsTypeLabel: String,
    key: String,
    safeToWrite: Boolean,
    onUserAppSettingsItemCheckBoxChange: (Boolean) -> Unit,
    onDeleteUserAppSettingsItem: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = enabled, onCheckedChange = {
            onUserAppSettingsItemCheckBoxChange(it)
        })

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                color = if (safeToWrite) Color.Unspecified else MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = settingsTypeLabel, style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = key, style = MaterialTheme.typography.bodySmall
            )
        }

        IconButton(onClick = { onDeleteUserAppSettingsItem() }) {
            Icon(
                imageVector = Icons.Default.Delete, contentDescription = null
            )
        }
    }
}