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
package com.android.geto.data.repository

import com.android.geto.domain.common.dispatcher.Dispatcher
import com.android.geto.domain.common.dispatcher.GetoDispatchers.Default
import com.android.geto.domain.framework.ShortcutManagerCompatWrapper
import com.android.geto.domain.model.GetoShortcutInfoCompat
import com.android.geto.domain.repository.ShortcutRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultShortcutRepository @Inject constructor(
    @Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher,
    private val shortcutManagerCompatWrapper: ShortcutManagerCompatWrapper,
) : ShortcutRepository {

    override fun isRequestPinShortcutSupported(): Boolean {
        return shortcutManagerCompatWrapper.isRequestPinShortcutSupported()
    }

    override fun requestPinShortcut(
        packageName: String,
        icon: ByteArray?,
        appName: String,
        id: String,
        shortLabel: String,
        longLabel: String,
    ): Boolean {
        return shortcutManagerCompatWrapper.requestPinShortcut(
            packageName = packageName,
            icon = icon,
            appName = appName,
            id = id,
            shortLabel = shortLabel,
            longLabel = longLabel,
        )
    }

    override fun updateShortcuts(
        packageName: String,
        icon: ByteArray?,
        appName: String,
        id: String,
        shortLabel: String,
        longLabel: String,
    ): Boolean {
        return shortcutManagerCompatWrapper.updateShortcuts(
            packageName = packageName,
            icon = icon,
            appName = appName,
            id = id,
            shortLabel = shortLabel,
            longLabel = longLabel,
        )
    }

    override suspend fun getPinnedShortcuts(): List<GetoShortcutInfoCompat> {
        return shortcutManagerCompatWrapper.getShortcuts()
    }

    override suspend fun getPinnedShortcut(id: String): GetoShortcutInfoCompat? {
        return withContext(defaultDispatcher) {
            shortcutManagerCompatWrapper.getShortcuts().find { it.id == id }
        }
    }
}
