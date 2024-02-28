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
import com.android.geto.core.model.Shortcut
import com.android.geto.core.testing.shortcutmanager.TestShortcutManagerCompatWrapper
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

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
            Shortcut(
                icon = null,
                id = "id",
                shortLabel = "shortLabel",
                longLabel = "longLabel",
                intent = Intent()
            )
        )

        assertTrue(result)
    }

    @Test
    fun shortcutRepository_cannot_request_pin_shortcut() {
        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(false)

        val result = subject.requestPinShortcut(
            Shortcut(
                icon = null,
                id = "id",
                shortLabel = "shortLabel",
                longLabel = "longLabel",
                intent = Intent()
            )
        )

        assertFalse(result)
    }

    @Test
    fun shortcutRepository_cannot_update_pin_shortcut() {
        shortcutManagerCompatWrapper.setUpdateImmutableShortcuts(true)

        val result = subject.updateRequestPinShortcut(
            Shortcut(
                icon = null,
                id = "id",
                shortLabel = "shortLabel",
                longLabel = "longLabel",
                intent = Intent()
            )
        )

        assertFailsWith<IllegalArgumentException> { result.getOrThrow() }
    }

    @Test
    fun shortcutRepository_can_update_pin_shortcut() {
        shortcutManagerCompatWrapper.setUpdateImmutableShortcuts(false)

        val result = subject.updateRequestPinShortcut(
            Shortcut(
                icon = null,
                id = "id",
                shortLabel = "shortLabel",
                longLabel = "longLabel",
                intent = Intent()
            )
        )

        assertEquals(expected = true, actual = result.getOrNull())
    }

    @Test
    fun shortcutRepository_enable_shortcuts() {
        val result = subject.enableShortcuts(id = "id", enabled = true)

        assertEquals(expected = "Shortcut enabled", actual = result.getOrNull())
    }

    @Test
    fun shortcutRepository_disable_shortcuts_on_disableImmutableShortcuts() {
        shortcutManagerCompatWrapper.setDisableImmutableShortcuts(true)

        assertFailsWith<IllegalArgumentException> {
            subject.enableShortcuts(
                id = "id", enabled = false
            ).getOrThrow()
        }
    }

    @Test
    fun shortcutRepository_disable_shortcuts_on_userIsLocked() {
        shortcutManagerCompatWrapper.setUserIsLocked(true)

        assertFailsWith<IllegalStateException> {
            subject.enableShortcuts(
                id = "id", enabled = false
            ).getOrThrow()
        }
    }

    @Test
    fun shortcutRepository_shortcut_not_found() {
        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(true)

        shortcutManagerCompatWrapper.sendShortcuts(
            listOf(
                Shortcut(
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

        shortcutManagerCompatWrapper.sendShortcuts(
            listOf(
                Shortcut(
                    id = "TestId", shortLabel = "shortLabel", longLabel = "longLabel"
                )
            )
        )

        val result = subject.getShortcut("TestId")

        assertNotNull(result)
    }
}