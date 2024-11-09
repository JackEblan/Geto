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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.geto.core.data.repository.UserDataRepository
import com.android.geto.foregroundservice.ForegroundServiceManager
import com.android.geto.framework.usagestatsmanager.UsageStatsManagerWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val foregroundServiceManager: ForegroundServiceManager,
    private val usageStatsManagerWrapper: UsageStatsManagerWrapper,
    private val userDataRepository: UserDataRepository,
) : ViewModel() {
    val isUsageStatsPermissionGranted get() = usageStatsManagerWrapper.isUsageStatsPermissionGranted()

    val serviceUiState = userDataRepository.userData.onEach { userData ->
        if (userData.useUsageStatsService && usageStatsManagerWrapper.isUsageStatsPermissionGranted()) {
            foregroundServiceManager.startForegroundService()
        } else {
            foregroundServiceManager.stopForegroundService()
        }
    }.map(ServiceUiState::Success).stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5_000),
        initialValue = ServiceUiState.Loading,
    )

    fun onEvent(event: ServiceEvent) {
        when (event) {
            is ServiceEvent.UpdateUsageStatsService -> {
                updateUsageStatsService(useUsageStatsService = event.useUsageStatsService)
            }

            ServiceEvent.RequestPermission -> {
                usageStatsManagerWrapper.requestUsageStatsPermission()
            }
        }
    }

    private fun updateUsageStatsService(useUsageStatsService: Boolean) {
        viewModelScope.launch {
            userDataRepository.setUsageStatsService(useUsageStatsService = useUsageStatsService)
        }
    }
}
