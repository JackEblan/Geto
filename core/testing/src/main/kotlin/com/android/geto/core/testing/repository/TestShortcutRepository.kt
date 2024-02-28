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

package com.android.geto.core.testing.repository

import com.android.geto.core.data.repository.ShortcutRepository
import com.android.geto.core.model.Shortcut

class TestShortcutRepository : ShortcutRepository {

    private var requestPinShortcutSupported = false

    private var shortcuts = listOf<Shortcut>()

    private var updateImmutableShortcuts = false

    private var disableImmutableShortcuts = false

    private var userIsLocked = false

    override fun requestPinShortcut(shortcut: Shortcut): Boolean {
        return requestPinShortcutSupported
    }

    override fun updateRequestPinShortcut(shortcut: Shortcut): Result<Boolean> {
        return runCatching {
            updateImmutableShortcuts
        }
    }

    override fun enableShortcuts(id: String, enabled: Boolean): Result<String> {
        return runCatching {
            if (enabled) {
                "Shortcut enabled"
            } else {
                if (disableImmutableShortcuts) throw IllegalArgumentException()

                if (userIsLocked) throw IllegalStateException()

                "Shortcut disabled"
            }
        }
    }

    override fun getShortcut(id: String): Shortcut? {
        val shortcutInfoCompat = shortcuts.find { it.id == id }

        return if (shortcutInfoCompat != null) {
            Shortcut(
                shortLabel = shortcutInfoCompat.shortLabel.toString(),
                longLabel = shortcutInfoCompat.longLabel.toString()
            )
        } else {
            null
        }
    }

    /**
     * A test-only API to set isRequestPinShortcutSupported
     */
    fun setRequestPinShortcutSupported(value: Boolean) {
        requestPinShortcutSupported = value
    }

    /**
     * A test-only API to set set shortcuts
     */
    fun sendShortcuts(value: List<Shortcut>) {
        shortcuts = value
    }

    /**
     * If trying to update immutable shortcuts
     */
    fun setUpdateImmutableShortcuts(value: Boolean) {
        updateImmutableShortcuts = value
    }

    /**
     *  If trying to disable immutable shortcuts
     */
    fun setDisableImmutableShortcuts(value: Boolean) {
        disableImmutableShortcuts = value
    }

    /**
     *  when the user is locked
     */
    fun setUserIsLocked(value: Boolean) {
        userIsLocked = value
    }
}