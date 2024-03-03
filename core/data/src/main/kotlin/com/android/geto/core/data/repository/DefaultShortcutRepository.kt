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

import androidx.core.content.pm.ShortcutManagerCompat
import com.android.geto.core.model.TargetShortcutInfoCompat
import com.android.geto.core.shortcutmanager.ShortcutManagerCompatWrapper
import javax.inject.Inject

class DefaultShortcutRepository @Inject constructor(
    private val shortcutManagerCompatWrapper: ShortcutManagerCompatWrapper
) : ShortcutRepository {

    override fun requestPinShortcut(targetShortcutInfoCompat: TargetShortcutInfoCompat): Boolean {
        return if (shortcutManagerCompatWrapper.isRequestPinShortcutSupported()) {
            shortcutManagerCompatWrapper.requestPinShortcut(
                icon = targetShortcutInfoCompat.icon,
                id = targetShortcutInfoCompat.id!!,
                shortLabel = targetShortcutInfoCompat.shortLabel!!,
                longLabel = targetShortcutInfoCompat.longLabel!!,
                intent = targetShortcutInfoCompat.intent!!
            )
        } else false
    }

    override fun updateRequestPinShortcut(targetShortcutInfoCompat: TargetShortcutInfoCompat): Result<Boolean> {
        return runCatching {
            val ids =
                shortcutManagerCompatWrapper.getShortcuts(ShortcutManagerCompat.FLAG_MATCH_PINNED)
                    .map { it.id }

            if (targetShortcutInfoCompat.id in ids) {
                shortcutManagerCompatWrapper.updateShortcuts(
                    icon = targetShortcutInfoCompat.icon,
                    id = targetShortcutInfoCompat.id!!,
                    shortLabel = targetShortcutInfoCompat.shortLabel!!,
                    longLabel = targetShortcutInfoCompat.longLabel!!,
                    intent = targetShortcutInfoCompat.intent!!
                )
            } else {
                false
            }
        }
    }

    override fun enableShortcuts(id: String, enabled: Boolean): Result<String> {
        return runCatching {
            if (enabled) {
                shortcutManagerCompatWrapper.enableShortcuts(id)
                "Shortcut enabled"
            } else {
                shortcutManagerCompatWrapper.disableShortcuts(id)
                "Shortcut disabled"
            }
        }
    }

    override fun getShortcut(id: String): TargetShortcutInfoCompat? {
        val shortcutInfoCompat =
            shortcutManagerCompatWrapper.getShortcuts(ShortcutManagerCompat.FLAG_MATCH_PINNED)
                .find { it.id == id }

        return if (shortcutInfoCompat != null) {
            TargetShortcutInfoCompat(
                shortLabel = shortcutInfoCompat.shortLabel.toString(),
                longLabel = shortcutInfoCompat.longLabel.toString()
            )
        } else {
            null
        }
    }
}