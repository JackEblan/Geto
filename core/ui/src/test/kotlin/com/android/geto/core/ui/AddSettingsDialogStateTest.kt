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

import com.android.geto.core.model.SecureSettings
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AddSettingsDialogStateTest {

    private lateinit var addSettingsDialogState: AddSettingsDialogState

    @Before
    fun setup() {
        addSettingsDialogState = AddSettingsDialogState()

        addSettingsDialogState.updateSecureSettings(
            listOf(
                SecureSettings(
                    id = 0, name = "name0", value = "value0"
                )
            )
        )
    }

    @Test
    fun selectedRadioOptionIndexErrorIsNotBlank_whenSelectedRadioOptionIndexIsNegative() {
        addSettingsDialogState.updateSelectedRadioOptionIndex(-1)

        addSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { addSettingsDialogState.selectedRadioOptionIndexError.isNotBlank() }
    }

    @Test
    fun selectedRadioOptionIndexErrorIsBlank_whenSelectedRadioOptionIndexIsPositive() {
        addSettingsDialogState.updateSelectedRadioOptionIndex(1)

        addSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { addSettingsDialogState.selectedRadioOptionIndexError.isBlank() }
    }

    @Test
    fun labelErrorIsNotBlank_whenLabelIsBlank() {
        addSettingsDialogState.updateLabel("")

        addSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { addSettingsDialogState.labelError.isNotBlank() }
    }

    @Test
    fun labelErrorIsBlank_whenLabelIsNotBlank() {
        addSettingsDialogState.updateLabel("Test")

        addSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { addSettingsDialogState.labelError.isBlank() }
    }

    @Test
    fun keyErrorIsNotBlank_whenKeyIsBlank() {
        addSettingsDialogState.updateKey("")

        addSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { addSettingsDialogState.keyError.isNotBlank() }
    }

    @Test
    fun keyErrorIsBlank_whenKeyIsNotBlank() {
        addSettingsDialogState.updateKey("test")

        addSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { addSettingsDialogState.keyError.isBlank() }
    }

    @Test
    fun settingsKeyNotFoundErrorIsNotBlank_whenSettingsKeyNotFound() {
        addSettingsDialogState.updateKey("keyNotFound")

        addSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { addSettingsDialogState.settingsKeyNotFoundError.isNotBlank() }
    }

    @Test
    fun settingsKeyNotFoundErrorIsBlank_whenSettingsKeyFound() {
        addSettingsDialogState.updateKey("name0")

        addSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { addSettingsDialogState.settingsKeyNotFoundError.isBlank() }
    }

    @Test
    fun valueOnLaunchErrorIsNotBlank_whenValueOnLaunchIsBlank() {
        addSettingsDialogState.updateValueOnLaunch("")

        addSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { addSettingsDialogState.valueOnLaunchError.isNotBlank() }
    }

    @Test
    fun valueOnLaunchErrorIsBlank_whenValueOnLaunchIsNotBlank() {
        addSettingsDialogState.updateValueOnLaunch("Test")

        addSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { addSettingsDialogState.valueOnLaunchError.isBlank() }
    }

    @Test
    fun valueOnRevertErrorIsNotBlank_whenValueOnRevertIsBlank() {
        addSettingsDialogState.updateValueOnRevert("")

        addSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { addSettingsDialogState.valueOnRevertError.isNotBlank() }
    }

    @Test
    fun valueOnRevertErrorIsBlank_whenValueOnRevertIsNotBlank() {
        addSettingsDialogState.updateValueOnRevert("Test")

        addSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { addSettingsDialogState.valueOnRevertError.isBlank() }
    }

    @Test
    fun getAppSettings_whenAllFieldsAreFilled() {
        addSettingsDialogState.updateSelectedRadioOptionIndex(1)

        addSettingsDialogState.updateKey("name0")

        addSettingsDialogState.updateLabel("Test")

        addSettingsDialogState.updateValueOnLaunch("Test")

        addSettingsDialogState.updateValueOnRevert("Test")

        assertNotNull(addSettingsDialogState.getAppSettings(packageName = "packageName"))
    }
}