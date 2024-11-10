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

import com.android.geto.core.testing.framework.FakeForegroundServiceManager
import com.android.geto.core.testing.framework.FakeUsageStatsManagerWrapper
import com.android.geto.core.testing.util.MainDispatcherRule
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

    private lateinit var foregroundServiceManager: FakeForegroundServiceManager

    private lateinit var usageStatsManagerWrapper: FakeUsageStatsManagerWrapper

    private lateinit var viewModel: ServiceViewModel

    @Before
    fun setup() {
        foregroundServiceManager = FakeForegroundServiceManager()

        usageStatsManagerWrapper = FakeUsageStatsManagerWrapper()

        viewModel = ServiceViewModel(
            foregroundServiceManager = foregroundServiceManager,
            usageStatsManagerWrapper = usageStatsManagerWrapper,
        )
    }

    @Test
    fun foregroundService_inActive_noPermission() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.isUsageStatsActive.collect()
        }

        usageStatsManagerWrapper.setUsageStatsPermissionGranted(false)

        viewModel.onEvent(event = ServiceEvent.UpdateUsageStatsForegroundService)

        assertFalse(viewModel.isUsageStatsActive.value)
    }

    @Test
    fun foregroundService_fromActive_to_inActive() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.isUsageStatsActive.collect()
        }

        foregroundServiceManager.setActive(true)

        usageStatsManagerWrapper.setUsageStatsPermissionGranted(true)

        viewModel.onEvent(event = ServiceEvent.UpdateUsageStatsForegroundService)

        assertFalse(viewModel.isUsageStatsActive.value)
    }

    @Test
    fun foregroundService_fromInActive_to_active() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.isUsageStatsActive.collect()
        }

        foregroundServiceManager.setActive(false)

        usageStatsManagerWrapper.setUsageStatsPermissionGranted(true)

        viewModel.onEvent(event = ServiceEvent.UpdateUsageStatsForegroundService)

        assertTrue(viewModel.isUsageStatsActive.value)
    }
}