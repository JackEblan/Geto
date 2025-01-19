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
package com.android.geto.feature.apps

import com.android.geto.common.MainDispatcherRule
import com.android.geto.domain.framework.DummyPackageManagerWrapper
import com.android.geto.domain.model.GetoApplicationInfo
import com.android.geto.domain.repository.TestAppSettingsRepository
import com.android.geto.domain.repository.TestGetoApplicationInfosRepository
import com.android.geto.domain.usecase.UpdateGetoApplicationInfosUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue

class AppsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var updateGetoApplicationInfosUseCase: UpdateGetoApplicationInfosUseCase

    private lateinit var packageManagerWrapper: DummyPackageManagerWrapper

    private lateinit var getoApplicationInfosRepository: TestGetoApplicationInfosRepository

    private lateinit var appSettingsRepository: TestAppSettingsRepository

    private lateinit var viewModel: AppsViewModel

    @BeforeTest
    fun setup() {
        packageManagerWrapper = DummyPackageManagerWrapper()

        getoApplicationInfosRepository = TestGetoApplicationInfosRepository()

        appSettingsRepository = TestAppSettingsRepository()

        updateGetoApplicationInfosUseCase = UpdateGetoApplicationInfosUseCase(
            defaultDispatcher = mainDispatcherRule.testDispatcher,
            packageManagerWrapper = packageManagerWrapper,
            getoApplicationInfosRepository = getoApplicationInfosRepository,
            appSettingsRepository = appSettingsRepository,

            )
        viewModel = AppsViewModel(
            updateGetoApplicationInfosUseCase = updateGetoApplicationInfosUseCase,
            getoApplicationInfosRepository = getoApplicationInfosRepository,
        )
    }

    @Test
    fun appsUiState_isLoading_whenStarted() {
        assertIs<AppsUiState.Loading>(viewModel.appsUiState.value)
    }

    @Test
    fun searchGetoApplicationInfos_isEmpty_whenStarted() {
        assertTrue(viewModel.searchGetoApplicationInfos.value.isEmpty())
    }

    @Test
    fun appsUiState_isSuccess_whenUpdateGetoApplicationInfos() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.appsUiState.collect()
        }

        val getoApplicationInfos = List(2) { index ->
            GetoApplicationInfo(
                flags = 0,
                iconPath = "",
                packageName = "com.android.geto$index",
                label = "Geto $index",
            )
        }

        getoApplicationInfosRepository.setGetoApplicationInfos(getoApplicationInfos)

        viewModel.updateGetoApplicationInfos()

        val result = assertIs<AppsUiState.Success>(viewModel.appsUiState.value)

        assertTrue(result.getoApplicationInfos.isNotEmpty())
    }

    @Test
    fun searchGetoApplicationInfos_whenGetGetoApplicationInfoByPackageName() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.searchGetoApplicationInfos.collect()
        }

        val getoApplicationInfos = List(10) { index ->
            GetoApplicationInfo(
                flags = 0,
                iconPath = "",
                packageName = "com.android.geto$index",
                label = "Geto $index",
            )
        }

        getoApplicationInfosRepository.setGetoApplicationInfos(getoApplicationInfos)

        viewModel.getGetoApplicationInfoByPackageName(text = "com.android.geto")

        assertTrue(viewModel.searchGetoApplicationInfos.value.size == 10)
    }

    @Test
    fun searchGetoApplicationInfos_isEmpty_whenGetGetoApplicationInfoByPackageName() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.searchGetoApplicationInfos.collect()
        }

        val getoApplicationInfos = List(10) { index ->
            GetoApplicationInfo(
                flags = 0,
                iconPath = "",
                packageName = "com.android.geto$index",
                label = "Geto $index",
            )
        }

        getoApplicationInfosRepository.setGetoApplicationInfos(getoApplicationInfos)

        viewModel.getGetoApplicationInfoByPackageName(text = "Invalid Search")

        assertTrue(viewModel.searchGetoApplicationInfos.value.isEmpty())
    }
}
