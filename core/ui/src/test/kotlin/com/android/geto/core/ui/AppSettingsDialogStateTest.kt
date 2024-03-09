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

class AppSettingsDialogStateTest {

    private lateinit var appSettingsDialogState: AppSettingsDialogState

    @Before
    fun setup() {
        appSettingsDialogState = AppSettingsDialogState()

        appSettingsDialogState.updateSecureSettings(
            listOf(
                SecureSettings(
                    id = 0, name = "name0", value = "value0"
                )
            )
        )
    }

    @Test
    fun labelErrorIsNotBlank_whenLabelIsBlank() {
        appSettingsDialogState.updateLabel("")

        appSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { appSettingsDialogState.labelError.isNotBlank() }
    }

    @Test
    fun labelErrorIsBlank_whenLabelIsNotBlank() {
        appSettingsDialogState.updateLabel("Test")

        appSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { appSettingsDialogState.labelError.isBlank() }
    }

    @Test
    fun keyErrorIsNotBlank_whenKeyIsBlank() {
        appSettingsDialogState.updateKey("")

        appSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { appSettingsDialogState.keyError.isNotBlank() }
    }

    @Test
    fun keyErrorIsBlank_whenKeyIsNotBlank() {
        appSettingsDialogState.updateKey("test")

        appSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { appSettingsDialogState.keyError.isBlank() }
    }

    @Test
    fun settingsKeyNotFoundErrorIsNotBlank_whenSettingsKeyNotFound() {
        appSettingsDialogState.updateKey("keyNotFound")

        appSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { appSettingsDialogState.settingsKeyNotFoundError.isNotBlank() }
    }

    @Test
    fun settingsKeyNotFoundErrorIsBlank_whenSettingsKeyFound() {
        appSettingsDialogState.updateKey("name0")

        appSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { appSettingsDialogState.settingsKeyNotFoundError.isBlank() }
    }

    @Test
    fun valueOnLaunchErrorIsNotBlank_whenValueOnLaunchIsBlank() {
        appSettingsDialogState.updateValueOnLaunch("")

        appSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { appSettingsDialogState.valueOnLaunchError.isNotBlank() }
    }

    @Test
    fun valueOnLaunchErrorIsBlank_whenValueOnLaunchIsNotBlank() {
        appSettingsDialogState.updateValueOnLaunch("Test")

        appSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { appSettingsDialogState.valueOnLaunchError.isBlank() }
    }

    @Test
    fun valueOnRevertErrorIsNotBlank_whenValueOnRevertIsBlank() {
        appSettingsDialogState.updateValueOnRevert("")

        appSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { appSettingsDialogState.valueOnRevertError.isNotBlank() }
    }

    @Test
    fun valueOnRevertErrorIsBlank_whenValueOnRevertIsNotBlank() {
        appSettingsDialogState.updateValueOnRevert("Test")

        appSettingsDialogState.getAppSettings(packageName = "packageName")

        assertTrue { appSettingsDialogState.valueOnRevertError.isBlank() }
    }

    @Test
    fun getAppSettings_whenAllFieldsAreFilled() {
        appSettingsDialogState.updateSelectedRadioOptionIndex(1)

        appSettingsDialogState.updateKey("name0")

        appSettingsDialogState.updateLabel("Test")

        appSettingsDialogState.updateValueOnLaunch("Test")

        appSettingsDialogState.updateValueOnRevert("Test")

        assertNotNull(appSettingsDialogState.getAppSettings(packageName = "packageName"))
    }
}