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
package com.android.geto.core.data.testdoubles

import com.android.geto.core.model.MappedShortcutInfoCompat
import com.android.geto.framework.shortcutmanager.ShortcutManagerCompatWrapper

class TestShortcutManagerCompatWrapper : ShortcutManagerCompatWrapper {

    private var requestPinShortcutSupported = false

    private var requestPinShortcut = false

    private var updateImmutableShortcuts = false

    private var mappedShortcutInfoCompats = emptyList<MappedShortcutInfoCompat>()

    override val flagMatchPinned = 0

    override fun isRequestPinShortcutSupported(): Boolean {
        return requestPinShortcutSupported
    }

    override fun requestPinShortcut(
        packageName: String,
        appName: String,
        mappedShortcutInfoCompat: MappedShortcutInfoCompat,
    ): Boolean {
        return requestPinShortcut
    }

    override fun updateShortcuts(
        packageName: String,
        appName: String,
        mappedShortcutInfoCompats: List<MappedShortcutInfoCompat>,
    ): Boolean {
        return if (updateImmutableShortcuts) {
            throw IllegalArgumentException()
        } else {
            requestPinShortcutSupported
        }
    }

    override fun getShortcuts(matchFlags: Int): List<MappedShortcutInfoCompat> {
        return mappedShortcutInfoCompats
    }

    fun setRequestPinShortcutSupported(value: Boolean) {
        requestPinShortcutSupported = value
    }

    fun setRequestPinShortcut(value: Boolean) {
        requestPinShortcut = value
    }

    fun setShortcuts(value: List<MappedShortcutInfoCompat>) {
        mappedShortcutInfoCompats = value
    }

    fun setUpdateImmutableShortcuts(value: Boolean) {
        updateImmutableShortcuts = value
    }
}
