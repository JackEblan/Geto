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

package com.android.geto.feature.appsettings.dialog.appsettings

import com.android.geto.core.model.SecureSetting
import com.android.geto.core.testing.resources.TestResourcesWrapper
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AppSettingsDialogStateTest {

    private lateinit var appSettingsDialogState: AppSettingsDialogState

    private val resourcesWrapper = TestResourcesWrapper()

    private val packageName = "com.android.geto"

    @Before
    fun setup() {
        appSettingsDialogState = AppSettingsDialogState(resourcesWrapper = resourcesWrapper)
    }

    @Test
    fun labelError_isNotBlank_whenLabel_isBlank() {
        resourcesWrapper.setString("Settings label is blank")

        appSettingsDialogState.updateLabel("")

        appSettingsDialogState.getAppSettings(packageName = packageName)

        assertTrue { appSettingsDialogState.labelError.isNotBlank() }
    }

    @Test
    fun labelError_isBlank_whenLabel_isNotBlank() {
        appSettingsDialogState.updateLabel("Geto")

        appSettingsDialogState.getAppSettings(packageName = packageName)

        assertTrue { appSettingsDialogState.labelError.isBlank() }
    }

    @Test
    fun keyError_isNotBlank_whenKey_isBlank() {
        resourcesWrapper.setString("Settings key is blank")

        appSettingsDialogState.updateKey("")

        appSettingsDialogState.getAppSettings(packageName = packageName)

        assertTrue { appSettingsDialogState.keyError.isNotBlank() }
    }

    @Test
    fun keyError_isBlank_whenKey_isNotBlank() {
        appSettingsDialogState.updateKey("Geto")

        appSettingsDialogState.getAppSettings(packageName = packageName)

        assertTrue { appSettingsDialogState.keyError.isBlank() }
    }

    @Test
    fun settingsKeyNotFoundError_isNotBlank_whenSettingsKey_notFound() {
        val secureSettings = List(5) { index ->
            SecureSetting(id = index.toLong(), name = "Geto", value = "0")
        }

        resourcesWrapper.setString("Settings key not found")

        appSettingsDialogState.updateSecureSettings(secureSettings)

        appSettingsDialogState.updateKey("_")

        appSettingsDialogState.getAppSettings(packageName = packageName)

        assertTrue { appSettingsDialogState.settingsKeyNotFoundError.isNotBlank() }
    }

    @Test
    fun settingsKeyNotFoundError_isBlank_whenSettingsKey_found() {
        val secureSettings = List(5) { index ->
            SecureSetting(id = index.toLong(), name = "Geto", value = "0")
        }

        appSettingsDialogState.updateSecureSettings(secureSettings)

        appSettingsDialogState.updateKey("Geto")

        appSettingsDialogState.getAppSettings(packageName = packageName)

        assertTrue { appSettingsDialogState.settingsKeyNotFoundError.isBlank() }
    }

    @Test
    fun valueOnLaunchError_isNotBlank_whenValueOnLaunch_isBlank() {
        resourcesWrapper.setString("Settings value on launch is blank")

        appSettingsDialogState.updateValueOnLaunch("")

        appSettingsDialogState.getAppSettings(packageName = packageName)

        assertTrue { appSettingsDialogState.valueOnLaunchError.isNotBlank() }
    }

    @Test
    fun valueOnLaunchError_isBlank_whenValueOnLaunch_isNotBlank() {
        appSettingsDialogState.updateValueOnLaunch("0")

        appSettingsDialogState.getAppSettings(packageName = packageName)

        assertTrue { appSettingsDialogState.valueOnLaunchError.isBlank() }
    }

    @Test
    fun valueOnRevertError_isNotBlank_whenValueOnRevert_isBlank() {
        resourcesWrapper.setString("Settings value on revert is blank")

        appSettingsDialogState.updateValueOnRevert("")

        appSettingsDialogState.getAppSettings(packageName = packageName)

        assertTrue { appSettingsDialogState.valueOnRevertError.isNotBlank() }
    }

    @Test
    fun valueOnRevertError_isBlank_whenValueOnRevert_isNotBlank() {
        appSettingsDialogState.updateValueOnRevert("1")

        appSettingsDialogState.getAppSettings(packageName = packageName)

        assertTrue { appSettingsDialogState.valueOnRevertError.isBlank() }
    }

    @Test
    fun getAppSettings_isNotNull_whenAllProperties_areFilled() {
        val secureSettings = List(5) { index ->
            SecureSetting(id = index.toLong(), name = "Geto", value = "0")
        }

        appSettingsDialogState.updateSecureSettings(secureSettings)

        appSettingsDialogState.updateSelectedRadioOptionIndex(1)

        appSettingsDialogState.updateKey("Geto")

        appSettingsDialogState.updateLabel("Geto")

        appSettingsDialogState.updateValueOnLaunch("0")

        appSettingsDialogState.updateValueOnRevert("1")

        assertNotNull(appSettingsDialogState.getAppSettings(packageName = packageName))
    }
}