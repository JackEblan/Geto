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

package com.android.geto.core.shortcutmanager

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat
import com.android.geto.core.model.Shortcut
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultShortcutManagerCompatWrapper @Inject constructor(@ApplicationContext private val context: Context) :
    ShortcutManagerCompatWrapper {

    override fun isRequestPinShortcutSupported(): Boolean {
        return ShortcutManagerCompat.isRequestPinShortcutSupported(context)
    }

    override fun createShortcutResultIntent(shortcutInfoCompat: ShortcutInfoCompat): Intent {
        return ShortcutManagerCompat.createShortcutResultIntent(
            context, shortcutInfoCompat
        )
    }

    override fun requestPinShortcut(
        icon: Bitmap?, id: String, shortLabel: String, longLabel: String, intent: Intent
    ): Boolean {
        val shortcutInfo = if (icon != null) {
            ShortcutInfoCompat.Builder(context, id).setIcon(IconCompat.createWithBitmap(icon))
                .setShortLabel(shortLabel).setLongLabel(longLabel).setIntent(intent).build()
        } else {
            ShortcutInfoCompat.Builder(context, id).setShortLabel(shortLabel)
                .setLongLabel(longLabel).setIntent(intent).build()
        }

        val shortcutCallbackIntent =
            ShortcutManagerCompat.createShortcutResultIntent(context, shortcutInfo).apply {
                action = "com.android.geto.ACTION_CREATE_SHORTCUT"
            }

        val successCallback = PendingIntent.getBroadcast(
            context, 0, shortcutCallbackIntent, PendingIntent.FLAG_IMMUTABLE
        )

        return ShortcutManagerCompat.requestPinShortcut(
            context, shortcutInfo, successCallback?.intentSender
        )
    }

    @Throws(IllegalArgumentException::class)
    override fun updateShortcuts(
        icon: Bitmap?, id: String, shortLabel: String, longLabel: String, intent: Intent
    ): Boolean {
        val shortcutInfo = if (icon != null) {
            ShortcutInfoCompat.Builder(context, id).setIcon(IconCompat.createWithBitmap(icon))
                .setShortLabel(shortLabel).setLongLabel(longLabel).setIntent(intent).build()
        } else {
            ShortcutInfoCompat.Builder(context, id).setShortLabel(shortLabel)
                .setLongLabel(longLabel).setIntent(intent).build()
        }

        val ids =
            ShortcutManagerCompat.getShortcuts(context, ShortcutManagerCompat.FLAG_MATCH_PINNED)
                .map { it.id }

        return if (id in ids) {
            ShortcutManagerCompat.updateShortcuts(context, listOf(shortcutInfo))
        } else {
            false
        }
    }

    override fun enableShortcuts(id: String) {
        val shortcutInfo = ShortcutInfoCompat.Builder(context, id).build()

        ShortcutManagerCompat.enableShortcuts(context, listOf(shortcutInfo))
    }

    @Throws(IllegalArgumentException::class, IllegalStateException::class)
    override fun disableShortcuts(id: String) {
        ShortcutManagerCompat.disableShortcuts(context, listOf(id), "Shortcut already disabled")
    }

    override fun getShortcuts(matchFlags: Int): List<Shortcut> {
        return ShortcutManagerCompat.getShortcuts(
            context, matchFlags
        ).map(ShortcutInfoCompat::asShortcut)
    }
}