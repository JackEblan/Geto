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

package com.android.geto.feature.securesettingslist

import com.android.geto.core.testing.repository.TestClipboardRepository
import com.android.geto.core.testing.repository.TestSecureSettingsRepository
import com.android.geto.core.testing.util.MainDispatcherRule
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertIs
import kotlin.test.assertNull

@OptIn(ExperimentalCoroutinesApi::class)
class SecureSettingsListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var settingsRepository: TestSecureSettingsRepository

    private lateinit var clipboardRepository: TestClipboardRepository

    private lateinit var viewModel: SecureSettingsListViewModel

    @Before
    fun setUp() {
        settingsRepository = TestSecureSettingsRepository()

        clipboardRepository = TestClipboardRepository()

        viewModel = SecureSettingsListViewModel(
            secureSettingsRepository = settingsRepository, clipboardRepository = clipboardRepository
        )
    }

    @Test
    fun stateIsSuccess_whenEventIsGetSecureSettingsList_selectedSettingsTypeIndexIs0() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.secureSettingsListUiState.collect() }

        viewModel.getSecureSettingsList(0)

        testScheduler.runCurrent()

        testScheduler.advanceTimeBy(500)

        testScheduler.advanceUntilIdle()

        val item = viewModel.secureSettingsListUiState.value

        assertIs<SecureSettingsListUiState.Success>(item)

        collectJob.cancel()
    }

    @Test
    fun stateIsSuccess_whenEventIsGetSecureSettingsList_selectedSettingsTypeIndexIs1() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.secureSettingsListUiState.collect() }

        viewModel.getSecureSettingsList(1)

        testScheduler.runCurrent()

        testScheduler.advanceTimeBy(viewModel.loadingDelay)

        testScheduler.advanceUntilIdle()

        val item = viewModel.secureSettingsListUiState.value

        assertIs<SecureSettingsListUiState.Success>(item)

        collectJob.cancel()
    }

    @Test
    fun stateIsSuccess_whenGetSecureSettingsList_selectedSettingsTypeIndexIs2() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.secureSettingsListUiState.collect() }

        viewModel.getSecureSettingsList(2)

        testScheduler.runCurrent()

        testScheduler.advanceTimeBy(500)

        testScheduler.advanceUntilIdle()

        val item = viewModel.secureSettingsListUiState.value

        assertIs<SecureSettingsListUiState.Success>(item)

        collectJob.cancel()
    }

    @Test
    fun snackBarNotNull_whenCopySecureSettings_api32AndLower() = runTest {
        val collectJob =
            launch(UnconfinedTestDispatcher()) { viewModel.secureSettingsListUiState.collect() }

        clipboardRepository.setApi32(false)

        viewModel.copySecureSettings("Hi")

        assertNotNull(viewModel.snackBar.value)

        collectJob.cancel()
    }

    @Test
    fun snackBarNull_whenCopySecureSettings_api32AndHigher() = runTest {
        clipboardRepository.setApi32(true)

        viewModel.copySecureSettings("Hi")

        assertNull(viewModel.snackBar.value)
    }
}