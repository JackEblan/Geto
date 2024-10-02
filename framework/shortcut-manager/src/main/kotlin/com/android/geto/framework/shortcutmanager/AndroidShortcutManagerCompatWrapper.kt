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
package com.android.geto.framework.shortcutmanager

import android.content.Context
import androidx.core.content.pm.ShortcutManagerCompat
import com.android.geto.core.common.Dispatcher
import com.android.geto.core.common.GetoDispatchers.Default
import com.android.geto.core.model.MappedShortcutInfoCompat
import com.android.geto.framework.shortcutmanager.mapper.asMappedShortcutInfoCompat
import com.android.geto.framework.shortcutmanager.mapper.asShortcutInfoCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AndroidShortcutManagerCompatWrapper @Inject constructor(
    @Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
) : ShortcutManagerCompatWrapper {
    override fun isRequestPinShortcutSupported(): Boolean {
        return ShortcutManagerCompat.isRequestPinShortcutSupported(context)
    }

    override fun requestPinShortcut(
        packageName: String,
        appName: String,
        mappedShortcutInfoCompat: MappedShortcutInfoCompat,
    ): Boolean {
        return ShortcutManagerCompat.requestPinShortcut(
            context,
            mappedShortcutInfoCompat.asShortcutInfoCompat(
                context = context,
                packageName = packageName,
                appName = appName,
            ),
            null,
        )
    }

    override suspend fun updateShortcuts(
        packageName: String,
        appName: String,
        mappedShortcutInfoCompats: List<MappedShortcutInfoCompat>,
    ): Boolean {
        return withContext(defaultDispatcher) {
            ShortcutManagerCompat.updateShortcuts(
                context,
                mappedShortcutInfoCompats.map {
                    it.asShortcutInfoCompat(
                        context = context,
                        packageName = packageName,
                        appName = appName,
                    )
                },
            )
        }
    }

    override suspend fun getShortcuts(): List<MappedShortcutInfoCompat> {
        return withContext(defaultDispatcher) {
            ShortcutManagerCompat.getShortcuts(
                context,
                ShortcutManagerCompat.FLAG_MATCH_PINNED,
            ).map { it.asMappedShortcutInfoCompat() }
        }
    }
}
