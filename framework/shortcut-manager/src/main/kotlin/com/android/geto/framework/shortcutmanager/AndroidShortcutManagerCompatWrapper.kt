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
import com.android.geto.core.model.MappedShortcutInfoCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class AndroidShortcutManagerCompatWrapper @Inject constructor(@ApplicationContext private val context: Context) :
    ShortcutManagerCompatWrapper {

    override val flagMatchPinned = ShortcutManagerCompat.FLAG_MATCH_PINNED

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

    override fun updateShortcuts(
        packageName: String,
        appName: String,
        mappedShortcutInfoCompats: List<MappedShortcutInfoCompat>,
    ): Boolean {
        return ShortcutManagerCompat.updateShortcuts(
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

    override fun getShortcuts(matchFlags: Int): List<MappedShortcutInfoCompat> {
        return ShortcutManagerCompat.getShortcuts(
            context,
            matchFlags,
        ).map { it.asMappedShortcutInfoCompat() }
    }
}
