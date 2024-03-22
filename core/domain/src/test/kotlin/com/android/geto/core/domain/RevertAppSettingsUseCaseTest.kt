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
import com.android.geto.core.model.SettingsType
import com.android.geto.core.testing.repository.TestAppSettingsRepository
import com.android.geto.core.testing.repository.TestSecureSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertIs

class RevertAppSettingsUseCaseTest {
    private lateinit var revertAppSettingsUseCase: RevertAppSettingsUseCase

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var secureSettingsRepository: TestSecureSettingsRepository

    @Before
    fun setup() {
        secureSettingsRepository = TestSecureSettingsRepository()

        appSettingsRepository = TestAppSettingsRepository()

        revertAppSettingsUseCase = RevertAppSettingsUseCase(
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository
        )
    }

    @Test
    fun revertAppSettingsUseCase_empty_app_settings_list() = runTest {
        appSettingsRepository.setAppSettings(emptyList())

        val result = revertAppSettingsUseCase(packageName = PACKAGE_NAME_TEST)

        assertIs<AppSettingsResult.EmptyAppSettingsList>(result)
    }


    @Test
    fun revertAppSettingsUseCase_app_settings_disabled() = runTest {
        appSettingsRepository.setAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = false,
                    settingsType = SettingsType.SYSTEM, packageName = PACKAGE_NAME_TEST,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test"
                )
            )
        )

        val result = revertAppSettingsUseCase(packageName = PACKAGE_NAME_TEST)

        assertIs<AppSettingsResult.AppSettingsDisabled>(result)
    }

    @Test
    fun revertAppSettingsUseCase_success() = runTest {
        secureSettingsRepository.setWriteSecureSettings(true)

        appSettingsRepository.setAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = true,
                    settingsType = SettingsType.SYSTEM, packageName = PACKAGE_NAME_TEST,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test"
                )
            )
        )

        val result = revertAppSettingsUseCase(packageName = PACKAGE_NAME_TEST)

        assertIs<AppSettingsResult.Success>(result)
    }

    @Test
    fun revertAppSettingsUseCase_security_exception() = runTest {
        secureSettingsRepository.setWriteSecureSettings(false)

        appSettingsRepository.setAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = true,
                    settingsType = SettingsType.SYSTEM, packageName = PACKAGE_NAME_TEST,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test"
                )
            )
        )

        val result = revertAppSettingsUseCase(packageName = PACKAGE_NAME_TEST)

        assertIs<AppSettingsResult.SecurityException>(result)
    }
}

private const val PACKAGE_NAME_TEST = "PACKAGE_NAME_TEST"