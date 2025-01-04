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
import com.android.geto.core.domain.model.ForegroundServiceAppSettingsResult
import com.android.geto.core.domain.model.GetoLifeCycle
import com.android.geto.core.domain.model.GetoUsageEvent
import com.android.geto.core.domain.model.SettingType
import com.android.geto.core.domain.framework.FakeUsageStatsManagerWrapper
import com.android.geto.core.domain.repository.TestAppSettingsRepository
import com.android.geto.core.domain.repository.TestSecureSettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class ForegroundServiceAppSettingsUseCaseTest {
    private lateinit var usageStatsManagerWrapper: FakeUsageStatsManagerWrapper

    private lateinit var applyAppSettingsUseCase: ApplyAppSettingsUseCase

    private lateinit var revertAppSettingsUseCase: RevertAppSettingsUseCase

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var secureSettingsRepository: TestSecureSettingsRepository

    private lateinit var foregroundServiceAppSettingsUseCase: ForegroundServiceAppSettingsUseCase

    private val packageName = "com.android.geto"

    @Before
    fun setup() {
        usageStatsManagerWrapper = FakeUsageStatsManagerWrapper()

        appSettingsRepository = TestAppSettingsRepository()

        secureSettingsRepository = TestSecureSettingsRepository()

        applyAppSettingsUseCase = ApplyAppSettingsUseCase(
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository,
        )

        revertAppSettingsUseCase = RevertAppSettingsUseCase(
            appSettingsRepository = appSettingsRepository,
            secureSettingsRepository = secureSettingsRepository,
        )

        foregroundServiceAppSettingsUseCase = ForegroundServiceAppSettingsUseCase(
            usageStatsManagerWrapper = usageStatsManagerWrapper,
            applyAppSettingsUseCase = applyAppSettingsUseCase,
            revertAppSettingsUseCase = revertAppSettingsUseCase,
        )
    }

    @Test
    fun foregroundServiceAppSettingsUseCase_isSuccess_whenActivityResumed() = runTest {
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

        secureSettingsRepository.setWriteSecureSettings(true)

        usageStatsManagerWrapper.setGetoUsageEvent(
            GetoUsageEvent(
                packageName = packageName,
                getoLifeCycle = GetoLifeCycle.ACTIVITY_RESUMED,
            ),
        )

        assertEquals(
            expected = ForegroundServiceAppSettingsResult.Success(packageName = packageName),
            actual = foregroundServiceAppSettingsUseCase().first(),
        )
    }

    @Test
    fun foregroundServiceAppSettingsUseCase_isIgnore_whenActivityResumed() = runTest {
        appSettingsRepository.setAppSettings(emptyList())

        secureSettingsRepository.setWriteSecureSettings(false)

        usageStatsManagerWrapper.setGetoUsageEvent(
            GetoUsageEvent(
                packageName = packageName,
                getoLifeCycle = GetoLifeCycle.ACTIVITY_RESUMED,
            ),
        )

        assertEquals(
            expected = ForegroundServiceAppSettingsResult.Ignore,
            actual = foregroundServiceAppSettingsUseCase().first(),
        )
    }

    @Test
    fun foregroundServiceAppSettingsUseCase_isIgnore_whenActivityPaused() = runTest {
        appSettingsRepository.setAppSettings(emptyList())

        secureSettingsRepository.setWriteSecureSettings(false)

        usageStatsManagerWrapper.setGetoUsageEvent(
            GetoUsageEvent(
                packageName = packageName,
                getoLifeCycle = GetoLifeCycle.ACTIVITY_PAUSED,
            ),
        )

        assertEquals(
            expected = ForegroundServiceAppSettingsResult.Ignore,
            actual = foregroundServiceAppSettingsUseCase().first(),
        )
    }
}
