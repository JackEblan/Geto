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

package com.android.geto.feature.settings

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.core.designsystem.component.GetoBackground
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.model.DarkThemeConfig
import com.android.geto.core.model.ThemeBrand
import com.android.geto.core.screenshot.testing.util.DefaultTestDevices
import com.android.geto.core.screenshot.testing.util.captureForDevice
import com.android.geto.core.screenshot.testing.util.captureMultiDevice
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(application = HiltTestApplication::class)
@LooperMode(LooperMode.Mode.PAUSED)
class SettingsScreenScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun settingsScreen_populated() {
        composeTestRule.captureMultiDevice("SettingsScreenPopulated") {
            GetoTheme {
                SettingsScreen(settingsUiState = SettingsUiState.Success(
                    settings = UserEditableSettings(
                        brand = ThemeBrand.DEFAULT,
                        useDynamicColor = false,
                        darkThemeConfig = DarkThemeConfig.DARK,
                        useAutoLaunch = false
                    )
                ),
                               onThemeDialog = {},
                               onDarkDialog = {}, onCleanDialog = {},
                               onChangeDynamicColorPreference = {},
                               onNavigationIconClick = {},
                               onChangeAutoLaunchPreference = {})
            }
        }
    }

    @Test
    fun settingsScreen_loading() {
        composeTestRule.captureMultiDevice("SettingsScreenLoading") {
            GetoTheme {
                SettingsScreen(settingsUiState = SettingsUiState.Loading,
                               onThemeDialog = {},
                               onDarkDialog = {}, onCleanDialog = {},
                               onChangeDynamicColorPreference = {},
                               onNavigationIconClick = {},
                               onChangeAutoLaunchPreference = {})
            }
        }
    }

    @Test
    fun settingsScreen_populated_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "SettingsScreenPopulated",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    SettingsScreen(settingsUiState = SettingsUiState.Success(
                        settings = UserEditableSettings(
                            brand = ThemeBrand.DEFAULT,
                            useDynamicColor = false,
                            darkThemeConfig = DarkThemeConfig.DARK,
                            useAutoLaunch = false
                        )
                    ),
                                   onThemeDialog = {},
                                   onDarkDialog = {}, onCleanDialog = {},
                                   onChangeDynamicColorPreference = {},
                                   onNavigationIconClick = {},
                                   onChangeAutoLaunchPreference = {})
                }
            }
        }
    }

    @Test
    fun settingsScreen_loading_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "SettingsScreenLoading",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    SettingsScreen(settingsUiState = SettingsUiState.Loading,
                                   onThemeDialog = {},
                                   onDarkDialog = {}, onCleanDialog = {},
                                   onChangeDynamicColorPreference = {},
                                   onNavigationIconClick = {},
                                   onChangeAutoLaunchPreference = {})
                }
            }
        }
    }
}