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

package com.android.geto.core.testing.repository

import android.graphics.Bitmap
import com.android.geto.core.data.repository.ShortcutRepository
import com.android.geto.core.data.repository.ShortcutResult
import com.android.geto.core.model.TargetShortcutInfoCompat

class TestShortcutRepository : ShortcutRepository {

    private var requestPinShortcutSupported = false

    private var targetShortcutInfoCompats = listOf<TargetShortcutInfoCompat>()

    private var updateImmutableShortcuts = false

    override fun requestPinShortcut(
        icon: Bitmap?, targetShortcutInfoCompat: TargetShortcutInfoCompat,
    ): ShortcutResult {
        return if (requestPinShortcutSupported) {
            ShortcutResult.SupportedLauncher
        } else ShortcutResult.UnsupportedLauncher
    }

    override fun updateRequestPinShortcut(
        icon: Bitmap?, targetShortcutInfoCompat: TargetShortcutInfoCompat,
    ): ShortcutResult {
        return if (updateImmutableShortcuts) {
            ShortcutResult.ShortcutUpdateImmutableShortcuts
        } else ShortcutResult.ShortcutUpdateSuccess
    }

    override fun getShortcut(id: String): ShortcutResult {
        val shortcutInfoCompat = targetShortcutInfoCompats.find { it.id == id }

        return if (shortcutInfoCompat != null) {
            ShortcutResult.ShortcutFound(
                targetShortcutInfoCompat = TargetShortcutInfoCompat(
                    shortLabel = shortcutInfoCompat.shortLabel.toString(),
                    longLabel = shortcutInfoCompat.longLabel.toString(),
                ),
            )
        } else {
            ShortcutResult.NoShortcutFound
        }
    }

    fun setRequestPinShortcutSupported(value: Boolean) {
        requestPinShortcutSupported = value
    }

    fun setShortcuts(value: List<TargetShortcutInfoCompat>) {
        targetShortcutInfoCompats = value
    }

    fun setUpdateImmutableShortcuts(value: Boolean) {
        updateImmutableShortcuts = value
    }
}