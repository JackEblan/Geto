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

package com.android.geto.feature.appsettings.dialog.shortcut

import android.content.Intent
import com.android.geto.core.testing.resources.TestResourcesWrapper
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ShortcutDialogStateTest {
    private lateinit var shortcutDialogState: ShortcutDialogState

    private val resourcesWrapper = TestResourcesWrapper()

    private val packageName = "com.android.geto"

    @Before
    fun setup() {
        shortcutDialogState = ShortcutDialogState(resourcesWrapper = resourcesWrapper)
    }

    @Test
    fun shortLabelError_isNotBlank_whenShortLabel_isBlank() {
        resourcesWrapper.setString("Short label is blank")

        shortcutDialogState.updateShortLabel("")

        shortcutDialogState.getShortcut(packageName = packageName, shortcutIntent = Intent())

        assertTrue { shortcutDialogState.shortLabelError.isNotBlank() }
    }

    @Test
    fun shortLabelError_isBlank_whenShortLabel_isNotBlank() {
        shortcutDialogState.updateShortLabel("Geto")

        shortcutDialogState.getShortcut(packageName = packageName, shortcutIntent = Intent())

        assertTrue { shortcutDialogState.shortLabelError.isBlank() }
    }

    @Test
    fun longLabelError_isNotBlank_whenLongLabel_isBlank() {
        resourcesWrapper.setString("Long label is blank")

        shortcutDialogState.updateLongLabel("")

        shortcutDialogState.getShortcut(packageName = packageName, shortcutIntent = Intent())

        assertTrue { shortcutDialogState.longLabelError.isNotBlank() }
    }

    @Test
    fun longLabelError_isBlank_whenLongLabel_isNotBlank() {
        shortcutDialogState.updateLongLabel("Geto")

        shortcutDialogState.getShortcut(packageName = packageName, shortcutIntent = Intent())

        assertTrue { shortcutDialogState.longLabelError.isBlank() }
    }

    @Test
    fun getShortcut_isNotNull_whenAllProperties_areFilled() {
        shortcutDialogState.updateShortLabel("Geto")

        shortcutDialogState.updateLongLabel("Geto")

        assertNotNull(
            shortcutDialogState.getShortcut(
                packageName = packageName, shortcutIntent = Intent()
            )
        )
    }
}