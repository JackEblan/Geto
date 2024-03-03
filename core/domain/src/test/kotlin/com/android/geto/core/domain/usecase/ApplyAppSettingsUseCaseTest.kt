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

package com.android.geto.core.domain.usecase

import com.android.geto.core.domain.ApplyAppSettingsUseCase
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SettingsType
import com.android.geto.core.testing.repository.TestAppSettingsRepository
import com.android.geto.core.testing.repository.TestSecureSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class ApplyAppSettingsUseCaseTest {
    private lateinit var applyAppSettingsUseCase: ApplyAppSettingsUseCase

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var secureSettingsRepository: TestSecureSettingsRepository

    private val packageNameTest = "packageNameTest"

    @Before
    fun setup() {
        secureSettingsRepository = TestSecureSettingsRepository()

        appSettingsRepository = TestAppSettingsRepository()

        applyAppSettingsUseCase = ApplyAppSettingsUseCase(
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository
        )
    }

    @Test
    fun test_onEmptyAppSettingsListIsNotBlank() = runTest {
        appSettingsRepository.setAppSettings(emptyList())

        applyAppSettingsUseCase(packageName = packageNameTest, onEmptyAppSettingsList = {
            assertTrue { it.isNotBlank() }
        }, onAppSettingsDisabled = {}, onApplied = {}, onSecurityException = {}, onFailure = {})
    }


    @Test
    fun onAppSettingsDisabledIsNotBlank() = runTest {
        appSettingsRepository.setAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = false,
                    settingsType = SettingsType.SYSTEM,
                    packageName = packageNameTest,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test"
                )
            )
        )

        applyAppSettingsUseCase(packageName = packageNameTest,
                                onEmptyAppSettingsList = {},
                                onAppSettingsDisabled = { assertTrue { it.isNotBlank() } },
                                onApplied = { },
                                onSecurityException = {},
                                onFailure = {})
    }

    @Test
    fun onAppliedIsNotBlank() = runTest {
        secureSettingsRepository.setWriteSecureSettings(true)

        appSettingsRepository.setAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = true,
                    settingsType = SettingsType.SYSTEM,
                    packageName = packageNameTest,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test"
                )
            )
        )

        applyAppSettingsUseCase(packageName = packageNameTest,
                                onEmptyAppSettingsList = {},
                                onAppSettingsDisabled = {},
                                onApplied = { assertTrue { it.isNotBlank() } },
                                onSecurityException = {},
                                onFailure = {})
    }

    @Test
    fun onSecurityExceptionIsNotBlank() = runTest {
        secureSettingsRepository.setWriteSecureSettings(false)

        appSettingsRepository.setAppSettings(
            listOf(
                AppSettings(
                    id = 0,
                    enabled = true,
                    settingsType = SettingsType.SYSTEM,
                    packageName = packageNameTest,
                    label = "system",
                    key = "key",
                    valueOnLaunch = "test",
                    valueOnRevert = "test"
                )
            )
        )

        applyAppSettingsUseCase(packageName = packageNameTest,
                                onEmptyAppSettingsList = {},
                                onAppSettingsDisabled = { },
                                onApplied = {},
                                onSecurityException = { assertTrue { it.isNotBlank() } },
                                onFailure = {})
    }
}