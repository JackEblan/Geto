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

import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.pm.ShortcutInfoCompat
import com.android.geto.core.model.TargetShortcutInfoCompat

interface ShortcutManagerCompatWrapper {
    fun isRequestPinShortcutSupported(): Boolean

    fun createShortcutResultIntent(shortcutInfoCompat: ShortcutInfoCompat): Intent

    fun requestPinShortcut(
        icon: Bitmap?, id: String, shortLabel: String, longLabel: String
    ): Boolean

    @Throws(IllegalArgumentException::class)
    fun updateShortcuts(
        icon: Bitmap?, id: String, shortLabel: String, longLabel: String
    ): Boolean

    fun enableShortcuts(id: String)

    @Throws(IllegalArgumentException::class, IllegalStateException::class)
    fun disableShortcuts(id: String)

    fun getShortcuts(matchFlags: Int): List<TargetShortcutInfoCompat>
}