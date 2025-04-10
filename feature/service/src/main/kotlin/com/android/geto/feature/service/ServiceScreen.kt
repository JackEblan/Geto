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

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.geto.designsystem.component.AnimatedWavyCircle

@Composable
internal fun ServiceRoute(
    modifier: Modifier = Modifier,
    viewModel: ServiceViewModel = hiltViewModel(),
) {
    val usageStatsForegroundServiceActive by viewModel.usageStatsForegroundServiceActive.collectAsStateWithLifecycle()

    ServiceScreen(
        modifier = modifier,
        usageStatsForegroundServiceActive = usageStatsForegroundServiceActive,
        onUpdateUsageStatsForegroundService = viewModel::updateUsageStatsForegroundService,
    )
}

@Composable
internal fun ServiceScreen(
    modifier: Modifier = Modifier,
    usageStatsForegroundServiceActive: Boolean,
    onUpdateUsageStatsForegroundService: () -> Unit,
) {
    AnimatedWavyCircle(
        modifier = modifier.fillMaxSize(),
        active = usageStatsForegroundServiceActive,
        onClick = onUpdateUsageStatsForegroundService,
    )
}
