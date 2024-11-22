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

import com.android.geto.core.domain.model.AppSetting
import com.android.geto.core.domain.model.AppSettingsResult.DisabledAppSettings
import com.android.geto.core.domain.model.AppSettingsResult.EmptyAppSettings
import com.android.geto.core.domain.model.AppSettingsResult.InvalidValues
import com.android.geto.core.domain.model.AppSettingsResult.NoPermission
import com.android.geto.core.domain.model.AppSettingsResult.Success
import com.android.geto.core.domain.model.GetoApplicationInfo
import com.android.geto.core.domain.model.SettingType
import com.android.geto.core.testing.repository.TestAppSettingsRepository
import com.android.geto.core.testing.framework.FakePackageManagerWrapper
import com.android.geto.core.testing.repository.TestSecureSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class ApplyAppSettingsUseCaseTest {
    private lateinit var applyAppSettingsUseCase: ApplyAppSettingsUseCase

    private lateinit var packageRepository: FakePackageManagerWrapper

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var secureSettingsRepository: TestSecureSettingsRepository

    private val packageName = "com.android.geto"

    @Before
    fun setup() {
        packageRepository = FakePackageManagerWrapper()

        appSettingsRepository = TestAppSettingsRepository()

        secureSettingsRepository = TestSecureSettingsRepository()

        applyAppSettingsUseCase = ApplyAppSettingsUseCase(
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository,
        )
    }

    @Test
    fun applyAppSettingsUseCase_isEmptyAppSettings() = runTest {
        appSettingsRepository.setAppSettings(emptyList())

        assertEquals(
            expected = EmptyAppSettings,
            actual = applyAppSettingsUseCase(packageName = packageName),
        )
    }

    @Test
    fun applyAppSettingsUseCase_isDisabledAppSettings() = runTest {
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

        assertEquals(
            expected = DisabledAppSettings,
            actual = applyAppSettingsUseCase(packageName = packageName),
        )
    }

    @Test
    fun applyAppSettingsUseCase_isSuccess() = runTest {
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

        val getoApplicationInfos = List(5) { index ->
            GetoApplicationInfo(flags = 0, packageName = packageName, label = "Geto $index")
        }

        packageRepository.setApplicationInfos(getoApplicationInfos)

        secureSettingsRepository.setWriteSecureSettings(true)

        appSettingsRepository.setAppSettings(appSettings)

        assertEquals(
            expected = Success,
            actual = applyAppSettingsUseCase(packageName = packageName),
        )
    }

    @Test
    fun applyAppSettingsUseCase_isNoPermission() = runTest {
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

        appSettingsRepository.setAppSettings(appSettings)

        assertEquals(
            expected = NoPermission,
            actual = applyAppSettingsUseCase(packageName = packageName),
        )
    }

    @Test
    fun applyAppSettingsUseCase_isInvalidValues() = runTest {
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

        appSettingsRepository.setAppSettings(appSettings)

        assertEquals(
            expected = InvalidValues,
            actual = applyAppSettingsUseCase(packageName = packageName),
        )
    }
}
