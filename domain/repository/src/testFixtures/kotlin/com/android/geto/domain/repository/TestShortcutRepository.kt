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
package com.android.geto.domain.repository

import com.android.geto.domain.model.GetoShortcutInfoCompat

class TestShortcutRepository : ShortcutRepository {

    private var requestPinShortcutSupported = false

    private var getoShortcutInfoCompats = listOf<GetoShortcutInfoCompat>()

    private var updateImmutableShortcuts = false

    override fun isRequestPinShortcutSupported(): Boolean {
        return requestPinShortcutSupported
    }

    override fun requestPinShortcut(
        packageName: String,
        appName: String,
        getoShortcutInfoCompat: GetoShortcutInfoCompat,
    ): Boolean {
        return requestPinShortcutSupported
    }

    override fun updateShortcuts(
        packageName: String,
        appName: String,
        getoShortcutInfoCompats: List<GetoShortcutInfoCompat>,
    ): Boolean {
        return if (updateImmutableShortcuts) {
            throw IllegalArgumentException()
        } else {
            requestPinShortcutSupported
        }
    }

    override suspend fun getPinnedShortcuts(): List<GetoShortcutInfoCompat> {
        return getoShortcutInfoCompats
    }

    override suspend fun getPinnedShortcut(id: String): GetoShortcutInfoCompat? {
        return getoShortcutInfoCompats.find { it.id == id }
    }

    fun setRequestPinShortcutSupported(value: Boolean) {
        requestPinShortcutSupported = value
    }

    fun setShortcuts(value: List<GetoShortcutInfoCompat>) {
        getoShortcutInfoCompats = value
    }

    fun setUpdateImmutableShortcuts(value: Boolean) {
        updateImmutableShortcuts = value
    }
}
