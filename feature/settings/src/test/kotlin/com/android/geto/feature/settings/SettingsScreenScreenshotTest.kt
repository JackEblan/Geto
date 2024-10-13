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
import androidx.compose.material3.Surface
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.model.DarkThemeConfig
import com.android.geto.core.model.ThemeBrand
import com.android.geto.core.model.UserData
import com.android.geto.core.screenshottesting.util.DefaultTestDevices
import com.android.geto.core.screenshottesting.util.captureScreenForDevice
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
        composeTestRule.captureScreenForDevice(
            fileName = "SettingsScreenPopulated",
            deviceName = "foldable",
            deviceSpec = DefaultTestDevices.FOLDABLE.spec,
        ) {
            GetoTheme {
                SettingsScreen(
                    settingsUiState = SettingsUiState.Success(
                        userData = UserData(
                            themeBrand = ThemeBrand.PURPLE,
                            useDynamicColor = false,
                            darkThemeConfig = DarkThemeConfig.DARK,
                            useAutoLaunch = false,
                        ),
                    ),
                    supportDynamicColor = true,
                    onUpdateThemeBrand = {},
                    onUpdateDarkThemeConfig = {},
                    onCleanAppSettings = {},
                    onChangeDynamicColorPreference = {},
                    onChangeAutoLaunchPreference = {},
                )
            }
        }
    }

    @Test
    fun settingsScreen_loading() {
        composeTestRule.captureScreenForDevice(
            fileName = "SettingsScreenLoading",
            deviceName = "foldable",
            deviceSpec = DefaultTestDevices.FOLDABLE.spec,
        ) {
            GetoTheme {
                SettingsScreen(
                    settingsUiState = SettingsUiState.Loading,
                    supportDynamicColor = true,
                    onUpdateThemeBrand = {},
                    onUpdateDarkThemeConfig = {},
                    onCleanAppSettings = {},
                    onChangeDynamicColorPreference = {},
                    onChangeAutoLaunchPreference = {},
                )
            }
        }
    }

    @Test
    fun settingsScreen_populated_dark() {
        composeTestRule.captureScreenForDevice(
            fileName = "SettingsScreenPopulated",
            deviceName = "foldable_dark",
            deviceSpec = DefaultTestDevices.FOLDABLE.spec,
            darkMode = true,
        ) {
            GetoTheme {
                Surface {
                    SettingsScreen(
                        settingsUiState = SettingsUiState.Success(
                            userData = UserData(
                                themeBrand = ThemeBrand.PURPLE,
                                useDynamicColor = false,
                                darkThemeConfig = DarkThemeConfig.DARK,
                                useAutoLaunch = false,
                            ),
                        ),
                        supportDynamicColor = true,
                        onUpdateThemeBrand = {},
                        onUpdateDarkThemeConfig = {},
                        onCleanAppSettings = {},
                        onChangeDynamicColorPreference = {},
                        onChangeAutoLaunchPreference = {},
                    )
                }
            }
        }
    }

    @Test
    fun settingsScreen_loading_dark() {
        composeTestRule.captureScreenForDevice(
            fileName = "SettingsScreenLoading",
            deviceName = "foldable_dark",
            deviceSpec = DefaultTestDevices.FOLDABLE.spec,
            darkMode = true,
        ) {
            GetoTheme {
                Surface {
                    SettingsScreen(
                        settingsUiState = SettingsUiState.Loading,
                        supportDynamicColor = true,
                        onUpdateThemeBrand = {},
                        onUpdateDarkThemeConfig = {},
                        onCleanAppSettings = {},
                        onChangeDynamicColorPreference = {},
                        onChangeAutoLaunchPreference = {},
                    )
                }
            }
        }
    }
}
