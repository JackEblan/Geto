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

import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.DarkThemeConfig
import com.android.geto.core.model.SettingsType
import com.android.geto.core.model.TargetApplicationInfo
import com.android.geto.core.model.ThemeBrand
import com.android.geto.core.model.UserData
import com.android.geto.core.testing.repository.TestAppSettingsRepository
import com.android.geto.core.testing.repository.TestPackageRepository
import com.android.geto.core.testing.repository.TestSecureSettingsRepository
import com.android.geto.core.testing.repository.TestUserDataRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertIs
import kotlin.test.assertNotNull

class AutoLaunchUseCaseTest {
    private lateinit var autoLaunchUseCase: AutoLaunchUseCase

    private lateinit var packageRepository: TestPackageRepository

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var userDataRepository: TestUserDataRepository

    private lateinit var secureSettingsRepository: TestSecureSettingsRepository

    @Before
    fun setup() {
        packageRepository = TestPackageRepository()

        secureSettingsRepository = TestSecureSettingsRepository()

        appSettingsRepository = TestAppSettingsRepository()

        userDataRepository = TestUserDataRepository()

        autoLaunchUseCase = AutoLaunchUseCase(
            packageRepository = packageRepository,
            userDataRepository = userDataRepository,
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository
        )
    }

    @Test
    fun autoLaunchAppUseCase_empty_app_settings_list() = runTest {
        appSettingsRepository.setAppSettings(emptyList())

        userDataRepository.setUserData(
            userData = UserData(
                themeBrand = ThemeBrand.DEFAULT,
                darkThemeConfig = DarkThemeConfig.DARK,
                useDynamicColor = false,
                useAutoLaunch = false
            )
        )

        val result = autoLaunchUseCase(packageName = PACKAGE_NAME_TEST)

        assertIs<AutoLaunchResult.Ignore>(result)
    }

    @Test
    fun autoLaunchAppUseCase_app_settings_disabled() = runTest {
        appSettingsRepository.setAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = false,
                    settingsType = SettingsType.SYSTEM,
                    packageName = PACKAGE_NAME_TEST,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test"
                )
            )
        )

        userDataRepository.setUserData(
            userData = UserData(
                themeBrand = ThemeBrand.DEFAULT,
                darkThemeConfig = DarkThemeConfig.DARK,
                useDynamicColor = false,
                useAutoLaunch = false
            )
        )

        val result = autoLaunchUseCase(packageName = PACKAGE_NAME_TEST)

        assertIs<AutoLaunchResult.Ignore>(result)
    }

    @Test
    fun autoLaunchAppUseCase_launchApp() = runTest {
        packageRepository.setNonSystemApps(testTargetApplicationInfo)

        secureSettingsRepository.setWriteSecureSettings(true)

        userDataRepository.setUserData(
            userData = UserData(
                themeBrand = ThemeBrand.DEFAULT,
                darkThemeConfig = DarkThemeConfig.DARK,
                useDynamicColor = false,
                useAutoLaunch = true
            )
        )

        appSettingsRepository.setAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = true,
                    settingsType = SettingsType.SYSTEM,
                    packageName = PACKAGE_NAME_TEST,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test"
                )
            )
        )

        val result = autoLaunchUseCase(packageName = PACKAGE_NAME_TEST)

        assertIs<AppSettingsResult.Success>(result)

        assertNotNull(result.intent)
    }

    @Test
    fun autoLaunchAppUseCase_security_exception() = runTest {
        secureSettingsRepository.setWriteSecureSettings(false)

        userDataRepository.setUserData(
            userData = UserData(
                themeBrand = ThemeBrand.DEFAULT,
                darkThemeConfig = DarkThemeConfig.DARK,
                useDynamicColor = false,
                useAutoLaunch = true
            )
        )

        appSettingsRepository.setAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = true,
                    settingsType = SettingsType.SYSTEM,
                    packageName = PACKAGE_NAME_TEST,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test"
                )
            )
        )

        val result = autoLaunchUseCase(packageName = PACKAGE_NAME_TEST)

        assertIs<AppSettingsResult.SecurityException>(result)
    }

    @Test
    fun autoLaunchAppUseCase_ignore() = runTest {
        secureSettingsRepository.setWriteSecureSettings(true)

        userDataRepository.setUserData(
            userData = UserData(
                themeBrand = ThemeBrand.DEFAULT,
                darkThemeConfig = DarkThemeConfig.DARK,
                useDynamicColor = false,
                useAutoLaunch = false
            )
        )

        appSettingsRepository.setAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = true,
                    settingsType = SettingsType.SYSTEM,
                    packageName = PACKAGE_NAME_TEST,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test"
                )
            )
        )

        val result = autoLaunchUseCase(packageName = PACKAGE_NAME_TEST)

        assertIs<AutoLaunchResult.Ignore>(result)
    }
}

private const val PACKAGE_NAME_TEST = "packageNameTest"

private val testTargetApplicationInfo = listOf(
    TargetApplicationInfo(flags = 0, packageName = PACKAGE_NAME_TEST, label = "Label 0"),
    TargetApplicationInfo(flags = 0, packageName = PACKAGE_NAME_TEST, label = "Label 1")
)