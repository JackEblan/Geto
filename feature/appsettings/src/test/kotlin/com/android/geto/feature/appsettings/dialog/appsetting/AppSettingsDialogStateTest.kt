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

import com.android.geto.core.model.SecureSetting
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AppSettingsDialogStateTest {

    private lateinit var appSettingDialogState: AppSettingDialogState

    private val packageName = "com.android.geto"

    @Before
    fun setup() {
        appSettingDialogState = AppSettingDialogState()

        appSettingDialogState.setStringResources(
            labelIsBlank = "Settings label is blank",
            keyIsBlank = "Settings key is blank",
            keyNotFound = "Settings key not found",
            valueOnLaunchIsBlank = "Settings value on launch is blank",
            valueOnRevertIsBlank = "Settings value on revert is blank",
        )
    }

    @Test
    fun labelError_isNotBlank_whenLabel_isBlank() {
        appSettingDialogState.updateLabel("")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.showLabelError.isNotBlank() }
    }

    @Test
    fun labelError_isBlank_whenLabel_isNotBlank() {
        appSettingDialogState.updateLabel("Geto")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.showLabelError.isBlank() }
    }

    @Test
    fun keyError_isNotBlank_whenKey_isBlank() {
        appSettingDialogState.updateKey("")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.showKeyError.isNotBlank() }
    }

    @Test
    fun keyError_isBlank_whenKey_isNotBlank() {
        appSettingDialogState.updateKey("Geto")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.showKeyError.isBlank() }
    }

    @Test
    fun settingsKeyNotFoundError_isNotBlank_whenSettingsKey_notFound() {
        val secureSettings = List(5) { index ->
            SecureSetting(id = index.toLong(), name = "Geto", value = "0")
        }

        appSettingDialogState.updateSecureSettings(secureSettings)

        appSettingDialogState.updateKey("_")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.showKeyNotFoundError.isNotBlank() }
    }

    @Test
    fun settingsKeyNotFoundError_isBlank_whenSettingsKey_found() {
        val secureSettings = List(5) { index ->
            SecureSetting(id = index.toLong(), name = "Geto", value = "0")
        }

        appSettingDialogState.updateSecureSettings(secureSettings)

        appSettingDialogState.updateKey("Geto")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.showKeyNotFoundError.isBlank() }
    }

    @Test
    fun valueOnLaunchError_isNotBlank_whenValueOnLaunch_isBlank() {
        appSettingDialogState.updateValueOnLaunch("")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.showValueOnLaunchError.isNotBlank() }
    }

    @Test
    fun valueOnLaunchError_isBlank_whenValueOnLaunch_isNotBlank() {
        appSettingDialogState.updateValueOnLaunch("0")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.showValueOnLaunchError.isBlank() }
    }

    @Test
    fun valueOnRevertError_isNotBlank_whenValueOnRevert_isBlank() {
        appSettingDialogState.updateValueOnRevert("")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.showValueOnRevertError.isNotBlank() }
    }

    @Test
    fun valueOnRevertError_isBlank_whenValueOnRevert_isNotBlank() {
        appSettingDialogState.updateValueOnRevert("1")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.showValueOnRevertError.isBlank() }
    }

    @Test
    fun getAppSettings_isNotNull_whenAllProperties_areFilled() {
        val secureSettings = List(5) { index ->
            SecureSetting(id = index.toLong(), name = "Geto", value = "0")
        }

        appSettingDialogState.updateSecureSettings(secureSettings)

        appSettingDialogState.updateSelectedRadioOptionIndex(1)

        appSettingDialogState.updateKey("Geto")

        appSettingDialogState.updateLabel("Geto")

        appSettingDialogState.updateValueOnLaunch("0")

        appSettingDialogState.updateValueOnRevert("1")

        assertNotNull(appSettingDialogState.getAppSetting(packageName = packageName))
    }
}
