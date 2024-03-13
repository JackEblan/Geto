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

import com.android.geto.core.data.testdoubles.TestShortcutManagerCompatWrapper
import com.android.geto.core.model.TargetShortcutInfoCompat
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ShortcutRepositoryTest {

    private lateinit var shortcutManagerCompatWrapper: TestShortcutManagerCompatWrapper

    private lateinit var subject: ShortcutRepository

    @Before
    fun setUp() {
        shortcutManagerCompatWrapper = TestShortcutManagerCompatWrapper()

        subject = DefaultShortcutRepository(
            shortcutManagerCompatWrapper = shortcutManagerCompatWrapper
        )
    }

    @Test
    fun shortcutRepository_can_request_pin_shortcut() {
        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(true)

        shortcutManagerCompatWrapper.setRequestPinShortcut(true)

        val result = subject.requestPinShortcut(
            TargetShortcutInfoCompat(
                icon = null, id = "id", shortLabel = "shortLabel", longLabel = "longLabel"
            )
        )

        assertEquals(expected = "Your current launcher supports shortcuts", actual = result)
    }

    @Test
    fun shortcutRepository_cannot_request_pin_shortcut() {
        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(false)

        val result = subject.requestPinShortcut(
            TargetShortcutInfoCompat(
                icon = null, id = "id", shortLabel = "shortLabel", longLabel = "longLabel"
            )
        )

        assertEquals(expected = "Your current launcher does not support shortcuts", actual = result)
    }

    @Test
    fun shortcutRepository_cannot_update_pin_shortcut() {
        shortcutManagerCompatWrapper.setUpdateImmutableShortcuts(true)

        shortcutManagerCompatWrapper.setShortcuts(
            listOf(
                TargetShortcutInfoCompat(
                    icon = null, id = "id", shortLabel = "shortLabel", longLabel = "longLabel"
                )
            )
        )

        val result = subject.updateRequestPinShortcut(
            TargetShortcutInfoCompat(
                icon = null, id = "id", shortLabel = "shortLabel", longLabel = "longLabel"
            )
        )

        assertEquals(expected = "Trying to update immutable shortcuts", actual = result)
    }

    @Test
    fun shortcutRepository_can_update_pin_shortcut() {
        shortcutManagerCompatWrapper.setUpdateImmutableShortcuts(false)

        shortcutManagerCompatWrapper.setShortcuts(
            listOf(
                TargetShortcutInfoCompat(
                    icon = null, id = "id", shortLabel = "shortLabel", longLabel = "longLabel"
                )
            )
        )

        val result = subject.updateRequestPinShortcut(
            TargetShortcutInfoCompat(
                icon = null, id = "id", shortLabel = "shortLabel", longLabel = "longLabel"
            )
        )

        assertEquals(expected = "Shortcut updated successfully", actual = result)
    }

    @Test
    fun shortcutRepository_enable_shortcuts() {
        val result = subject.enableShortcuts(id = "id", enabled = true)

        assertEquals(expected = "Shortcuts enabled", actual = result)
    }

    @Test
    fun shortcutRepository_disable_shortcuts() {
        shortcutManagerCompatWrapper.setDisableImmutableShortcuts(false)

        shortcutManagerCompatWrapper.setUserIsLocked(false)

        val result = subject.enableShortcuts(id = "id", enabled = false)

        assertEquals(expected = "Shortcuts disabled", actual = result)
    }

    @Test
    fun shortcutRepository_disable_shortcuts_on_disableImmutableShortcuts() {
        shortcutManagerCompatWrapper.setDisableImmutableShortcuts(true)

        val result = subject.enableShortcuts(
            id = "id", enabled = false
        )

        assertEquals(expected = "Trying to disable immutable shortcuts", actual = result)
    }

    @Test
    fun shortcutRepository_disable_shortcuts_on_userIsLocked() {
        shortcutManagerCompatWrapper.setUserIsLocked(true)

        val result = subject.enableShortcuts(
            id = "id", enabled = false
        )

        assertEquals(expected = "User is locked", actual = result)
    }

    @Test
    fun shortcutRepository_shortcut_not_found() {
        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(true)

        shortcutManagerCompatWrapper.setShortcuts(
            listOf(
                TargetShortcutInfoCompat(
                    id = "TestId", shortLabel = "shortLabel", longLabel = "longLabel"
                )
            )
        )

        val result = subject.getShortcut("TestIdNotFound")

        assertNull(result)
    }

    @Test
    fun shortcutRepository_shortcut_found() {
        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(true)

        shortcutManagerCompatWrapper.setShortcuts(
            listOf(
                TargetShortcutInfoCompat(
                    id = "TestId", shortLabel = "shortLabel", longLabel = "longLabel"
                )
            )
        )

        val result = subject.getShortcut("TestId")

        assertNotNull(result)
    }
}