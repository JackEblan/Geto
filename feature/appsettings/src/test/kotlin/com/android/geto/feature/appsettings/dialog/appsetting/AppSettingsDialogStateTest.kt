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
import com.android.geto.core.testing.resources.TestResourcesWrapper
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AppSettingsDialogStateTest {

    private lateinit var appSettingDialogState: AppSettingDialogState

    private val resourcesWrapper = TestResourcesWrapper()

    private val packageName = "com.android.geto"

    @Before
    fun setup() {
        appSettingDialogState = AppSettingDialogState(resourcesWrapper = resourcesWrapper)
    }

    @Test
    fun labelError_isNotBlank_whenLabel_isBlank() {
        resourcesWrapper.setString("Settings label is blank")

        appSettingDialogState.updateLabel("")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.labelError.isNotBlank() }
    }

    @Test
    fun labelError_isBlank_whenLabel_isNotBlank() {
        appSettingDialogState.updateLabel("Geto")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.labelError.isBlank() }
    }

    @Test
    fun keyError_isNotBlank_whenKey_isBlank() {
        resourcesWrapper.setString("Settings key is blank")

        appSettingDialogState.updateKey("")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.keyError.isNotBlank() }
    }

    @Test
    fun keyError_isBlank_whenKey_isNotBlank() {
        appSettingDialogState.updateKey("Geto")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.keyError.isBlank() }
    }

    @Test
    fun settingsKeyNotFoundError_isNotBlank_whenSettingsKey_notFound() {
        val secureSettings = List(5) { index ->
            SecureSetting(id = index.toLong(), name = "Geto", value = "0")
        }

        resourcesWrapper.setString("Settings key not found")

        appSettingDialogState.updateSecureSettings(secureSettings)

        appSettingDialogState.updateKey("_")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.settingKeyNotFoundError.isNotBlank() }
    }

    @Test
    fun settingsKeyNotFoundError_isBlank_whenSettingsKey_found() {
        val secureSettings = List(5) { index ->
            SecureSetting(id = index.toLong(), name = "Geto", value = "0")
        }

        appSettingDialogState.updateSecureSettings(secureSettings)

        appSettingDialogState.updateKey("Geto")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.settingKeyNotFoundError.isBlank() }
    }

    @Test
    fun valueOnLaunchError_isNotBlank_whenValueOnLaunch_isBlank() {
        resourcesWrapper.setString("Settings value on launch is blank")

        appSettingDialogState.updateValueOnLaunch("")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.valueOnLaunchError.isNotBlank() }
    }

    @Test
    fun valueOnLaunchError_isBlank_whenValueOnLaunch_isNotBlank() {
        appSettingDialogState.updateValueOnLaunch("0")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.valueOnLaunchError.isBlank() }
    }

    @Test
    fun valueOnRevertError_isNotBlank_whenValueOnRevert_isBlank() {
        resourcesWrapper.setString("Settings value on revert is blank")

        appSettingDialogState.updateValueOnRevert("")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.valueOnRevertError.isNotBlank() }
    }

    @Test
    fun valueOnRevertError_isBlank_whenValueOnRevert_isNotBlank() {
        appSettingDialogState.updateValueOnRevert("1")

        appSettingDialogState.getAppSetting(packageName = packageName)

        assertTrue { appSettingDialogState.valueOnRevertError.isBlank() }
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