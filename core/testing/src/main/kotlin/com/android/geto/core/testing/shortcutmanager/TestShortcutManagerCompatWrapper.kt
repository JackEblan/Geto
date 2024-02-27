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

package com.android.geto.core.testing.shortcutmanager

import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.pm.ShortcutInfoCompat
import com.android.geto.core.shortcutmanager.ShortcutManagerCompatWrapper

class TestShortcutManagerCompatWrapper : ShortcutManagerCompatWrapper {

    private var requestPinShortcutSupported = false

    private var requestPinShortcut = false
    override fun isRequestPinShortcutSupported(): Boolean {
        return requestPinShortcutSupported
    }

    override fun createShortcutResultIntent(shortcutInfoCompat: ShortcutInfoCompat): Intent {
        return Intent()
    }

    override fun requestPinShortcut(
        icon: Bitmap?, id: String, shortLabel: String, longLabel: String, intent: Intent
    ): Boolean {
        return requestPinShortcut
    }

    /**
     * A test-only API to set isRequestPinShortcutSupported
     */
    fun setRequestPinShortcutSupported(value: Boolean) {
        requestPinShortcutSupported = value
    }

    /**
     * A test-only API to set requestPinShortcut
     */
    fun setRequestPinShortcut(value: Boolean) {
        requestPinShortcut = value
    }
}