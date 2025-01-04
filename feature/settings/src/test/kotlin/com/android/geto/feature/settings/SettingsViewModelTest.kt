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
package com.android.geto.feature.settings

import com.android.geto.common.MainDispatcherRule
import com.android.geto.core.domain.model.DarkThemeConfig
import com.android.geto.core.domain.model.ThemeBrand
import com.android.geto.core.domain.usecase.CleanAppSettingsUseCase
import com.android.geto.core.domain.framework.FakePackageManagerWrapper
import com.android.geto.core.domain.repository.TestAppSettingsRepository
import com.android.geto.core.domain.repository.TestUserDataRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertIs

class SettingsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var userDataRepository: TestUserDataRepository

    private lateinit var packageManagerWrapper: FakePackageManagerWrapper

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var cleanAppSettingsUseCase: CleanAppSettingsUseCase

    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setUp() {
        userDataRepository = TestUserDataRepository()

        packageManagerWrapper = FakePackageManagerWrapper()

        appSettingsRepository = TestAppSettingsRepository()

        cleanAppSettingsUseCase = CleanAppSettingsUseCase(
            packageManagerWrapper = packageManagerWrapper,
            appSettingsRepository = appSettingsRepository,
        )

        viewModel = SettingsViewModel(
            userDataRepository = userDataRepository,
            cleanAppSettingsUseCase = cleanAppSettingsUseCase,
        )
    }

    @Test
    fun settingsUiState_isLoading_whenStarted() {
        assertIs<SettingsUiState.Loading>(viewModel.settingsUiState.value)
    }

    @Test
    fun settingsUiState_isSuccess() = runTest {
        userDataRepository.setThemeBrand(ThemeBrand.GREEN)

        userDataRepository.setDarkThemeConfig(DarkThemeConfig.DARK)

        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.settingsUiState.collect() }

        assertIs<SettingsUiState.Success>(viewModel.settingsUiState.value)

        collectJob.cancel()
    }
}
