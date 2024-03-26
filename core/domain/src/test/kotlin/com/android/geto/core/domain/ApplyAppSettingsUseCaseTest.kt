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

package com.android.geto.core.domain

import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.SettingType
import com.android.geto.core.model.TargetApplicationInfo
import com.android.geto.core.testing.repository.TestAppSettingsRepository
import com.android.geto.core.testing.repository.TestPackageRepository
import com.android.geto.core.testing.repository.TestSecureSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertIs
import kotlin.test.assertNotNull

class ApplyAppSettingsUseCaseTest {
    private lateinit var applyAppSettingsUseCase: ApplyAppSettingsUseCase

    private val packageRepository = TestPackageRepository()

    private val appSettingsRepository = TestAppSettingsRepository()

    private val secureSettingsRepository = TestSecureSettingsRepository()

    private val packageName = "com.android.geto"

    @Before
    fun setUp() {
        applyAppSettingsUseCase = ApplyAppSettingsUseCase(
            packageRepository = packageRepository,
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository
        )
    }

    @Test
    fun applyAppSettingsUseCase_isEmptyAppSettings() = runTest {
        appSettingsRepository.setAppSettings(emptyList())

        val result = applyAppSettingsUseCase(packageName = packageName)

        assertIs<AppSettingsResult.EmptyAppSettings>(result)
    }


    @Test
    fun applyAppSettingsUseCase_isAppSettingsDisabled() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = false,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto", key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        }

        appSettingsRepository.setAppSettings(appSettings)

        val result = applyAppSettingsUseCase(packageName = packageName)

        assertIs<AppSettingsResult.AppSettingsDisabled>(result)
    }

    @Test
    fun applyAppSettingsUseCase_isSuccess() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto", key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        }

        val installedApplications = List(5) { index ->
            TargetApplicationInfo(flags = 0, packageName = packageName, label = "Geto $index")
        }

        packageRepository.setInstalledApplications(installedApplications)

        secureSettingsRepository.setWriteSecureSettings(true)

        appSettingsRepository.setAppSettings(appSettings)

        val result = applyAppSettingsUseCase(packageName = packageName)

        assertIs<AppSettingsResult.Success>(result)

        assertNotNull(result.launchIntent)
    }

    @Test
    fun applyAppSettingsUseCase_isSecurityException() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto", key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        }

        secureSettingsRepository.setWriteSecureSettings(false)

        appSettingsRepository.setAppSettings(appSettings)

        val result = applyAppSettingsUseCase(packageName = packageName)

        assertIs<AppSettingsResult.SecurityException>(result)
    }

    @Test
    fun applyAppSettingsUseCase_isIllegalArgumentException() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto", key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        }

        secureSettingsRepository.setWriteSecureSettings(true)

        secureSettingsRepository.setInvalidValues(true)

        appSettingsRepository.setAppSettings(appSettings)

        val result = applyAppSettingsUseCase(packageName = packageName)

        assertIs<AppSettingsResult.IllegalArgumentException>(result)
    }
}