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
package com.android.geto.feature.appsettings.dialog.appsetting

import com.android.geto.domain.model.SecureSetting
import com.android.geto.domain.model.SettingType
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AppSettingsDialogStateTest {

    private lateinit var appSettingDialogState: AppSettingDialogState

    @BeforeTest
    fun setup() {
        appSettingDialogState = AppSettingDialogState()
    }

    @Test
    fun labelError_isNotBlank_whenLabel_isBlank() {
        appSettingDialogState.updateLabel("")

        appSettingDialogState.getAppSetting()

        assertTrue(appSettingDialogState.showLabelError)
    }

    @Test
    fun labelError_isBlank_whenLabel_isNotBlank() {
        appSettingDialogState.updateLabel("Geto")

        appSettingDialogState.getAppSetting()

        assertFalse { appSettingDialogState.showLabelError }
    }

    @Test
    fun keyError_isNotBlank_whenKey_isBlank() {
        appSettingDialogState.updateKey("")

        appSettingDialogState.getAppSetting()

        assertTrue(appSettingDialogState.showKeyError)
    }

    @Test
    fun keyError_isBlank_whenKey_isNotBlank() {
        appSettingDialogState.updateKey("Geto")

        appSettingDialogState.getAppSetting()

        assertFalse { appSettingDialogState.showKeyError }
    }

    @Test
    fun settingsKeyNotFoundError_isNotBlank_whenSettingsKey_notFound() {
        val secureSettings = List(5) { index ->
            SecureSetting(
                settingType = SettingType.SYSTEM,
                id = index.toLong(),
                name = "Geto",
                value = "0",
            )
        }

        appSettingDialogState.updateSecureSettings(secureSettings)

        appSettingDialogState.updateKey("_")

        appSettingDialogState.getAppSetting()

        assertTrue(appSettingDialogState.showKeyNotFoundError)
    }

    @Test
    fun settingsKeyNotFoundError_isBlank_whenSettingsKey_found() {
        val secureSettings = List(5) { index ->
            SecureSetting(
                settingType = SettingType.SYSTEM,
                id = index.toLong(),
                name = "Geto",
                value = "0",
            )
        }

        appSettingDialogState.updateSecureSettings(secureSettings)

        appSettingDialogState.updateKey("Geto")

        appSettingDialogState.getAppSetting()

        assertFalse(appSettingDialogState.showKeyNotFoundError)
    }

    @Test
    fun valueOnLaunchError_isNotBlank_whenValueOnLaunch_isBlank() {
        appSettingDialogState.updateValueOnLaunch("")

        appSettingDialogState.getAppSetting()

        assertTrue { appSettingDialogState.showValueOnLaunchError }
    }

    @Test
    fun valueOnLaunchError_isBlank_whenValueOnLaunch_isNotBlank() {
        appSettingDialogState.updateValueOnLaunch("0")

        appSettingDialogState.getAppSetting()

        assertFalse(appSettingDialogState.showValueOnLaunchError)
    }

    @Test
    fun valueOnRevertError_isNotBlank_whenValueOnRevert_isBlank() {
        appSettingDialogState.updateValueOnRevert("")

        appSettingDialogState.getAppSetting()

        assertTrue(appSettingDialogState.showValueOnRevertError)
    }

    @Test
    fun valueOnRevertError_isBlank_whenValueOnRevert_isNotBlank() {
        appSettingDialogState.updateValueOnRevert("1")

        appSettingDialogState.getAppSetting()

        assertFalse(appSettingDialogState.showValueOnRevertError)
    }

    @Test
    fun getAppSettings_isNotNull_whenAllProperties_areFilled() {
        val secureSettings = List(5) { index ->
            SecureSetting(
                settingType = SettingType.SYSTEM,
                id = index.toLong(),
                name = "Geto",
                value = "0",
            )
        }

        appSettingDialogState.updateSecureSettings(secureSettings)

        appSettingDialogState.updateSelectedRadioOptionIndex(1)

        appSettingDialogState.updateKey("Geto")

        appSettingDialogState.updateLabel("Geto")

        appSettingDialogState.updateValueOnLaunch("0")

        appSettingDialogState.updateValueOnRevert("1")

        assertNotNull(appSettingDialogState.getAppSetting())
    }
}
