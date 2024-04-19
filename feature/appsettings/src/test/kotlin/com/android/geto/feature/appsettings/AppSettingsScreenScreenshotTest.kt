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
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.core.data.repository.ClipboardResult
import com.android.geto.core.data.repository.ShortcutResult
import com.android.geto.core.designsystem.component.GetoBackground
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.domain.AppSettingsResult
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.SettingType
import com.android.geto.core.screenshot.testing.util.DefaultTestDevices
import com.android.geto.core.screenshot.testing.util.captureForDevice
import com.android.geto.core.screenshot.testing.util.captureMultiDevice
import com.android.geto.feature.appsettings.dialog.appsetting.rememberAppSettingDialogState
import com.android.geto.feature.appsettings.dialog.copypermissioncommand.rememberCopyPermissionCommandDialogState
import com.android.geto.feature.appsettings.dialog.shortcut.rememberShortcutDialogState
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
        composeTestRule.captureMultiDevice("AppSettingsScreenPopulated") {
            GetoTheme {
                AppSettingsScreen(
                    packageName = "com.android.geto",
                    appName = "Geto",
                    appSettingsUiState = AppSettingsUiState.Success(appSettings),
                    applicationIcon = null,
                    secureSettings = emptyList(),
                    applyAppSettingsResult = AppSettingsResult.NoResult,
                    revertAppSettingsResult = AppSettingsResult.NoResult,
                    shortcutResult = ShortcutResult.NoResult,
                    clipboardResult = ClipboardResult.NoResult,
                    copyPermissionCommandDialogState = rememberCopyPermissionCommandDialogState(),
                    appSettingDialogState = rememberAppSettingDialogState(),
                    addShortcutDialogState = rememberShortcutDialogState(),
                    updateShortcutDialogState = rememberShortcutDialogState(),
                    onNavigationIconClick = {},
                    onRevertAppSettings = {},
                    onGetShortcut = {},
                    onGetApplicationIcon = {},
                    onCheckAppSetting = { _, _ -> },
                    onDeleteAppSetting = {},
                    onLaunchApp = {},
                    onAutoLaunchApp = {},
                    onResetAppSettingsResult = {},
                    onResetShortcutResult = {},
                    onResetClipboardResult = {},
                    onGetSecureSettings = { _, _ -> },
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
        composeTestRule.captureMultiDevice("AppSettingsScreenLoading") {
            GetoTheme {
                AppSettingsScreen(
                    packageName = "com.android.geto",
                    appName = "Geto",
                    appSettingsUiState = AppSettingsUiState.Loading,
                    applicationIcon = null,
                    secureSettings = emptyList(),
                    applyAppSettingsResult = AppSettingsResult.NoResult,
                    revertAppSettingsResult = AppSettingsResult.NoResult,
                    shortcutResult = ShortcutResult.NoResult,
                    clipboardResult = ClipboardResult.NoResult,
                    copyPermissionCommandDialogState = rememberCopyPermissionCommandDialogState(),
                    appSettingDialogState = rememberAppSettingDialogState(),
                    addShortcutDialogState = rememberShortcutDialogState(),
                    updateShortcutDialogState = rememberShortcutDialogState(),
                    onNavigationIconClick = {},
                    onRevertAppSettings = {},
                    onGetShortcut = {},
                    onGetApplicationIcon = {},
                    onCheckAppSetting = { _, _ -> },
                    onDeleteAppSetting = {},
                    onLaunchApp = {},
                    onAutoLaunchApp = {},
                    onResetAppSettingsResult = {},
                    onResetShortcutResult = {},
                    onResetClipboardResult = {},
                    onGetSecureSettings = { _, _ -> },
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
        composeTestRule.captureMultiDevice("AppSettingsScreenEmpty") {
            GetoTheme {
                AppSettingsScreen(
                    packageName = "com.android.geto",
                    appName = "Geto",
                    appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                    applicationIcon = null,
                    secureSettings = emptyList(),
                    applyAppSettingsResult = AppSettingsResult.NoResult,
                    revertAppSettingsResult = AppSettingsResult.NoResult,
                    shortcutResult = ShortcutResult.NoResult,
                    clipboardResult = ClipboardResult.NoResult,
                    copyPermissionCommandDialogState = rememberCopyPermissionCommandDialogState(),
                    appSettingDialogState = rememberAppSettingDialogState(),
                    addShortcutDialogState = rememberShortcutDialogState(),
                    updateShortcutDialogState = rememberShortcutDialogState(),
                    onNavigationIconClick = {},
                    onRevertAppSettings = {},
                    onGetShortcut = {},
                    onGetApplicationIcon = {},
                    onCheckAppSetting = { _, _ -> },
                    onDeleteAppSetting = {},
                    onLaunchApp = {},
                    onAutoLaunchApp = {},
                    onResetAppSettingsResult = {},
                    onResetShortcutResult = {},
                    onResetClipboardResult = {},
                    onGetSecureSettings = { _, _ -> },
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
        composeTestRule.captureForDevice(
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
                        applicationIcon = null,
                        secureSettings = emptyList(),
                        applyAppSettingsResult = AppSettingsResult.NoResult,
                        revertAppSettingsResult = AppSettingsResult.NoResult,
                        shortcutResult = ShortcutResult.NoResult,
                        clipboardResult = ClipboardResult.NoResult,
                        copyPermissionCommandDialogState = rememberCopyPermissionCommandDialogState(),
                        appSettingDialogState = rememberAppSettingDialogState(),
                        addShortcutDialogState = rememberShortcutDialogState(),
                        updateShortcutDialogState = rememberShortcutDialogState(),
                        onNavigationIconClick = {},
                        onRevertAppSettings = {},
                        onGetShortcut = {},
                        onGetApplicationIcon = {},
                        onCheckAppSetting = { _, _ -> },
                        onDeleteAppSetting = {},
                        onLaunchApp = {},
                        onAutoLaunchApp = {},
                        onResetAppSettingsResult = {},
                        onResetShortcutResult = {},
                        onResetClipboardResult = {},
                        onGetSecureSettings = { _, _ -> },
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
        composeTestRule.captureForDevice(
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
                        applicationIcon = null,
                        secureSettings = emptyList(),
                        applyAppSettingsResult = AppSettingsResult.NoResult,
                        revertAppSettingsResult = AppSettingsResult.NoResult,
                        shortcutResult = ShortcutResult.NoResult,
                        clipboardResult = ClipboardResult.NoResult,
                        copyPermissionCommandDialogState = rememberCopyPermissionCommandDialogState(),
                        appSettingDialogState = rememberAppSettingDialogState(),
                        addShortcutDialogState = rememberShortcutDialogState(),
                        updateShortcutDialogState = rememberShortcutDialogState(),
                        onNavigationIconClick = {},
                        onRevertAppSettings = {},
                        onGetShortcut = {},
                        onGetApplicationIcon = {},
                        onCheckAppSetting = { _, _ -> },
                        onDeleteAppSetting = {},
                        onLaunchApp = {},
                        onAutoLaunchApp = {},
                        onResetAppSettingsResult = {},
                        onResetShortcutResult = {},
                        onResetClipboardResult = {},
                        onGetSecureSettings = { _, _ -> },
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
        composeTestRule.captureForDevice(
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
                        applicationIcon = null,
                        secureSettings = emptyList(),
                        applyAppSettingsResult = AppSettingsResult.NoResult,
                        revertAppSettingsResult = AppSettingsResult.NoResult,
                        shortcutResult = ShortcutResult.NoResult,
                        clipboardResult = ClipboardResult.NoResult,
                        copyPermissionCommandDialogState = rememberCopyPermissionCommandDialogState(),
                        appSettingDialogState = rememberAppSettingDialogState(),
                        addShortcutDialogState = rememberShortcutDialogState(),
                        updateShortcutDialogState = rememberShortcutDialogState(),
                        onNavigationIconClick = {},
                        onRevertAppSettings = {},
                        onGetShortcut = {},
                        onGetApplicationIcon = {},
                        onCheckAppSetting = { _, _ -> },
                        onDeleteAppSetting = {},
                        onLaunchApp = {},
                        onAutoLaunchApp = {},
                        onResetAppSettingsResult = {},
                        onResetShortcutResult = {},
                        onResetClipboardResult = {},
                        onGetSecureSettings = { _, _ -> },
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
