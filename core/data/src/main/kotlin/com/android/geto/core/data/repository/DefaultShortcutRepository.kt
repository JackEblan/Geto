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
import com.android.geto.core.data.R
import com.android.geto.core.model.MappedShortcutInfoCompat
import com.android.geto.core.resources.ResourcesWrapper
import com.android.geto.core.shortcutmanager.ShortcutManagerCompatWrapper
import javax.inject.Inject

internal class DefaultShortcutRepository @Inject constructor(
    private val shortcutManagerCompatWrapper: ShortcutManagerCompatWrapper,
    private val resourcesWrapper: ResourcesWrapper,
) : ShortcutRepository {

    override fun requestPinShortcut(
        packageName: String,
        appName: String,
        mappedShortcutInfoCompat: MappedShortcutInfoCompat,
    ): String {
        if (shortcutManagerCompatWrapper.isRequestPinShortcutSupported().not()) {
            return resourcesWrapper.getString(R.string.unsupported_launcher)
        }

        val requestPinShortcutSuccess = shortcutManagerCompatWrapper.requestPinShortcut(
            packageName = packageName,
            appName = appName,
            mappedShortcutInfoCompat = mappedShortcutInfoCompat,
        )

        return if (requestPinShortcutSuccess) {
            resourcesWrapper.getString(R.string.supported_launcher)
        } else {
            resourcesWrapper.getString(R.string.unsupported_launcher)
        }
    }

    override fun updateRequestPinShortcut(
        packageName: String,
        appName: String,
        mappedShortcutInfoCompat: MappedShortcutInfoCompat,
    ): String {
        val shortcutIds =
            shortcutManagerCompatWrapper.getShortcuts(ShortcutManagerCompat.FLAG_MATCH_PINNED)
                .map { it.id }

        if (shortcutIds.contains(mappedShortcutInfoCompat.id).not()) {
            return resourcesWrapper.getString(R.string.shortcut_id_not_found)
        }

        return try {
            val updateShortcutsSuccess = shortcutManagerCompatWrapper.updateShortcuts(
                packageName = packageName,
                appName = appName,
                mappedShortcutInfoCompat = mappedShortcutInfoCompat,
            )

            if (updateShortcutsSuccess) {
                resourcesWrapper.getString(R.string.shortcut_update_success)
            } else {
                resourcesWrapper.getString(R.string.shortcut_update_failed)
            }
        } catch (e: IllegalArgumentException) {
            resourcesWrapper.getString(R.string.shortcut_update_immutable_shortcuts)
        }
    }

    override fun getShortcut(id: String): MappedShortcutInfoCompat? {
        return shortcutManagerCompatWrapper.getShortcuts(ShortcutManagerCompat.FLAG_MATCH_PINNED)
            .find { it.id == id }
    }
}
