package com.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.core.model.SecureSettings

@Composable
fun SecureSettingsItem(
    modifier: Modifier = Modifier,
    secureSetting: () -> SecureSettings,
    onItemClick: (String?) -> Unit,
) {
    Column(modifier = modifier
        .clickable { onItemClick(secureSetting().name) }
        .padding(10.dp)
        .fillMaxWidth()) {
        Text(
            text = secureSetting().name.toString(), style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = secureSetting().value.toString(), style = MaterialTheme.typography.bodySmall
        )
    }
}