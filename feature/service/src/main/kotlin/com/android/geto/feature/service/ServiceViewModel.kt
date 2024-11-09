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
import com.android.geto.foregroundservice.ForegroundServiceManager
import com.android.geto.framework.usagestatsmanager.UsageStatsManagerWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ServiceViewModel @Inject constructor(
    private val foregroundServiceManager: ForegroundServiceManager,
    private val usageStatsManagerWrapper: UsageStatsManagerWrapper,
) : ViewModel() {
    private val _serviceState = MutableStateFlow(
        ServiceState(
            isUsageStatsActive = foregroundServiceManager.isActive(),
            isUsageStatsPermissionGranted = usageStatsManagerWrapper.isUsageStatsPermissionGranted(),
        ),
    )
    val serviceState = _serviceState.asStateFlow()

    fun onEvent(event: ServiceEvent) {
        when (event) {
            ServiceEvent.UpdateUsageStatsForegroundService -> {
                updateUsageForegroundService()
            }

            ServiceEvent.RequestPermission -> {
                usageStatsManagerWrapper.requestUsageStatsPermission()
            }
        }
    }

    private fun updateUsageForegroundService() {
        _serviceState.update { currentState ->
            currentState.copy(
                isUsageStatsActive = foregroundServiceManager.isActive().not(),
                isUsageStatsPermissionGranted = usageStatsManagerWrapper.isUsageStatsPermissionGranted(),
            )
        }

        if (foregroundServiceManager.isActive()) {
            foregroundServiceManager.stopForegroundService()
        } else {
            foregroundServiceManager.startForegroundService()
        }
    }
}
