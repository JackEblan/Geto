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
package com.android.geto.domain.usecase

import com.android.geto.domain.model.AddAppSettingResult
import com.android.geto.domain.model.AppSetting
import com.android.geto.domain.model.SettingType
import com.android.geto.domain.repository.TestAppSettingsRepository
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class AddAppSettingUseCaseTest {
    private lateinit var addAppSettingUseCase: AddAppSettingUseCase

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private val packageName = "com.android.geto"

    @BeforeTest
    fun setup() {
        appSettingsRepository = TestAppSettingsRepository()

        addAppSettingUseCase = AddAppSettingUseCase(
            appSettingsRepository = appSettingsRepository,
        )
    }

    @Test
    fun addAppSettingResult_isSuccess_whenAddAppSetting() = runTest {
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

        val newAppSetting = AppSetting(
            id = 6,
            enabled = true,
            settingType = SettingType.SYSTEM,
            packageName = packageName,
            label = "Geto",
            key = "Geto",
            valueOnLaunch = "0",
            valueOnRevert = "1",
        )

        appSettingsRepository.setAppSettings(appSettings)

        assertEquals(
            expected = AddAppSettingResult.SUCCESS,
            actual = addAppSettingUseCase(appSetting = newAppSetting),
        )
    }

    @Test
    fun addAppSettingResult_isFailed_whenAddAppSetting() = runTest {
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

        appSettingsRepository.setAppSettings(appSettings)

        assertEquals(
            expected = AddAppSettingResult.FAILED,
            actual = addAppSettingUseCase(appSetting = appSettings.first()),
        )
    }
}
