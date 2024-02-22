/*
 *
 *   Copyright 2023 Einstein Blanco
 *
 *   Licensed under the GNU General Public License v3.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.gnu.org/licenses/gpl-3.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.android.geto.core.designsystem.component

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