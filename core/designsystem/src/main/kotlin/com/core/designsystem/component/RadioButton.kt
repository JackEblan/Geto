package com.core.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun GetoRadioButton(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedRadioOptionIndex: () -> Int,
    onRadioOptionSelected: (Int) -> Unit
) {
    Column(modifier.selectableGroup()) {
        items.forEachIndexed { index, text ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .selectable(
                        selected = (index == selectedRadioOptionIndex()),
                        onClick = { onRadioOptionSelected(index) },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (index == selectedRadioOptionIndex()), onClick = null
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}