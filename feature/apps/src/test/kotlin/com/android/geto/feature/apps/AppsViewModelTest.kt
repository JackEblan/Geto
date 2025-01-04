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

import com.android.geto.core.domain.model.GetoApplicationInfo
import com.android.geto.core.domain.framework.FakePackageManagerWrapper
import com.android.geto.common.MainDispatcherRule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertIs

class AppsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var packageManagerWrapper: FakePackageManagerWrapper

    private lateinit var viewModel: AppsViewModel

    @Before
    fun setup() {
        packageManagerWrapper = FakePackageManagerWrapper()

        viewModel = AppsViewModel(packageManagerWrapper = packageManagerWrapper)
    }

    @Test
    fun appsUiState_isLoading_whenStarted() {
        assertIs<AppsUiState.Loading>(viewModel.appsUiState.value)
    }

    @Test
    fun appsUiState_isSuccess_whenQueryIntentActivities() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.appsUiState.collect()
        }

        val getoApplicationInfos = List(2) { index ->
            GetoApplicationInfo(
                flags = 0,
                icon = ByteArray(0),
                packageName = "com.android.geto$index",
                label = "Geto $index",
            )
        }

        packageManagerWrapper.setApplicationInfos(getoApplicationInfos)

        assertIs<AppsUiState.Success>(viewModel.appsUiState.value)
    }

    @Test
    fun appsUiState_isSuccessEmpty_whenQueryIntentActivities() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.appsUiState.collect()
        }

        val getoApplicationInfos = List(2) { index ->
            GetoApplicationInfo(
                flags = 0,
                icon = ByteArray(0),
                packageName = "com.android.geto$index",
                label = "Geto $index",
            )
        }

        packageManagerWrapper.setApplicationInfos(getoApplicationInfos)

        assertIs<AppsUiState.Success>(viewModel.appsUiState.value)
    }
}
