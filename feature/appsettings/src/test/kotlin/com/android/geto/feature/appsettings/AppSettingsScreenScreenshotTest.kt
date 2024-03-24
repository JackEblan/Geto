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

package com.android.geto.feature.appsettings

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.core.designsystem.component.GetoBackground
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.SettingType
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
class AppSettingsScreenScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val installedApplications = List(5) { index ->
        AppSetting(
            id = index,
            enabled = true,
            settingType = SettingType.SYSTEM,
            packageName = "com.android.geto",
            label = "Geto",
            key = "Geto",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )
    }

    @Test
    fun appSettingsScreen_populated() {
        composeTestRule.captureMultiDevice("AppSettingsScreenPopulated") {
            GetoTheme {
                AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                                  appName = "Geto",
                                  appSettingsUiState = AppSettingsUiState.Success(
                                      installedApplications
                                  ),
                                  onNavigationIconClick = {},
                                  onRevertSettingsIconClick = {},
                                  onSettingsIconClick = {},
                                  onShortcutIconClick = {},
                                  onAppSettingsItemCheckBoxChange = { _, _ -> },
                                  onDeleteAppSettingsItem = {},
                                  onLaunchApp = {})
            }
        }
    }

    @Test
    fun appSettingsScreen_loading() {
        composeTestRule.captureMultiDevice("AppSettingsScreenLoading") {
            GetoTheme {
                AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                                  appName = "Geto",
                                  appSettingsUiState = AppSettingsUiState.Loading,
                                  onNavigationIconClick = {},
                                  onRevertSettingsIconClick = {},
                                  onSettingsIconClick = {},
                                  onShortcutIconClick = {},
                                  onAppSettingsItemCheckBoxChange = { _, _ -> },
                                  onDeleteAppSettingsItem = {},
                                  onLaunchApp = {})
            }
        }
    }

    @Test
    fun appSettingsScreen_empty() {
        composeTestRule.captureMultiDevice("AppSettingsScreenEmpty") {
            GetoTheme {
                AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                                  appName = "Geto",
                                  appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                                  onNavigationIconClick = {},
                                  onRevertSettingsIconClick = {},
                                  onSettingsIconClick = {},
                                  onShortcutIconClick = {},
                                  onAppSettingsItemCheckBoxChange = { _, _ -> },
                                  onDeleteAppSettingsItem = {},
                                  onLaunchApp = {})
            }
        }
    }

    @Test
    fun appSettingsScreen_populated_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "AppSettingsScreenPopulated",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                                      appName = "Geto",
                                      appSettingsUiState = AppSettingsUiState.Success(
                                          installedApplications
                                      ),
                                      onNavigationIconClick = {},
                                      onRevertSettingsIconClick = {},
                                      onSettingsIconClick = {},
                                      onShortcutIconClick = {},
                                      onAppSettingsItemCheckBoxChange = { _, _ -> },
                                      onDeleteAppSettingsItem = {},
                                      onLaunchApp = {})
                }
            }
        }
    }

    @Test
    fun appSettingsScreen_loading_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "AppSettingsScreenLoading",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                                      appName = "Geto",
                                      appSettingsUiState = AppSettingsUiState.Loading,
                                      onNavigationIconClick = {},
                                      onRevertSettingsIconClick = {},
                                      onSettingsIconClick = {},
                                      onShortcutIconClick = {},
                                      onAppSettingsItemCheckBoxChange = { _, _ -> },
                                      onDeleteAppSettingsItem = {},
                                      onLaunchApp = {})
                }
            }
        }
    }

    @Test
    fun appSettingsScreen_empty_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "AppSettingsScreenEmpty",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                                      appName = "Geto",
                                      appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                                      onNavigationIconClick = {},
                                      onRevertSettingsIconClick = {},
                                      onSettingsIconClick = {},
                                      onShortcutIconClick = {},
                                      onAppSettingsItemCheckBoxChange = { _, _ -> },
                                      onDeleteAppSettingsItem = {},
                                      onLaunchApp = {})
                }
            }
        }
    }
}