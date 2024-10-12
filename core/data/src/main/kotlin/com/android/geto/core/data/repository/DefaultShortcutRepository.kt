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

import com.android.geto.core.model.GetoShortcutInfoCompat
import com.android.geto.framework.shortcutmanager.ShortcutManagerCompatWrapper
import javax.inject.Inject

internal class DefaultShortcutRepository @Inject constructor(
    private val shortcutManagerCompatWrapper: ShortcutManagerCompatWrapper,
) : ShortcutRepository {

    override fun isRequestPinShortcutSupported(): Boolean {
        return shortcutManagerCompatWrapper.isRequestPinShortcutSupported()
    }

    override fun requestPinShortcut(
        packageName: String,
        appName: String,
        getoShortcutInfoCompat: GetoShortcutInfoCompat,
    ): Boolean {
        return shortcutManagerCompatWrapper.requestPinShortcut(
            packageName = packageName,
            appName = appName,
            getoShortcutInfoCompat = getoShortcutInfoCompat,
        )
    }

    override fun updateShortcuts(
        packageName: String,
        appName: String,
        getoShortcutInfoCompats: List<GetoShortcutInfoCompat>,
    ): Boolean {
        return shortcutManagerCompatWrapper.updateShortcuts(
            packageName = packageName,
            appName = appName,
            getoShortcutInfoCompats = getoShortcutInfoCompats,
        )
    }

    override fun getPinnedShortcuts(): List<GetoShortcutInfoCompat> {
        return shortcutManagerCompatWrapper.getShortcuts(shortcutManagerCompatWrapper.flagMatchPinned)
    }

    override fun getPinnedShortcut(id: String): GetoShortcutInfoCompat? {
        return shortcutManagerCompatWrapper.getShortcuts(shortcutManagerCompatWrapper.flagMatchPinned)
            .find { it.id == id }
    }
}
