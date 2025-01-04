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
package com.android.geto.feature.service

import com.android.geto.core.common.MainDispatcherRule
import com.android.geto.core.domain.usecase.UpdateUsageStatsForegroundServiceUseCase
import com.android.geto.core.domain.framework.FakeUsageStatsForegroundServiceManager
import com.android.geto.core.domain.framework.FakeUsageStatsManagerWrapper
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ServiceViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var usageStatsForegroundServiceManager: FakeUsageStatsForegroundServiceManager

    private lateinit var usageStatsManagerWrapper: FakeUsageStatsManagerWrapper

    private lateinit var updateUsageStatsForegroundServiceUseCase: UpdateUsageStatsForegroundServiceUseCase

    private lateinit var viewModel: ServiceViewModel

    @Before
    fun setup() {
        usageStatsForegroundServiceManager = FakeUsageStatsForegroundServiceManager()

        usageStatsManagerWrapper = FakeUsageStatsManagerWrapper()

        updateUsageStatsForegroundServiceUseCase = UpdateUsageStatsForegroundServiceUseCase(
            usageStatsForegroundServiceManager = usageStatsForegroundServiceManager,
            usageStatsManagerWrapper = usageStatsManagerWrapper,
        )

        viewModel = ServiceViewModel(
            usageStatsForegroundServiceManager = usageStatsForegroundServiceManager,
            updateUsageStatsForegroundServiceUseCase = updateUsageStatsForegroundServiceUseCase,
        )
    }

    @Test
    fun foregroundService_start() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.usageStatsForegroundServiceActive.collect()
        }

        usageStatsManagerWrapper.setUsageStatsPermissionGranted(true)

        viewModel.onEvent(event = ServiceEvent.UpdateUsageStatsForegroundService)

        assertTrue(viewModel.usageStatsForegroundServiceActive.value)
    }

    @Test
    fun foregroundService_stop() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.usageStatsForegroundServiceActive.collect()
        }

        usageStatsForegroundServiceManager.setBound(true)

        usageStatsForegroundServiceManager.updateForegroundService()

        usageStatsManagerWrapper.setUsageStatsPermissionGranted(true)

        viewModel.onEvent(event = ServiceEvent.UpdateUsageStatsForegroundService)

        assertFalse(viewModel.usageStatsForegroundServiceActive.value)
    }
}
