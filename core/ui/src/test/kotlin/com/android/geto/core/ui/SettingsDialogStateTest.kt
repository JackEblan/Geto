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

class SettingsDialogStateTest {

    private lateinit var settingsDialogState: SettingsDialogState

    @Before
    fun setup() {
        settingsDialogState = SettingsDialogState()

        settingsDialogState.updateSecureSettings(
            listOf(
                SecureSettings(
                    id = 0, name = "name0", value = "value0"
                )
            )
        )
    }

    @Test
    fun labelErrorIsNotBlank_whenLabelIsBlank() {
        settingsDialogState.updateLabel("")

        settingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { settingsDialogState.labelError.isNotBlank() }
    }

    @Test
    fun labelErrorIsBlank_whenLabelIsNotBlank() {
        settingsDialogState.updateLabel("Test")

        settingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { settingsDialogState.labelError.isBlank() }
    }

    @Test
    fun keyErrorIsNotBlank_whenKeyIsBlank() {
        settingsDialogState.updateKey("")

        settingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { settingsDialogState.keyError.isNotBlank() }
    }

    @Test
    fun keyErrorIsBlank_whenKeyIsNotBlank() {
        settingsDialogState.updateKey("test")

        settingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { settingsDialogState.keyError.isBlank() }
    }

    @Test
    fun settingsKeyNotFoundErrorIsNotBlank_whenSettingsKeyNotFound() {
        settingsDialogState.updateKey("keyNotFound")

        settingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { settingsDialogState.settingsKeyNotFoundError.isNotBlank() }
    }

    @Test
    fun settingsKeyNotFoundErrorIsBlank_whenSettingsKeyFound() {
        settingsDialogState.updateKey("name0")

        settingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { settingsDialogState.settingsKeyNotFoundError.isBlank() }
    }

    @Test
    fun valueOnLaunchErrorIsNotBlank_whenValueOnLaunchIsBlank() {
        settingsDialogState.updateValueOnLaunch("")

        settingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { settingsDialogState.valueOnLaunchError.isNotBlank() }
    }

    @Test
    fun valueOnLaunchErrorIsBlank_whenValueOnLaunchIsNotBlank() {
        settingsDialogState.updateValueOnLaunch("Test")

        settingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { settingsDialogState.valueOnLaunchError.isBlank() }
    }

    @Test
    fun valueOnRevertErrorIsNotBlank_whenValueOnRevertIsBlank() {
        settingsDialogState.updateValueOnRevert("")

        settingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { settingsDialogState.valueOnRevertError.isNotBlank() }
    }

    @Test
    fun valueOnRevertErrorIsBlank_whenValueOnRevertIsNotBlank() {
        settingsDialogState.updateValueOnRevert("Test")

        settingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { settingsDialogState.valueOnRevertError.isBlank() }
    }

    @Test
    fun getAppSettings_whenAllFieldsAreFilled() {
        settingsDialogState.updateSelectedRadioOptionIndex(1)

        settingsDialogState.updateKey("name0")

        settingsDialogState.updateLabel("Test")

        settingsDialogState.updateValueOnLaunch("Test")

        settingsDialogState.updateValueOnRevert("Test")

        assertNotNull(settingsDialogState.getAppSettings(packageName = "packageName"))
    }
}