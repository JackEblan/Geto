package com.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.dp

@Composable
fun UserAppSettingsItem(
    modifier: Modifier = Modifier,
    enabled: () -> Boolean,
    label: () -> String,
    settingsTypeLabel: () -> String,
    key: () -> String,
    onUserAppSettingsItemCheckBoxChange: (Boolean) -> Unit,
    onDeleteUserAppSettingsItem: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = enabled(), onCheckedChange = {
            onUserAppSettingsItemCheckBoxChange(it)
        })

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label(), style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = settingsTypeLabel(), style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = key(), style = MaterialTheme.typography.bodySmall
            )
        }

        IconButton(onClick = { onDeleteUserAppSettingsItem() }) {
            Icon(
                imageVector = Icons.Default.Delete, contentDescription = null
            )
        }
    }
}