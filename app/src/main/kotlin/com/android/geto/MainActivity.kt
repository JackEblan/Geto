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
package com.android.geto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.android.geto.designsystem.theme.GetoTheme
import com.android.geto.domain.model.DarkThemeConfig
import com.android.geto.domain.model.ThemeBrand
import com.android.geto.navigation.GetoNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    private data class ThemeSettings(
        val themeBrand: ThemeBrand,
        val darkThemeConfig: DarkThemeConfig,
        val dynamicTheme: Boolean,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        var themeSettings by mutableStateOf(
            ThemeSettings(
                themeBrand = ThemeBrand.GREEN,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                dynamicTheme = false,
            ),
        )

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    themeSettings = when (uiState) {
                        MainActivityUiState.Loading -> themeSettings

                        is MainActivityUiState.Success -> ThemeSettings(
                            themeBrand = uiState.userData.themeBrand,
                            darkThemeConfig = uiState.userData.darkThemeConfig,
                            dynamicTheme = uiState.userData.useDynamicColor,
                        )
                    }
                }
            }
        }

        splashScreen.setKeepOnScreenCondition { viewModel.uiState.value == MainActivityUiState.Loading }

        setContent {
            val navController = rememberNavController()

            GetoTheme(
                themeBrand = themeSettings.themeBrand,
                darkThemeConfig = themeSettings.darkThemeConfig,
                dynamicTheme = themeSettings.dynamicTheme,
            ) {
                Surface {
                    GetoNavHost(navController = navController)
                }
            }
        }
    }
}
