package com.core.designsystem.component

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun GetoLabeledRadioButton(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedRadioOptionIndex: () -> Int,
    onRadioOptionSelected: (Int) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    items.forEachIndexed { index, text ->
        Row(
            modifier
                .padding(vertical = 10.dp)
                .selectable(selected = (index == selectedRadioOptionIndex()),
                            role = Role.RadioButton,
                            interactionSource = interactionSource,
                            indication = null,
                            enabled = true,
                            onClick = { onRadioOptionSelected(index) })
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
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