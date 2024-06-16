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
import com.android.geto.core.screenshottesting.util.DefaultTestDevices
import com.android.geto.core.screenshottesting.util.captureScreenForDevice
import com.android.geto.core.screenshottesting.util.captureScreenForMultiDevice
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

    private val appSettings = List(5) { index ->
        AppSetting(
            id = index,
            enabled = true,
            settingType = SettingType.SYSTEM,
            packageName = "com.android.geto",
            label = "Geto",
            key = "Geto $index",
            valueOnLaunch = "0",
            valueOnRevert = "1",
        )
    }

    @Test
    fun appSettingsScreen_populated() {
        composeTestRule.captureScreenForMultiDevice("AppSettingsScreenPopulated") {
            GetoTheme {
                AppSettingsScreen(
                    packageName = "com.android.geto",
                    appName = "Geto",
                    appSettingsUiState = AppSettingsUiState.Success(appSettings),
                    snackbarHostState = SnackbarHostState(),
                    applicationIcon = null,
                    mappedShortcutInfoCompat = null,
                    secureSettings = emptyList(),
                    permissionCommandText = "",
                    applyAppSettingsResult = null,
                    revertAppSettingsResult = null,
                    autoLaunchResult = null,
                    requestPinShortcutResult = null,
                    updateRequestPinShortcutResult = null,
                    setPrimaryClipResult = false,
                    onNavigationIconClick = {},
                    onRevertAppSettings = {},
                    onGetShortcut = {},
                    onCheckAppSetting = { _, _ -> },
                    onDeleteAppSetting = {},
                    onLaunchApp = {},
                    onUpdatePackageName = {},
                    onAutoLaunchApp = {},
                    onGetApplicationIcon = {},
                    onResetApplyAppSettingsResult = {},
                    onResetRevertAppSettingsResult = {},
                    onResetAutoLaunchResult = {},
                    onResetShortcutResult = {},
                    onResetSetPrimaryClipResult = {},
                    onGetSecureSettingsByName = { _, _ -> },
                    onAddAppSetting = {},
                    onCopyPermissionCommand = {},
                    onAddShortcut = {},
                    onUpdateShortcut = {},
                )
            }
        }
    }

    @Test
    fun appSettingsScreen_loading() {
        composeTestRule.captureScreenForMultiDevice("AppSettingsScreenLoading") {
            GetoTheme {
                AppSettingsScreen(
                    packageName = "com.android.geto",
                    appName = "Geto",
                    appSettingsUiState = AppSettingsUiState.Loading,
                    snackbarHostState = SnackbarHostState(),
                    applicationIcon = null,
                    mappedShortcutInfoCompat = null,
                    secureSettings = emptyList(),
                    permissionCommandText = "",
                    applyAppSettingsResult = null,
                    revertAppSettingsResult = null,
                    autoLaunchResult = null,
                    requestPinShortcutResult = null,
                    updateRequestPinShortcutResult = null,
                    setPrimaryClipResult = false,
                    onNavigationIconClick = {},
                    onRevertAppSettings = {},
                    onGetShortcut = {},
                    onCheckAppSetting = { _, _ -> },
                    onDeleteAppSetting = {},
                    onLaunchApp = {},
                    onUpdatePackageName = {},
                    onAutoLaunchApp = {},
                    onGetApplicationIcon = {},
                    onResetApplyAppSettingsResult = {},
                    onResetRevertAppSettingsResult = {},
                    onResetAutoLaunchResult = {},
                    onResetShortcutResult = {},
                    onResetSetPrimaryClipResult = {},
                    onGetSecureSettingsByName = { _, _ -> },
                    onAddAppSetting = {},
                    onCopyPermissionCommand = {},
                    onAddShortcut = {},
                    onUpdateShortcut = {},
                )
            }
        }
    }

    @Test
    fun appSettingsScreen_empty() {
        composeTestRule.captureScreenForMultiDevice("AppSettingsScreenEmpty") {
            GetoTheme {
                AppSettingsScreen(
                    packageName = "com.android.geto",
                    appName = "Geto",
                    appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                    snackbarHostState = SnackbarHostState(),
                    applicationIcon = null,
                    mappedShortcutInfoCompat = null,
                    secureSettings = emptyList(),
                    permissionCommandText = "",
                    applyAppSettingsResult = null,
                    revertAppSettingsResult = null,
                    autoLaunchResult = null,
                    requestPinShortcutResult = null,
                    updateRequestPinShortcutResult = null,
                    setPrimaryClipResult = false,
                    onNavigationIconClick = {},
                    onRevertAppSettings = {},
                    onGetShortcut = {},
                    onCheckAppSetting = { _, _ -> },
                    onDeleteAppSetting = {},
                    onLaunchApp = {},
                    onUpdatePackageName = {},
                    onAutoLaunchApp = {},
                    onGetApplicationIcon = {},
                    onResetApplyAppSettingsResult = {},
                    onResetRevertAppSettingsResult = {},
                    onResetAutoLaunchResult = {},
                    onResetShortcutResult = {},
                    onResetSetPrimaryClipResult = {},
                    onGetSecureSettingsByName = { _, _ -> },
                    onAddAppSetting = {},
                    onCopyPermissionCommand = {},
                    onAddShortcut = {},
                    onUpdateShortcut = {},
                )
            }
        }
    }

    @Test
    fun appSettingsScreen_populated_dark() {
        composeTestRule.captureScreenForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            fileName = "AppSettingsScreenPopulated",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AppSettingsScreen(
                        packageName = "com.android.geto",
                        appName = "Geto",
                        appSettingsUiState = AppSettingsUiState.Success(appSettings),
                        snackbarHostState = SnackbarHostState(),
                        applicationIcon = null,
                        mappedShortcutInfoCompat = null,
                        secureSettings = emptyList(),
                        permissionCommandText = "",
                        applyAppSettingsResult = null,
                        revertAppSettingsResult = null,
                        autoLaunchResult = null,
                        requestPinShortcutResult = null,
                        updateRequestPinShortcutResult = null,
                        setPrimaryClipResult = false,
                        onNavigationIconClick = {},
                        onRevertAppSettings = {},
                        onGetShortcut = {},
                        onCheckAppSetting = { _, _ -> },
                        onDeleteAppSetting = {},
                        onLaunchApp = {},
                        onUpdatePackageName = {},
                        onAutoLaunchApp = {},
                        onGetApplicationIcon = {},
                        onResetApplyAppSettingsResult = {},
                        onResetRevertAppSettingsResult = {},
                        onResetAutoLaunchResult = {},
                        onResetShortcutResult = {},
                        onResetSetPrimaryClipResult = {},
                        onGetSecureSettingsByName = { _, _ -> },
                        onAddAppSetting = {},
                        onCopyPermissionCommand = {},
                        onAddShortcut = {},
                        onUpdateShortcut = {},
                    )
                }
            }
        }
    }

    @Test
    fun appSettingsScreen_loading_dark() {
        composeTestRule.captureScreenForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            fileName = "AppSettingsScreenLoading",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AppSettingsScreen(
                        packageName = "com.android.geto",
                        appName = "Geto",
                        appSettingsUiState = AppSettingsUiState.Loading,
                        snackbarHostState = SnackbarHostState(),
                        applicationIcon = null,
                        mappedShortcutInfoCompat = null,
                        secureSettings = emptyList(),
                        permissionCommandText = "",
                        applyAppSettingsResult = null,
                        revertAppSettingsResult = null,
                        autoLaunchResult = null,
                        requestPinShortcutResult = null,
                        updateRequestPinShortcutResult = null,
                        setPrimaryClipResult = false,
                        onNavigationIconClick = {},
                        onRevertAppSettings = {},
                        onGetShortcut = {},
                        onCheckAppSetting = { _, _ -> },
                        onDeleteAppSetting = {},
                        onLaunchApp = {},
                        onUpdatePackageName = {},
                        onAutoLaunchApp = {},
                        onGetApplicationIcon = {},
                        onResetApplyAppSettingsResult = {},
                        onResetRevertAppSettingsResult = {},
                        onResetAutoLaunchResult = {},
                        onResetShortcutResult = {},
                        onResetSetPrimaryClipResult = {},
                        onGetSecureSettingsByName = { _, _ -> },
                        onAddAppSetting = {},
                        onCopyPermissionCommand = {},
                        onAddShortcut = {},
                        onUpdateShortcut = {},
                    )
                }
            }
        }
    }

    @Test
    fun appSettingsScreen_empty_dark() {
        composeTestRule.captureScreenForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            fileName = "AppSettingsScreenEmpty",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AppSettingsScreen(
                        packageName = "com.android.geto",
                        appName = "Geto",
                        appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                        snackbarHostState = SnackbarHostState(),
                        applicationIcon = null,
                        mappedShortcutInfoCompat = null,
                        secureSettings = emptyList(),
                        permissionCommandText = "",
                        applyAppSettingsResult = null,
                        revertAppSettingsResult = null,
                        autoLaunchResult = null,
                        requestPinShortcutResult = null,
                        updateRequestPinShortcutResult = null,
                        setPrimaryClipResult = false,
                        onNavigationIconClick = {},
                        onRevertAppSettings = {},
                        onGetShortcut = {},
                        onCheckAppSetting = { _, _ -> },
                        onDeleteAppSetting = {},
                        onLaunchApp = {},
                        onUpdatePackageName = {},
                        onAutoLaunchApp = {},
                        onGetApplicationIcon = {},
                        onResetApplyAppSettingsResult = {},
                        onResetRevertAppSettingsResult = {},
                        onResetAutoLaunchResult = {},
                        onResetShortcutResult = {},
                        onResetSetPrimaryClipResult = {},
                        onGetSecureSettingsByName = { _, _ -> },
                        onAddAppSetting = {},
                        onCopyPermissionCommand = {},
                        onAddShortcut = {},
                        onUpdateShortcut = {},
                    )
                }
            }
        }
    }
}
