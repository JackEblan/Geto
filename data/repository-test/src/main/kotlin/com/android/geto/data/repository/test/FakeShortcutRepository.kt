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
package com.android.geto.data.repository.test

import com.android.geto.domain.model.GetoShortcutInfoCompat
import com.android.geto.domain.repository.ShortcutRepository
import javax.inject.Inject

class FakeShortcutRepository @Inject constructor() : ShortcutRepository {

    override fun isRequestPinShortcutSupported(): Boolean {
        return false
    }

    override fun requestPinShortcut(
        packageName: String,
        icon: ByteArray?,
        appName: String,
        id: String,
        shortLabel: String,
        longLabel: String,
    ): Boolean {
        return false
    }

    override fun updateShortcuts(
        packageName: String,
        icon: ByteArray?,
        appName: String,
        id: String,
        shortLabel: String,
        longLabel: String,
    ): Boolean {
        return false
    }

    override suspend fun getPinnedShortcuts(): List<GetoShortcutInfoCompat> {
        return emptyList()
    }

    override suspend fun getPinnedShortcut(id: String): GetoShortcutInfoCompat? {
        return null
    }
}
