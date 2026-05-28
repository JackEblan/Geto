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
package com.android.geto.activity.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.android.geto.designsystem.theme.GetoTheme
import com.android.geto.framework.launcherapps.AndroidLauncherAppsWrapper
import com.android.geto.framework.notificationmanager.AndroidNotificationManagerWrapper
import com.android.geto.navigation.GetoNavHost
import com.android.geto.ui.local.LocalLauncherApps
import com.android.geto.ui.local.LocalNotificationManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var androidLauncherAppsWrapper: AndroidLauncherAppsWrapper

    @Inject
    lateinit var androidNotificationManagerWrapper: AndroidNotificationManagerWrapper

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        setContent {
            CompositionLocalProvider(
                LocalLauncherApps provides androidLauncherAppsWrapper,
                LocalNotificationManager provides androidNotificationManagerWrapper,
            ) {
                val navController = rememberNavController()

                val mainActivityUiState by viewModel.uiState.collectAsStateWithLifecycle()

                when (val uiState = mainActivityUiState) {
                    MainActivityUiState.Loading -> Unit
                    is MainActivityUiState.Success -> {
                        GetoTheme(
                            theme = uiState.userData.theme,
                            dynamicTheme = uiState.userData.dynamicTheme,
                        ) {
                            Surface {
                                GetoNavHost(navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}
