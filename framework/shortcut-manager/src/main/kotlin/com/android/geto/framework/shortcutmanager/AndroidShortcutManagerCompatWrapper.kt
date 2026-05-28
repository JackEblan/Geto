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
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.android.geto.domain.common.dispatcher.Dispatcher
import com.android.geto.domain.common.dispatcher.GetoDispatchers.Default
import com.android.geto.domain.framework.ShortcutManagerCompatWrapper
import com.android.geto.domain.model.GetoShortcutInfoCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AndroidShortcutManagerCompatWrapper @Inject constructor(
    @param:ApplicationContext private val context: Context,
    @param:Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher,
) : ShortcutManagerCompatWrapper {
    override fun isRequestPinShortcutSupported(): Boolean {
        return ShortcutManagerCompat.isRequestPinShortcutSupported(context)
    }

    override fun requestPinShortcut(
        componentName: String,
        icon: ByteArray?,
        id: String,
        shortLabel: String,
        longLabel: String,
    ): Boolean {
        return ShortcutManagerCompat.requestPinShortcut(
            context,
            asShortcutInfoCompat(
                componentName = componentName,
                icon = icon,
                id = id,
                shortLabel = shortLabel,
                longLabel = longLabel,
            ),
            null,
        )
    }

    override fun updateShortcuts(
        componentName: String,
        icon: ByteArray?,
        id: String,
        shortLabel: String,
        longLabel: String,
    ): Boolean {
        return ShortcutManagerCompat.updateShortcuts(
            context,
            listOf(
                asShortcutInfoCompat(
                    componentName = componentName,
                    icon = icon,
                    id = id,
                    shortLabel = shortLabel,
                    longLabel = longLabel,
                ),
            ),
        )
    }

    override suspend fun getShortcuts(): List<GetoShortcutInfoCompat> {
        return withContext(defaultDispatcher) {
            ShortcutManagerCompat.getShortcuts(
                context,
                ShortcutManagerCompat.FLAG_MATCH_PINNED,
            ).map { it.asGetoShortcutInfoCompat() }
        }
    }

    private fun ShortcutInfoCompat.asGetoShortcutInfoCompat(): GetoShortcutInfoCompat {
        return GetoShortcutInfoCompat(
            id = id,
            shortLabel = shortLabel.toString(),
            longLabel = longLabel.toString(),
        )
    }

    private fun asShortcutInfoCompat(
        componentName: String,
        icon: ByteArray?,
        id: String,
        shortLabel: String,
        longLabel: String,
    ): ShortcutInfoCompat {
        val shortcutIntent = Intent().apply {
            action = Intent.ACTION_VIEW
            // TODO: Do not hard code the className for ShortcutActivity
            setClassName(context.packageName, "com.android.geto.ShortcutActivity")
            putExtra("EXTRA_COMPONENT_NAME", componentName)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        return ShortcutInfoCompat.Builder(context, id).apply {
            icon?.let {
                val bitmapIcon = BitmapFactory.decodeByteArray(it, 0, it.size)

                setIcon(IconCompat.createWithBitmap(bitmapIcon))
            }

            setShortLabel(shortLabel)
            setLongLabel(longLabel)
            setIntent(shortcutIntent)
        }.build()
    }
}
