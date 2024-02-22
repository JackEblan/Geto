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

package com.android.geto.feature.applist

import com.android.geto.core.model.NonSystemApp
import com.android.geto.core.testing.repository.TestPackageRepository
import com.android.geto.core.testing.util.MainDispatcherRule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertIs

class AppListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var packageRepository: TestPackageRepository

    private lateinit var viewModel: AppListViewModel

    @Before
    fun setup() {
        packageRepository = TestPackageRepository()

        viewModel = AppListViewModel(packageRepository)
    }

    @Test
    fun appListUiStateIsInitiallyLoading() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.appListUiState.collect() }

        val item = viewModel.appListUiState.value

        assertIs<AppListUiState.Loading>(item)

        collectJob.cancel()
    }

    @Test
    fun appListUiStateIsSuccess_whenGetNonSystemApps() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.appListUiState.collect() }

        packageRepository.sendNonSystemApps(nonSystemAppsTestData)

        viewModel.getNonSystemApps()

        val item = viewModel.appListUiState.value

        assertIs<AppListUiState.Success>(item)

        collectJob.cancel()
    }
}

private val nonSystemAppsTestData = listOf(
    NonSystemApp(packageName = "com.android.geto", label = "Geto"),
    NonSystemApp(packageName = "com.android.geto1", label = "Geto1"),
    NonSystemApp(packageName = "com.android.geto2", label = "Geto2")
)
