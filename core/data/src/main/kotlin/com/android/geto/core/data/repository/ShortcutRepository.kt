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

package com.android.geto.core.data.repository

import android.graphics.Bitmap
import com.android.geto.core.model.TargetShortcutInfoCompat

interface ShortcutRepository {
    fun requestPinShortcut(
        icon: Bitmap?, targetShortcutInfoCompat: TargetShortcutInfoCompat
    ): ShortcutResult

    fun updateRequestPinShortcut(
        icon: Bitmap?, targetShortcutInfoCompat: TargetShortcutInfoCompat
    ): ShortcutResult

    fun enableShortcuts(id: String, enabled: Boolean): ShortcutResult

    fun getShortcut(id: String): ShortcutResult
}

sealed interface ShortcutResult {
    data object UnsupportedLauncher : ShortcutResult

    data object SupportedLauncher : ShortcutResult

    data object IDNotFound : ShortcutResult

    data object ShortcutUpdateSuccess : ShortcutResult

    data object ShortcutUpdateFailed : ShortcutResult

    data object ShortcutUpdateImmutableShortcuts : ShortcutResult

    data object ShortcutDisableImmutableShortcuts : ShortcutResult

    data object UserIsLocked : ShortcutResult

    data object ShortcutEnable : ShortcutResult

    data object ShortcutDisable : ShortcutResult

    data class GetShortcut(
        val targetShortcutInfoCompat: TargetShortcutInfoCompat
    ) : ShortcutResult

    data object NoShortcut : ShortcutResult
}