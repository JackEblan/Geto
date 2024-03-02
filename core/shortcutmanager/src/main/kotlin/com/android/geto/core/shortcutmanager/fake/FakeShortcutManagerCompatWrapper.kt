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

package com.android.geto.core.shortcutmanager.fake

import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.pm.ShortcutInfoCompat
import com.android.geto.core.model.Shortcut
import com.android.geto.core.shortcutmanager.ShortcutManagerCompatWrapper
import javax.inject.Inject

class FakeShortcutManagerCompatWrapper @Inject constructor() : ShortcutManagerCompatWrapper {

    override fun isRequestPinShortcutSupported(): Boolean {
        return true
    }

    override fun createShortcutResultIntent(shortcutInfoCompat: ShortcutInfoCompat): Intent {
        return Intent()
    }

    override fun requestPinShortcut(
        icon: Bitmap?, id: String, shortLabel: String, longLabel: String, intent: Intent
    ): Boolean {
        return true
    }

    override fun updateShortcuts(
        icon: Bitmap?, id: String, shortLabel: String, longLabel: String, intent: Intent
    ): Boolean {
        return true
    }

    override fun enableShortcuts(id: String) {
    }

    override fun disableShortcuts(id: String) {
    }

    override fun getShortcuts(matchFlags: Int): List<Shortcut> {
        return emptyList()
    }
}