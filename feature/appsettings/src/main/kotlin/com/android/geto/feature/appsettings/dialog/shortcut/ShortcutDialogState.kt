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

package com.android.geto.feature.appsettings.dialog.shortcut

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.setValue
import androidx.core.graphics.drawable.toBitmap
import com.android.geto.core.model.TargetShortcutInfoCompat

@Stable
class ShortcutDialogState {
    var showDialog by mutableStateOf(false)
        private set

    var icon by mutableStateOf<Bitmap?>(null)
        private set

    var shortLabel by mutableStateOf("")
        private set

    var shortLabelError by mutableStateOf("")
        private set

    var longLabel by mutableStateOf("")
        private set

    var longLabelError by mutableStateOf("")
        private set

    fun updateShowDialog(value: Boolean) {
        showDialog = value
    }

    fun updateIcon(value: Drawable?) {
        icon = value?.toBitmap()
    }

    fun updateShortLabel(value: String) {
        shortLabel = value
    }

    fun updateLongLabel(value: String) {
        longLabel = value
    }

    fun resetState() {
        showDialog = false
        longLabel = ""
        shortLabel = ""
    }

    fun getShortcut(packageName: String): TargetShortcutInfoCompat? {
        shortLabelError = if (shortLabel.isBlank()) "Short label is blank" else ""

        longLabelError = if (longLabel.isBlank()) "Long label is blank" else ""

        return if (shortLabelError.isBlank() && longLabelError.isBlank()) {
            TargetShortcutInfoCompat(
                icon = icon, id = packageName, shortLabel = shortLabel, longLabel = longLabel
            )
        } else {
            null
        }
    }

    companion object {
        val Saver = listSaver<ShortcutDialogState, Any>(save = { state ->
            listOf(
                state.showDialog,
                state.shortLabel,
                state.shortLabelError,
                state.longLabel,
                state.longLabelError
            )
        }, restore = {
            ShortcutDialogState().apply {
                showDialog = it[0] as Boolean

                shortLabel = it[1] as String

                shortLabelError = it[2] as String

                longLabel = it[3] as String

                longLabelError = it[4] as String
            }
        })
    }
}