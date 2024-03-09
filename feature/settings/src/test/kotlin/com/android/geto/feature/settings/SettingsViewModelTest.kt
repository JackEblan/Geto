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

import com.android.geto.core.model.DarkThemeConfig
import com.android.geto.core.model.ThemeBrand
import com.android.geto.core.testing.repository.TestUserDataRepository
import com.android.geto.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class SettingsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val userDataRepository = TestUserDataRepository()

    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setup() {
        viewModel = SettingsViewModel(userDataRepository)
    }

    @Test
    fun stateIsLoading_whenStarted() = runTest {
        assertEquals(SettingsUiState.Loading, viewModel.settingsUiState.value)
    }

    @Test
    fun stateIsSuccess_afterUserDataLoaded() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.settingsUiState.collect() }

        userDataRepository.setThemeBrand(ThemeBrand.ANDROID)
        userDataRepository.setDarkThemeConfig(DarkThemeConfig.DARK)

        assertEquals(
            SettingsUiState.Success(
                UserEditableSettings(
                    brand = ThemeBrand.ANDROID,
                    darkThemeConfig = DarkThemeConfig.DARK,
                    useDynamicColor = false,
                ),
            ),
            viewModel.settingsUiState.value,
        )

        collectJob.cancel()
    }
}