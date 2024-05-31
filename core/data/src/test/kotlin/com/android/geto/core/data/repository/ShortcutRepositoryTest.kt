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
import com.android.geto.core.model.MappedShortcutInfoCompat
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ShortcutRepositoryTest {
    private lateinit var shortcutManagerCompatWrapper: TestShortcutManagerCompatWrapper

    private lateinit var subject: ShortcutRepository

    private val shortcuts = List(10) {
        MappedShortcutInfoCompat(
            id = "com.android.geto",
            shortLabel = "Geto",
            longLabel = "Geto",
        )
    }

    @Before
    fun setup() {
        shortcutManagerCompatWrapper = TestShortcutManagerCompatWrapper()

        subject = DefaultShortcutRepository(
            shortcutManagerCompatWrapper = shortcutManagerCompatWrapper,
        )
    }

    @Test
    fun requestPinShortcut_isTrue() {
        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(true)

        shortcutManagerCompatWrapper.setRequestPinShortcut(true)

        assertTrue(
            subject.requestPinShortcut(
                packageName = "com.android.geto",
                appName = "Geto",
                mappedShortcutInfoCompat = MappedShortcutInfoCompat(
                    id = "com.android.geto",
                    shortLabel = "Geto",
                    longLabel = "Geto",
                ),
            ),
        )
    }

    @Test
    fun requestPinShortcut_isFalse() {
        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(false)

        assertFalse(
            subject.requestPinShortcut(
                packageName = "com.android.geto",
                appName = "Geto",
                mappedShortcutInfoCompat = MappedShortcutInfoCompat(
                    id = "com.android.geto",
                    shortLabel = "Geto",
                    longLabel = "Geto",
                ),
            ),
        )
    }

    @Test
    fun updateShortcuts_isTrue() {
        shortcutManagerCompatWrapper.setUpdateImmutableShortcuts(false)

        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(true)

        shortcutManagerCompatWrapper.setShortcuts(shortcuts)

        assertTrue(
            subject.updateShortcuts(
                packageName = "com.android.geto",
                appName = "Geto",
                shortcuts = listOf(
                    MappedShortcutInfoCompat(
                        id = "com.android.geto",
                        shortLabel = "Geto",
                        longLabel = "Geto",
                    ),
                ),
            ),
        )
    }

    @Test
    fun updateShortcuts_throwsIllegalArgumentException() {
        shortcutManagerCompatWrapper.setUpdateImmutableShortcuts(true)

        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(false)

        shortcutManagerCompatWrapper.setShortcuts(shortcuts)

        assertFailsWith(
            exceptionClass = IllegalArgumentException::class,
            block = {
                subject.updateShortcuts(
                    packageName = "com.android.geto",
                    appName = "Geto",
                    shortcuts = listOf(
                        MappedShortcutInfoCompat(
                            id = "com.android.geto",
                            shortLabel = "Geto",
                            longLabel = "Geto",
                        ),
                    ),
                )
            },
        )
    }

    @Test
    fun getPinnedShortcuts_isEmpty() {
        shortcutManagerCompatWrapper.setShortcuts(emptyList())

        assertTrue(subject.getPinnedShortcuts().isEmpty())
    }

    @Test
    fun getPinnedShortcuts_isNotEmpty() {
        shortcutManagerCompatWrapper.setShortcuts(shortcuts)

        assertTrue(subject.getPinnedShortcuts().isNotEmpty())
    }

    @Test
    fun getPinnedShortcut_isNull() {
        shortcutManagerCompatWrapper.setShortcuts(shortcuts)

        assertNull(subject.getPinnedShortcut("com.android.sample"))
    }

    @Test
    fun getPinnedShortcut_isNotNull() {
        shortcutManagerCompatWrapper.setShortcuts(shortcuts)

        assertNotNull(subject.getPinnedShortcut("com.android.geto"))
    }
}
