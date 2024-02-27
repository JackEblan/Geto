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

import android.content.Intent
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AddShortcutDialogStateTest {
    private lateinit var addShortcutDialogState: AddShortcutDialogState

    @Before
    fun setup() {
        addShortcutDialogState = AddShortcutDialogState()
    }

    @Test
    fun shortLabelErrorIsNotBlank_whenShortLabelIsBlank() {
        addShortcutDialogState.updateShortLabel("")

        addShortcutDialogState.getShortcut(packageName = "packageName", intent = Intent())

        assertTrue { addShortcutDialogState.shortLabelError.isNotBlank() }
    }

    @Test
    fun shortLabelErrorIsBlank_whenShortLabelIsNotBlank() {
        addShortcutDialogState.updateShortLabel("Test")

        addShortcutDialogState.getShortcut(packageName = "packageName", intent = Intent())

        assertTrue { addShortcutDialogState.shortLabelError.isBlank() }
    }

    @Test
    fun longLabelErrorIsNotBlank_whenLongLabelIsBlank() {
        addShortcutDialogState.updateLongLabel("")

        addShortcutDialogState.getShortcut(packageName = "packageName", intent = Intent())

        assertTrue { addShortcutDialogState.longLabelError.isNotBlank() }
    }

    @Test
    fun longLabelErrorIsBlank_whenLongLabelIsNotBlank() {
        addShortcutDialogState.updateLongLabel("Test")

        addShortcutDialogState.getShortcut(packageName = "packageName", intent = Intent())

        assertTrue { addShortcutDialogState.longLabelError.isBlank() }
    }

    @Test
    fun getShortcut_whenAllFieldsAreFilled() {
        addShortcutDialogState.updateShortLabel("Test")

        addShortcutDialogState.updateLongLabel("Test")

        assertNotNull(
            addShortcutDialogState.getShortcut(
                packageName = "packageName", intent = Intent()
            )
        )
    }
}