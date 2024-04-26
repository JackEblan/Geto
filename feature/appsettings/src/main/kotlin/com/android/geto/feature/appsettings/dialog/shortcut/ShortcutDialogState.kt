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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.graphics.drawable.toBitmap
import com.android.geto.core.model.TargetShortcutInfoCompat

@Composable
internal fun rememberShortcutDialogState(): ShortcutDialogState {
    return rememberSaveable(saver = ShortcutDialogState.Saver) {
        ShortcutDialogState()
    }
}

@Stable
internal class ShortcutDialogState {
    var showDialog by mutableStateOf(false)
        private set

    var icon by mutableStateOf<Bitmap?>(null)
        private set

    var shortLabel by mutableStateOf("")
        private set

    var showShortLabelError by mutableStateOf(false)
        private set

    var longLabel by mutableStateOf("")
        private set

    var showLongLabelError by mutableStateOf(false)
        private set

    val shortLabelMaxLength = 10

    val longLabelMaxLength = 25

    fun updateShowDialog(value: Boolean) {
        showDialog = value
    }

    fun updateIcon(value: Drawable?) {
        icon = value?.toBitmap()
    }

    fun updateShortLabel(value: String) {
        if (value.length <= shortLabelMaxLength) {
            shortLabel = value
        }
    }

    fun updateLongLabel(value: String) {
        if (value.length <= longLabelMaxLength) {
            longLabel = value
        }
    }

    fun resetState() {
        showDialog = false
        longLabel = ""
        shortLabel = ""
    }

    fun getShortcut(packageName: String): TargetShortcutInfoCompat? {
        showShortLabelError = shortLabel.isBlank()

        showLongLabelError = longLabel.isBlank()

        return if (showShortLabelError.not() && showLongLabelError.not()) {
            TargetShortcutInfoCompat(
                id = packageName,
                icon = icon,
                shortLabel = shortLabel,
                longLabel = longLabel,
            )
        } else {
            null
        }
    }

    companion object {
        val Saver = listSaver<ShortcutDialogState, Any>(
            save = { state ->
                listOf(
                    state.showDialog,
                    state.shortLabel,
                    state.showShortLabelError,
                    state.longLabel,
                    state.showLongLabelError,
                )
            },
            restore = {
                ShortcutDialogState().apply {
                    showDialog = it[0] as Boolean

                    shortLabel = it[1] as String

                    showShortLabelError = it[2] as Boolean

                    longLabel = it[3] as String

                    showLongLabelError = it[4] as Boolean
                }
            },
        )
    }
}
