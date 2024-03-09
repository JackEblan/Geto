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

package com.android.geto.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.android.geto.core.designsystem.component.GetoBackground
import com.android.geto.core.designsystem.component.GetoGradientBackground
import com.android.geto.core.designsystem.theme.GradientColors
import com.android.geto.core.designsystem.theme.LocalGradientColors
import com.android.geto.feature.settings.SettingsDialog
import com.android.geto.navigation.GetoNavHost

@Composable
fun GetoApp() {
    val navController = rememberNavController()

    val shouldShowGradientBackground = true

    var showSettingsDialog by rememberSaveable { mutableStateOf(false) }

    GetoBackground {
        GetoGradientBackground(
            gradientColors = if (shouldShowGradientBackground) {
                LocalGradientColors.current
            } else {
                GradientColors()
            },
        ) {

            if (showSettingsDialog) {
                SettingsDialog(
                    onDismiss = { showSettingsDialog = false },
                )
            }

            GetoNavHost(navController = navController, onSettingsClick = {
                showSettingsDialog = true
            })
        }
    }
}