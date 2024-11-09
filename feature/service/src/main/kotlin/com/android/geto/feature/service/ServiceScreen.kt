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

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.geto.core.designsystem.component.AnimatedWavyCircle

@Composable
internal fun ServiceRoute(
    modifier: Modifier = Modifier,
    viewModel: ServiceViewModel = hiltViewModel(),
) {
    val isUsageStatsRunning = false

    ServiceScreen(
        modifier = modifier,
        isUsageStatsRunning = isUsageStatsRunning,
        isUsageStatsPermissionGranted = viewModel.isUsageStatsPermissionGranted,
        onEvent = viewModel::onEvent,
    )
}

@Composable
internal fun ServiceScreen(
    modifier: Modifier = Modifier,
    isUsageStatsRunning: Boolean,
    isUsageStatsPermissionGranted: Boolean,
    onEvent: (ServiceEvent) -> Unit,
) {
    val stoppedColor = MaterialTheme.colorScheme.error

    val animatedColor = MaterialTheme.colorScheme.inversePrimary

    AnimatedWavyCircle(
        modifier = modifier,
        enabled = isUsageStatsRunning,
        color = if (isUsageStatsRunning) animatedColor else stoppedColor,
        onClick = {
            if (isUsageStatsRunning && isUsageStatsPermissionGranted) {
                onEvent(ServiceEvent.StopForegroundService)
            } else if (isUsageStatsPermissionGranted.not()) {
                onEvent(ServiceEvent.RequestPermission)
            } else {
                onEvent(ServiceEvent.StartForegroundService)
            }
        },
    )
}
