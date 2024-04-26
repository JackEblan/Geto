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

internal class DefaultShortcutRepository @Inject constructor(
    private val shortcutManagerCompatWrapper: ShortcutManagerCompatWrapper,
) : ShortcutRepository {

    override fun requestPinShortcut(
        packageName: String,
        appName: String,
        targetShortcutInfoCompat: TargetShortcutInfoCompat,
    ): ShortcutResult {
        if (shortcutManagerCompatWrapper.isRequestPinShortcutSupported().not()) {
            return ShortcutResult.UnsupportedLauncher
        }

        val requestPinShortcutSuccess = shortcutManagerCompatWrapper.requestPinShortcut(
            packageName = packageName,
            appName = appName,
            targetShortcutInfoCompat = targetShortcutInfoCompat,
        )

        return if (requestPinShortcutSuccess) {
            ShortcutResult.SupportedLauncher
        } else {
            ShortcutResult.UnsupportedLauncher
        }
    }

    override fun updateRequestPinShortcut(
        packageName: String,
        appName: String,
        targetShortcutInfoCompat: TargetShortcutInfoCompat,
    ): ShortcutResult {
        val shortcutIds =
            shortcutManagerCompatWrapper.getShortcuts(ShortcutManagerCompat.FLAG_MATCH_PINNED)
                .map { it.id }

        if (shortcutIds.contains(targetShortcutInfoCompat.id).not()) {
            return ShortcutResult.IDNotFound
        }

        return try {
            val updateShortcutsSuccess = shortcutManagerCompatWrapper.updateShortcuts(
                packageName = packageName,
                appName = appName,
                targetShortcutInfoCompat = targetShortcutInfoCompat,
            )

            if (updateShortcutsSuccess) {
                ShortcutResult.ShortcutUpdateSuccess
            } else {
                ShortcutResult.ShortcutUpdateFailed
            }
        } catch (e: IllegalArgumentException) {
            ShortcutResult.ShortcutUpdateImmutableShortcuts
        }
    }

    override fun getShortcut(id: String): ShortcutResult {
        val targetShortcutInfoCompat =
            shortcutManagerCompatWrapper.getShortcuts(ShortcutManagerCompat.FLAG_MATCH_PINNED)
                .find { it.id == id }

        return if (targetShortcutInfoCompat != null) {
            ShortcutResult.ShortcutFound(
                targetShortcutInfoCompat = targetShortcutInfoCompat,
            )
        } else {
            ShortcutResult.NoShortcutFound
        }
    }
}
