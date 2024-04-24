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
package com.android.geto.core.data.repository

import android.content.Intent
import com.android.geto.core.data.testdoubles.TestShortcutManagerCompatWrapper
import com.android.geto.core.model.TargetShortcutInfoCompat
import org.junit.Before
import org.junit.Test
import kotlin.test.assertIs

class ShortcutRepositoryTest {

    private val shortcutManagerCompatWrapper = TestShortcutManagerCompatWrapper()

    private lateinit var subject: ShortcutRepository

    private val shortcuts = List(10) {
        TargetShortcutInfoCompat(
            id = "com.android.geto",
            shortLabel = "Geto",
            longLabel = "Geto",
            shortcutIntent = Intent(),
        )
    }

    @Before
    fun setup() {
        subject = DefaultShortcutRepository(
            shortcutManagerCompatWrapper = shortcutManagerCompatWrapper,
        )
    }

    @Test
    fun requestPinShortcut_isSupportedLauncher() {
        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(true)

        shortcutManagerCompatWrapper.setRequestPinShortcut(true)

        assertIs<ShortcutResult.SupportedLauncher>(
            subject.requestPinShortcut(
                targetShortcutInfoCompat = TargetShortcutInfoCompat(
                    id = "com.android.geto",
                    shortLabel = "Geto",
                    longLabel = "Geto",
                    shortcutIntent = Intent(),
                ),
            ),
        )
    }

    @Test
    fun requestPinShortcut_isUnSupportedLauncher() {
        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(false)

        assertIs<ShortcutResult.UnsupportedLauncher>(
            subject.requestPinShortcut(
                targetShortcutInfoCompat = TargetShortcutInfoCompat(
                    id = "com.android.geto",
                    shortLabel = "Geto",
                    longLabel = "Geto",
                    shortcutIntent = Intent(),
                ),
            ),
        )
    }

    @Test
    fun updateRequestPinShortcut_isShortcutUpdateImmutableShortcuts() {
        shortcutManagerCompatWrapper.setUpdateImmutableShortcuts(true)

        shortcutManagerCompatWrapper.setShortcuts(shortcuts)

        assertIs<ShortcutResult.ShortcutUpdateImmutableShortcuts>(
            subject.updateRequestPinShortcut(
                targetShortcutInfoCompat = TargetShortcutInfoCompat(
                    id = "com.android.geto",
                    shortLabel = "Geto",
                    longLabel = "Geto",
                    shortcutIntent = Intent(),
                ),
            ),
        )
    }

    @Test
    fun updateRequestPinShortcut_isShortcutUpdateSuccess() {
        shortcutManagerCompatWrapper.setUpdateImmutableShortcuts(false)

        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(true)

        shortcutManagerCompatWrapper.setShortcuts(shortcuts)

        assertIs<ShortcutResult.ShortcutUpdateSuccess>(
            subject.updateRequestPinShortcut(
                targetShortcutInfoCompat = TargetShortcutInfoCompat(
                    id = "com.android.geto",
                    shortLabel = "Geto",
                    longLabel = "Geto",
                    shortcutIntent = Intent(),
                ),
            ),
        )
    }

    @Test
    fun updateRequestPinShortcut_isShortcutUpdateFailed() {
        shortcutManagerCompatWrapper.setUpdateImmutableShortcuts(false)

        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(false)

        shortcutManagerCompatWrapper.setShortcuts(shortcuts)

        assertIs<ShortcutResult.ShortcutUpdateFailed>(
            subject.updateRequestPinShortcut(
                targetShortcutInfoCompat = TargetShortcutInfoCompat(
                    id = "com.android.geto",
                    shortLabel = "Geto",
                    longLabel = "Geto",
                    shortcutIntent = Intent(),
                ),
            ),
        )
    }

    @Test
    fun getShortcut_isNoShortcutFound() {
        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(true)

        shortcutManagerCompatWrapper.setShortcuts(shortcuts)

        assertIs<ShortcutResult.NoShortcutFound>(subject.getShortcut("com.android.sample"))
    }

    @Test
    fun getShortcut_isShortcutFound() {
        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(true)

        shortcutManagerCompatWrapper.setShortcuts(shortcuts)

        assertIs<ShortcutResult.ShortcutFound>(subject.getShortcut("com.android.geto"))
    }
}
