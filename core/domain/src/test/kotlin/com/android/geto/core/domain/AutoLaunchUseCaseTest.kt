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
import com.android.geto.core.model.AppSettingsResult
import com.android.geto.core.model.ApplicationInfo
import com.android.geto.core.model.DarkThemeConfig
import com.android.geto.core.model.SettingType
import com.android.geto.core.model.ThemeBrand
import com.android.geto.core.model.UserData
import com.android.geto.core.testing.repository.TestAppSettingsRepository
import com.android.geto.core.testing.repository.TestPackageRepository
import com.android.geto.core.testing.repository.TestSecureSettingsRepository
import com.android.geto.core.testing.repository.TestUserDataRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class AutoLaunchUseCaseTest {
    private lateinit var autoLaunchUseCase: AutoLaunchUseCase

    private lateinit var packageRepository: TestPackageRepository

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var userDataRepository: TestUserDataRepository

    private lateinit var secureSettingsRepository: TestSecureSettingsRepository

    private val packageName = "com.android.geto"

    @Before
    fun setup() {
        packageRepository = TestPackageRepository()

        appSettingsRepository = TestAppSettingsRepository()

        userDataRepository = TestUserDataRepository()

        secureSettingsRepository = TestSecureSettingsRepository()

        autoLaunchUseCase = AutoLaunchUseCase(
            userDataRepository = userDataRepository,
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository,
        )
    }

    @Test
    fun autoLaunchAppUseCase_isFailure_whenAppSettings_isEmpty() = runTest {
        appSettingsRepository.setAppSettings(emptyList())

        userDataRepository.setUserData(
            userData = UserData(
                themeBrand = ThemeBrand.DEFAULT,
                darkThemeConfig = DarkThemeConfig.DARK,
                useDynamicColor = false,
                useAutoLaunch = false,
            ),
        )

        assertEquals(
            expected = AppSettingsResult.Failure,
            actual = autoLaunchUseCase(packageName = packageName),
        )
    }

    @Test
    fun autoLaunchAppUseCase_isFailure_whenAppSettings_isDisabled() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = false,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto",
                key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        appSettingsRepository.setAppSettings(appSettings)

        userDataRepository.setUserData(
            userData = UserData(
                themeBrand = ThemeBrand.DEFAULT,
                darkThemeConfig = DarkThemeConfig.DARK,
                useDynamicColor = false,
                useAutoLaunch = false,
            ),
        )

        assertEquals(
            expected = AppSettingsResult.Failure,
            actual = autoLaunchUseCase(packageName = packageName),
        )
    }

    @Test
    fun autoLaunchAppUseCase_isSuccess() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto",
                key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        val applicationInfos = List(5) { index ->
            ApplicationInfo(flags = 0, packageName = packageName, label = "Geto $index")
        }

        packageRepository.setApplicationInfos(applicationInfos)

        secureSettingsRepository.setWriteSecureSettings(true)

        userDataRepository.setUserData(
            userData = UserData(
                themeBrand = ThemeBrand.DEFAULT,
                darkThemeConfig = DarkThemeConfig.DARK,
                useDynamicColor = false,
                useAutoLaunch = true,
            ),
        )

        appSettingsRepository.setAppSettings(appSettings)

        assertEquals(
            expected = AppSettingsResult.Success,
            actual = autoLaunchUseCase(packageName = packageName),
        )
    }

    @Test
    fun autoLaunchAppUseCase_isSecurityException() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto",
                key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        secureSettingsRepository.setWriteSecureSettings(false)

        userDataRepository.setUserData(
            userData = UserData(
                themeBrand = ThemeBrand.DEFAULT,
                darkThemeConfig = DarkThemeConfig.DARK,
                useDynamicColor = false,
                useAutoLaunch = true,
            ),
        )

        appSettingsRepository.setAppSettings(appSettings)

        assertEquals(
            expected = AppSettingsResult.SecurityException,
            actual = autoLaunchUseCase(packageName = packageName),
        )
    }

    @Test
    fun autoLaunchAppUseCase_isIllegalArgumentException() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto",
                key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        secureSettingsRepository.setWriteSecureSettings(true)

        secureSettingsRepository.setInvalidValues(true)

        userDataRepository.setUserData(
            userData = UserData(
                themeBrand = ThemeBrand.DEFAULT,
                darkThemeConfig = DarkThemeConfig.DARK,
                useDynamicColor = false,
                useAutoLaunch = true,
            ),
        )

        appSettingsRepository.setAppSettings(appSettings)

        assertEquals(
            expected = AppSettingsResult.IllegalArgumentException,
            actual = autoLaunchUseCase(packageName = packageName),
        )
    }

    @Test
    fun autoLaunchAppUseCase_isNoResult_whenUseAutoLaunch_isFalse() = runTest {
        val appSettings = List(5) { index ->
            AppSetting(
                id = index,
                enabled = true,
                settingType = SettingType.SYSTEM,
                packageName = packageName,
                label = "Geto",
                key = "Geto $index",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        secureSettingsRepository.setWriteSecureSettings(true)

        userDataRepository.setUserData(
            userData = UserData(
                themeBrand = ThemeBrand.DEFAULT,
                darkThemeConfig = DarkThemeConfig.DARK,
                useDynamicColor = false,
                useAutoLaunch = false,
            ),
        )

        appSettingsRepository.setAppSettings(appSettings)

        assertEquals(
            expected = AppSettingsResult.Failure,
            actual = autoLaunchUseCase(packageName = packageName),
        )
    }
}
