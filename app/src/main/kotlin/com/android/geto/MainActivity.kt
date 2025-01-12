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

import android.content.res.Configuration
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
import androidx.core.util.Consumer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.rememberNavController
import com.android.geto.designsystem.theme.GetoTheme
import com.android.geto.navigation.GetoNavHost
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        var themeSettings by mutableStateOf(
            ThemeSettings(
                darkTheme = false,
                greenTheme = true,
                purpleTheme = false,
                dynamicTheme = false,
            ),
        )

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                combine(
                    isSystemInDarkTheme(),
                    viewModel.uiState,
                ) { systemDark, uiState ->
                    ThemeSettings(
                        darkTheme = uiState.shouldUseDarkTheme(systemDark),
                        greenTheme = uiState.shouldUseGreenTheme,
                        purpleTheme = uiState.shouldUsePurpleTheme,
                        dynamicTheme = uiState.shouldUseDynamicTheme,
                    )
                }.distinctUntilChanged().collect { newThemeSettings ->
                    enableEdgeToEdge()
                    themeSettings = newThemeSettings
                }
            }
        }

        splashScreen.setKeepOnScreenCondition { viewModel.uiState.value.shouldKeepSplashScreen() }

        setContent {
            val navController = rememberNavController()

            GetoTheme(
                greenTheme = themeSettings.greenTheme,
                purpleTheme = themeSettings.purpleTheme,
                darkTheme = themeSettings.darkTheme,
                dynamicTheme = themeSettings.dynamicTheme,
            ) {
                Surface {
                    GetoNavHost(navController = navController)
                }
            }
        }
    }
}

private val Configuration.isSystemInDarkTheme
    get() = (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES

private fun ComponentActivity.isSystemInDarkTheme() = callbackFlow {
    channel.trySend(resources.configuration.isSystemInDarkTheme)
    val listener = Consumer<Configuration> {
        channel.trySend(it.isSystemInDarkTheme)
    }
    addOnConfigurationChangedListener(listener)
    awaitClose { removeOnConfigurationChangedListener(listener) }
}.distinctUntilChanged().conflate()

data class ThemeSettings(
    val greenTheme: Boolean,
    val purpleTheme: Boolean,
    val darkTheme: Boolean,
    val dynamicTheme: Boolean,
)
