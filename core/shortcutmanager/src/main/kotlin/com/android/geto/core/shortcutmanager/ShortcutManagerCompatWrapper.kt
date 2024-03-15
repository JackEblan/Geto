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

    /**
     * @return true if the launcher supports requestPinShortcut,
     * false otherwise
     */
    fun isRequestPinShortcutSupported(): Boolean

    /**
     * Returns an Intent which can be used by the launcher to pin shortcut.
     *
     * @param shortcutInfoCompat new shortcut to pin
     * @return the intent that should be set as the result for the calling activity
     */
    fun createShortcutResultIntent(shortcutInfoCompat: ShortcutInfoCompat): Intent

    /**
     * Request to create a pinned shortcut.
     * <p>On API <= 25 it creates a legacy shortcut with the provided icon, label and intent. For
     * newer APIs it will create a {@link android.content.pm.ShortcutInfo} object which can be
     * updated by the app.
     *
     * @return true if the launcher supports this feature
     */
    fun requestPinShortcut(
        icon: Bitmap?, id: String, shortLabel: String, longLabel: String, intent: Intent
    ): Boolean

    /**
     * Update all existing shortcuts with the same IDs. Target shortcuts may be pinned and/or
     * dynamic, but they must not be immutable.
     * <p>On API <= 31 Any shortcuts that are marked as excluded from launcher will not be passed
     * to the {@link ShortcutManager}, but they might still be available to assistant and other
     * surfaces through alternative means.
     *
     * <p>This API will be rate-limited.
     *
     * @return true if the call has succeeded. false if the call fails or is
     * rate-limited.
     *
     * @throws IllegalArgumentException If trying to update immutable shortcuts.
     */
    fun updateShortcuts(
        icon: Bitmap?, id: String, shortLabel: String, longLabel: String, intent: Intent
    ): Boolean

    /**
     * Re-enable pinned shortcuts that were previously disabled.  If the target shortcuts
     * are already enabled, this method does nothing.
     * <p>In API 31 and below any shortcuts that are marked as excluded from launcher will be
     * ignored.
     *
     * @throws IllegalArgumentException If trying to enable immutable shortcuts.
     *
     * @throws IllegalStateException when the user is locked.
     */
    fun enableShortcuts(id: String)

    /**
     * Disable pinned shortcuts, showing the user a custom error message when they try to select
     * the disabled shortcuts.
     * For more details, read
     * <a href="/guide/topics/ui/shortcuts/managing-shortcuts.html#disable-shortcuts">
     * Disable shortcuts</a>.
     *
     * @throws IllegalArgumentException If trying to disable immutable shortcuts.
     *
     * @throws IllegalStateException when the user is locked.
     */
    fun disableShortcuts(id: String)

    /**
     * Returns list of ShortcutInfoCompat that match matchFlags.
     *
     * @param matchFlags result includes shortcuts matching this flags. Any combination of:
     *
     * @return list of ShortcutInfoCompat that match the flag.
     *
     * @throws IllegalStateException when the user is locked.
     */
    fun getShortcuts(matchFlags: Int): List<TargetShortcutInfoCompat>
}