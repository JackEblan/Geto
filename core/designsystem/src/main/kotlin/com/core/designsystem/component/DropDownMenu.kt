package com.core.designsystem.component

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun GetoDropDownMenu(
    dropDownExpanded: Boolean,
    onDismissRequest: () -> Unit,
    dropdownItems: List<String>,
    onDropDownMenuItemSelected: (Int) -> Unit
) {
    DropdownMenu(expanded = dropDownExpanded, onDismissRequest = onDismissRequest) {
        dropdownItems.mapIndexed { index, label ->
            DropdownMenuItem(text = { Text(label) }, onClick = {
                onDropDownMenuItemSelected(index)
            })
        }
    }
}