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

import com.android.geto.core.data.testdoubles.TestResourcesWrapper
import com.android.geto.core.data.testdoubles.TestShortcutManagerCompatWrapper
import com.android.geto.core.model.MappedShortcutInfoCompat
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ShortcutRepositoryTest {
    private lateinit var shortcutManagerCompatWrapper: TestShortcutManagerCompatWrapper

    private lateinit var resourcesWrapper: TestResourcesWrapper

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

        resourcesWrapper = TestResourcesWrapper()

        subject = DefaultShortcutRepository(
            shortcutManagerCompatWrapper = shortcutManagerCompatWrapper,
            resourcesWrapper = resourcesWrapper,
        )
    }

    @Test
    fun requestPinShortcut_isSupportedLauncher() {
        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(true)

        shortcutManagerCompatWrapper.setRequestPinShortcut(true)

        resourcesWrapper.setString("Your current launcher supports shortcuts")

        assertEquals(
            expected = "Your current launcher supports shortcuts",
            actual = subject.requestPinShortcut(
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
    fun requestPinShortcut_isUnSupportedLauncher() {
        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(false)

        resourcesWrapper.setString("Your current launcher does not support shortcuts")

        assertEquals(
            expected = "Your current launcher does not support shortcuts",
            actual = subject.requestPinShortcut(
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
    fun updateRequestPinShortcut_isShortcutUpdateImmutableShortcuts() {
        shortcutManagerCompatWrapper.setUpdateImmutableShortcuts(true)

        shortcutManagerCompatWrapper.setShortcuts(shortcuts)

        resourcesWrapper.setString("Cannot update immutable shortcuts")

        assertEquals(
            expected = "Cannot update immutable shortcuts",
            actual = subject.updateRequestPinShortcut(
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
    fun updateRequestPinShortcut_isShortcutUpdateSuccess() {
        shortcutManagerCompatWrapper.setUpdateImmutableShortcuts(false)

        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(true)

        shortcutManagerCompatWrapper.setShortcuts(shortcuts)

        resourcesWrapper.setString("Shortcut update success")

        assertEquals(
            expected = "Shortcut update success",
            actual = subject.updateRequestPinShortcut(
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
    fun updateRequestPinShortcut_isShortcutUpdateFailed() {
        shortcutManagerCompatWrapper.setUpdateImmutableShortcuts(false)

        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(false)

        shortcutManagerCompatWrapper.setShortcuts(shortcuts)

        resourcesWrapper.setString("Shortcut update failed")

        assertEquals(
            expected = "Shortcut update failed",
            actual = subject.updateRequestPinShortcut(
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
    fun getShortcut_isNull() {
        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(true)

        shortcutManagerCompatWrapper.setShortcuts(shortcuts)

        assertNull(subject.getShortcut("com.android.sample"))
    }

    @Test
    fun getShortcut_isNotNull() {
        shortcutManagerCompatWrapper.setRequestPinShortcutSupported(true)

        shortcutManagerCompatWrapper.setShortcuts(shortcuts)

        assertNotNull(subject.getShortcut("com.android.geto"))
    }
}
