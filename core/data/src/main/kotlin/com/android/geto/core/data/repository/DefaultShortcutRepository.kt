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
import androidx.core.content.pm.ShortcutManagerCompat
import com.android.geto.core.model.TargetShortcutInfoCompat
import com.android.geto.core.shortcutmanager.ShortcutManagerCompatWrapper
import javax.inject.Inject

class DefaultShortcutRepository @Inject constructor(
    private val shortcutManagerCompatWrapper: ShortcutManagerCompatWrapper
) : ShortcutRepository {

    override fun requestPinShortcut(
        icon: Bitmap?, targetShortcutInfoCompat: TargetShortcutInfoCompat
    ): ShortcutResult {
        if (shortcutManagerCompatWrapper.isRequestPinShortcutSupported().not()) {
            return ShortcutResult.UnsupportedLauncher
        }

        val requestPinShortcutSuccess = shortcutManagerCompatWrapper.requestPinShortcut(
            icon = icon,
            id = targetShortcutInfoCompat.id!!,
            shortLabel = targetShortcutInfoCompat.shortLabel!!,
            longLabel = targetShortcutInfoCompat.longLabel!!,
            intent = targetShortcutInfoCompat.shortcutIntent!!
        )

        return if (requestPinShortcutSuccess) {
            ShortcutResult.SupportedLauncher
        } else ShortcutResult.UnsupportedLauncher
    }

    override fun updateRequestPinShortcut(
        icon: Bitmap?, targetShortcutInfoCompat: TargetShortcutInfoCompat
    ): ShortcutResult {
        val shortcutIds =
            shortcutManagerCompatWrapper.getShortcuts(ShortcutManagerCompat.FLAG_MATCH_PINNED)
                .map { it.id }

        if (shortcutIds.contains(targetShortcutInfoCompat.id).not()) {
            return ShortcutResult.IDNotFound
        }

        return try {
            val updateShortcutsSuccess = shortcutManagerCompatWrapper.updateShortcuts(
                icon = icon,
                id = targetShortcutInfoCompat.id!!,
                shortLabel = targetShortcutInfoCompat.shortLabel!!,
                longLabel = targetShortcutInfoCompat.longLabel!!,
                intent = targetShortcutInfoCompat.shortcutIntent!!
            )

            if (updateShortcutsSuccess) {
                ShortcutResult.ShortcutUpdateSuccess
            } else ShortcutResult.ShortcutUpdateFailed

        } catch (e: IllegalArgumentException) {
            ShortcutResult.ShortcutUpdateImmutableShortcuts
        }
    }

    override fun getShortcut(id: String): ShortcutResult {
        val shortcutInfoCompat =
            shortcutManagerCompatWrapper.getShortcuts(ShortcutManagerCompat.FLAG_MATCH_PINNED)
                .find { it.id == id }

        return if (shortcutInfoCompat != null) {
            ShortcutResult.ShortcutFound(
                targetShortcutInfoCompat = TargetShortcutInfoCompat(
                    shortLabel = shortcutInfoCompat.shortLabel.toString(),
                    longLabel = shortcutInfoCompat.longLabel.toString()
                )
            )
        } else {
            ShortcutResult.NoShortcutFound
        }
    }
}