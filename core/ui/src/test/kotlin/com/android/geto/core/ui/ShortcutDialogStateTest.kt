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

package com.android.geto.core.ui

import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ShortcutDialogStateTest {
    private lateinit var shortcutDialogState: ShortcutDialogState

    @Before
    fun setup() {
        shortcutDialogState = ShortcutDialogState()
    }

    @Test
    fun shortLabelErrorIsNotBlank_whenShortLabelIsBlank() {
        shortcutDialogState.updateShortLabel("")

        shortcutDialogState.getShortcut(packageName = "packageName")

        assertTrue { shortcutDialogState.shortLabelError.isNotBlank() }
    }

    @Test
    fun shortLabelErrorIsBlank_whenShortLabelIsNotBlank() {
        shortcutDialogState.updateShortLabel("Test")

        shortcutDialogState.getShortcut(packageName = "packageName")

        assertTrue { shortcutDialogState.shortLabelError.isBlank() }
    }

    @Test
    fun longLabelErrorIsNotBlank_whenLongLabelIsBlank() {
        shortcutDialogState.updateLongLabel("")

        shortcutDialogState.getShortcut(packageName = "packageName")

        assertTrue { shortcutDialogState.longLabelError.isNotBlank() }
    }

    @Test
    fun longLabelErrorIsBlank_whenLongLabelIsNotBlank() {
        shortcutDialogState.updateLongLabel("Test")

        shortcutDialogState.getShortcut(packageName = "packageName")

        assertTrue { shortcutDialogState.longLabelError.isBlank() }
    }

    @Test
    fun getShortcut_whenAllFieldsAreFilled() {
        shortcutDialogState.updateShortLabel("Test")

        shortcutDialogState.updateLongLabel("Test")

        assertNotNull(
            shortcutDialogState.getShortcut(
                packageName = "packageName"
            )
        )
    }
}