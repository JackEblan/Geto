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
package com.android.geto.framework.shortcutmanager.mapper

import android.content.Context
import android.content.Intent
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.graphics.drawable.IconCompat
import androidx.core.net.toUri
import com.android.geto.core.model.GetoShortcutInfoCompat

internal fun ShortcutInfoCompat.asGetoShortcutInfoCompat(): GetoShortcutInfoCompat {
    return GetoShortcutInfoCompat(
        id = id,
        shortLabel = shortLabel.toString(),
        longLabel = longLabel.toString(),
    )
}

internal fun GetoShortcutInfoCompat.asShortcutInfoCompat(
    context: Context,
    packageName: String,
    appName: String,
): ShortcutInfoCompat {
    val shortcutIntent = Intent().apply {
        action = Intent.ACTION_VIEW
        // TODO: Do not hard code the className for MainActivity
        setClassName(context.packageName, "com.android.geto.MainActivity")
        data = "https://www.android.geto.com/$packageName/$appName".toUri()
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    return ShortcutInfoCompat.Builder(context, id).apply {
        if (icon != null) {
            setIcon(IconCompat.createWithBitmap(icon!!))
        }

        setShortLabel(shortLabel)
        setLongLabel(longLabel)
        setIntent(shortcutIntent)
    }.build()
}
