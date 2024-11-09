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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.core.designsystem.component.AnimatedWavyCircle
import com.android.geto.core.designsystem.component.GetoLoadingWheel

@Composable
internal fun ServiceRoute(
    modifier: Modifier = Modifier,
    viewModel: ServiceViewModel = hiltViewModel(),
) {
    val serviceUiState = viewModel.serviceUiState.collectAsStateWithLifecycle().value

    ServiceScreen(
        modifier = modifier,
        serviceUiState = serviceUiState,
        isUsageStatsPermissionGranted = viewModel.isUsageStatsPermissionGranted,
        onEvent = viewModel::onEvent,
    )
}

@Composable
internal fun ServiceScreen(
    modifier: Modifier = Modifier,
    serviceUiState: ServiceUiState,
    isUsageStatsPermissionGranted: Boolean,
    onEvent: (ServiceEvent) -> Unit,
) {
    val stoppedColor = MaterialTheme.colorScheme.error

    val animatedColor = MaterialTheme.colorScheme.inversePrimary

    var animatedWavyCircleEnabled by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .testTag("service"),
    ) {
        when (serviceUiState) {
            ServiceUiState.Loading -> {
                LoadingState(
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            is ServiceUiState.Success -> {
                val useUsageStatsService = serviceUiState.userData.useUsageStatsService

                animatedWavyCircleEnabled = useUsageStatsService

                AnimatedWavyCircle(
                    modifier = Modifier.fillMaxSize(),
                    color = if (useUsageStatsService) animatedColor else stoppedColor,
                    onClick = {
                        if (isUsageStatsPermissionGranted) {
                            onEvent(
                                ServiceEvent.UpdateUsageStatsService(
                                    useUsageStatsService = animatedWavyCircleEnabled.not(),
                                ),
                            )
                        } else {
                            onEvent(
                                ServiceEvent.RequestPermission,
                            )
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun LoadingState(modifier: Modifier = Modifier) {
    GetoLoadingWheel(
        modifier = modifier,
        contentDescription = "GetoLoadingWheel",
    )
}
