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

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.core.designsystem.component.GetoBackground
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SettingsType
import com.android.geto.core.screenshot.testing.util.DefaultTestDevices
import com.android.geto.core.screenshot.testing.util.captureForDevice
import com.android.geto.core.screenshot.testing.util.captureMultiDevice
import com.android.geto.feature.appsettings.dialog.appsettings.rememberAddAppSettingsDialogState
import com.android.geto.feature.appsettings.dialog.shortcut.rememberAddShortcutDialogState
import com.android.geto.feature.appsettings.dialog.shortcut.rememberUpdateShortcutDialogState
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

    @Test
    fun app_settings_populated() {
        composeTestRule.captureMultiDevice("AppSettingsPopulated") {
            GetoTheme {
                val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

                val addShortcutDialogState = rememberAddShortcutDialogState()

                val updateShortcutDialogState = rememberUpdateShortcutDialogState()

                val scrollState = rememberScrollState()

                AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                                  appName = "Geto", packageName = "Geto", shortcutIntent = Intent(),
                                  appSettingsUiState = AppSettingsUiState.Success(
                                      testAppSettingsList
                                  ),
                                  addAppSettingsDialogState = addAppSettingsDialogState,
                                  addShortcutDialogState = addShortcutDialogState,
                                  updateShortcutDialogState = updateShortcutDialogState,
                                  showCopyPermissionCommandDialog = false,
                                  onNavigationIconClick = {},
                                  onRevertSettingsIconClick = {},
                                  onSettingsIconClick = {},
                                  onShortcutIconClick = {},
                                  onAppSettingsItemCheckBoxChange = { _, _ -> },
                                  onDeleteAppSettingsItem = {},
                                  onLaunchApp = {},
                                  scrollState = scrollState,
                                  onAddSettings = {},
                                  onAddShortcut = {},
                                  onUpdateShortcut = {},
                                  onCopyPermissionCommand = {},
                                  onRefreshShortcut = {},
                                  onDismissRequestCopyPermissionCommand = {})
            }
        }
    }

    @Test
    fun app_settings_loading() {
        composeTestRule.captureMultiDevice("AppSettingsLoading") {
            GetoTheme {
                val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

                val addShortcutDialogState = rememberAddShortcutDialogState()

                val updateShortcutDialogState = rememberUpdateShortcutDialogState()

                val scrollState = rememberScrollState()

                AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                                  appName = "Geto", packageName = "Geto", shortcutIntent = Intent(),
                                  appSettingsUiState = AppSettingsUiState.Loading,
                                  addAppSettingsDialogState = addAppSettingsDialogState,
                                  addShortcutDialogState = addShortcutDialogState,
                                  updateShortcutDialogState = updateShortcutDialogState,
                                  showCopyPermissionCommandDialog = false,
                                  onNavigationIconClick = {},
                                  onRevertSettingsIconClick = {},
                                  onSettingsIconClick = {},
                                  onShortcutIconClick = {},
                                  onAppSettingsItemCheckBoxChange = { _, _ -> },
                                  onDeleteAppSettingsItem = {},
                                  onLaunchApp = {},
                                  scrollState = scrollState,
                                  onAddSettings = {},
                                  onAddShortcut = {},
                                  onUpdateShortcut = {},
                                  onCopyPermissionCommand = {},
                                  onRefreshShortcut = {},
                                  onDismissRequestCopyPermissionCommand = {})
            }
        }
    }

    @Test
    fun app_settings_empty() {
        composeTestRule.captureMultiDevice("AppSettingsEmpty") {
            GetoTheme {
                val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

                val addShortcutDialogState = rememberAddShortcutDialogState()

                val updateShortcutDialogState = rememberUpdateShortcutDialogState()

                val scrollState = rememberScrollState()

                AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                                  appName = "Geto", packageName = "Geto", shortcutIntent = Intent(),
                                  appSettingsUiState = AppSettingsUiState.Empty,
                                  addAppSettingsDialogState = addAppSettingsDialogState,
                                  addShortcutDialogState = addShortcutDialogState,
                                  updateShortcutDialogState = updateShortcutDialogState,
                                  showCopyPermissionCommandDialog = false,
                                  onNavigationIconClick = {},
                                  onRevertSettingsIconClick = {},
                                  onSettingsIconClick = {},
                                  onShortcutIconClick = {},
                                  onAppSettingsItemCheckBoxChange = { _, _ -> },
                                  onDeleteAppSettingsItem = {},
                                  onLaunchApp = {},
                                  scrollState = scrollState,
                                  onAddSettings = {},
                                  onAddShortcut = {},
                                  onUpdateShortcut = {},
                                  onCopyPermissionCommand = {},
                                  onRefreshShortcut = {},
                                  onDismissRequestCopyPermissionCommand = {})
            }
        }
    }

    @Test
    fun app_settings_populated_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "AppSettingsPopulated",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

                    val addShortcutDialogState = rememberAddShortcutDialogState()

                    val updateShortcutDialogState = rememberUpdateShortcutDialogState()

                    val scrollState = rememberScrollState()

                    AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                                      appName = "Geto",
                                      packageName = "Geto",
                                      shortcutIntent = Intent(),
                                      appSettingsUiState = AppSettingsUiState.Success(
                                          testAppSettingsList
                                      ),
                                      addAppSettingsDialogState = addAppSettingsDialogState,
                                      addShortcutDialogState = addShortcutDialogState,
                                      updateShortcutDialogState = updateShortcutDialogState,
                                      showCopyPermissionCommandDialog = false,
                                      onNavigationIconClick = {},
                                      onRevertSettingsIconClick = {},
                                      onSettingsIconClick = {},
                                      onShortcutIconClick = {},
                                      onAppSettingsItemCheckBoxChange = { _, _ -> },
                                      onDeleteAppSettingsItem = {},
                                      onLaunchApp = {},
                                      scrollState = scrollState,
                                      onAddSettings = {},
                                      onAddShortcut = {},
                                      onUpdateShortcut = {},
                                      onCopyPermissionCommand = {},
                                      onRefreshShortcut = {},
                                      onDismissRequestCopyPermissionCommand = {})
                }
            }
        }
    }

    @Test
    fun app_settings_loading_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "AppSettingsLoading",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

                    val addShortcutDialogState = rememberAddShortcutDialogState()

                    val updateShortcutDialogState = rememberUpdateShortcutDialogState()

                    val scrollState = rememberScrollState()

                    AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                                      appName = "Geto",
                                      packageName = "Geto",
                                      shortcutIntent = Intent(),
                                      appSettingsUiState = AppSettingsUiState.Loading,
                                      addAppSettingsDialogState = addAppSettingsDialogState,
                                      addShortcutDialogState = addShortcutDialogState,
                                      updateShortcutDialogState = updateShortcutDialogState,
                                      showCopyPermissionCommandDialog = false,
                                      onNavigationIconClick = {},
                                      onRevertSettingsIconClick = {},
                                      onSettingsIconClick = {},
                                      onShortcutIconClick = {},
                                      onAppSettingsItemCheckBoxChange = { _, _ -> },
                                      onDeleteAppSettingsItem = {},
                                      onLaunchApp = {},
                                      scrollState = scrollState,
                                      onAddSettings = {},
                                      onAddShortcut = {},
                                      onUpdateShortcut = {},
                                      onCopyPermissionCommand = {},
                                      onRefreshShortcut = {},
                                      onDismissRequestCopyPermissionCommand = {})
                }
            }
        }
    }

    @Test
    fun app_settings_empty_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "AppSettingsEmpty",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

                    val addShortcutDialogState = rememberAddShortcutDialogState()

                    val updateShortcutDialogState = rememberUpdateShortcutDialogState()

                    val scrollState = rememberScrollState()

                    AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                                      appName = "Geto",
                                      packageName = "Geto",
                                      shortcutIntent = Intent(),
                                      appSettingsUiState = AppSettingsUiState.Empty,
                                      addAppSettingsDialogState = addAppSettingsDialogState,
                                      addShortcutDialogState = addShortcutDialogState,
                                      updateShortcutDialogState = updateShortcutDialogState,
                                      showCopyPermissionCommandDialog = false,
                                      onNavigationIconClick = {},
                                      onRevertSettingsIconClick = {},
                                      onSettingsIconClick = {},
                                      onShortcutIconClick = {},
                                      onAppSettingsItemCheckBoxChange = { _, _ -> },
                                      onDeleteAppSettingsItem = {},
                                      onLaunchApp = {},
                                      scrollState = scrollState,
                                      onAddSettings = {},
                                      onAddShortcut = {},
                                      onUpdateShortcut = {},
                                      onCopyPermissionCommand = {},
                                      onRefreshShortcut = {},
                                      onDismissRequestCopyPermissionCommand = {})
                }
            }
        }
    }

    private val testAppSettingsList = listOf(
        AppSettings(
            id = 0,
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "packageName0",
            label = "label0",
            key = "key0",
            valueOnLaunch = "valueOnLaunch0",
            valueOnRevert = "valueOnRevert0"
        ), AppSettings(
            id = 1,
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "packageName1",
            label = "label1",
            key = "key1",
            valueOnLaunch = "valueOnLaunch1",
            valueOnRevert = "valueOnRevert1"
        )
    )
}