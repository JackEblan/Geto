package com.android.geto.core.ui

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
import com.android.geto.core.model.SecureSettings

@Composable
fun SecureSettingsItem(
    modifier: Modifier = Modifier,
    secureSettings: SecureSettings,
    onItemClick: (String) -> Unit,
) {
    Column(modifier = modifier
        .clickable { onItemClick(secureSettings.name!!) }
        .padding(10.dp)
        .fillMaxWidth()) {
        Text(
            text = secureSettings.name!!, style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = secureSettings.value ?: "", style = MaterialTheme.typography.bodySmall
        )
    }
}