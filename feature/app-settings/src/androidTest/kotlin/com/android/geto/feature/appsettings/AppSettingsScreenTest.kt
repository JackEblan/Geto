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
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.SettingType
import org.junit.Rule
import org.junit.Test

class AppSettingsScreenTest {
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
    fun getoLoadingWheel_isDisplayed_whenAppSettingsUiState_isLoading() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Loading,
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = null,
                revertAppSettingsResult = null,
                autoLaunchResult = null,
                requestPinShortcutResult = null,
                setPrimaryClipResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetRequestPinShortcutResult = {},
                onResetSetPrimaryClipResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onLaunchIntent = {},
            )
        }

        composeTestRule.onNodeWithContentDescription("GetoLoadingWheel").assertIsDisplayed()
    }

    @Test
    fun emptyListPlaceHolderScreen_isDisplayed_whenAppSettings_isEmpty() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = null,
                revertAppSettingsResult = null,
                autoLaunchResult = null,
                requestPinShortcutResult = null,
                setPrimaryClipResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetRequestPinShortcutResult = {},
                onResetSetPrimaryClipResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onLaunchIntent = {},
            )
        }

        composeTestRule.onNodeWithTag("appSettings:emptyListPlaceHolderScreen").assertIsDisplayed()
    }

    @Test
    fun lazyColumn_isDisplayed_whenAppSettingsUiState_isSuccess() {
        composeTestRule.setContent {
            AppSettingsScreen(
                packageName = "com.android.geto",
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(appSettings),
                snackbarHostState = SnackbarHostState(),
                applicationIcon = null,
                secureSettings = emptyList(),
                permissionCommandText = "",
                applyAppSettingsResult = null,
                revertAppSettingsResult = null,
                autoLaunchResult = null,
                requestPinShortcutResult = null,
                setPrimaryClipResult = false,
                onNavigationIconClick = {},
                onRevertAppSettings = {},
                onCheckAppSetting = { _, _ -> },
                onDeleteAppSetting = {},
                onLaunchApp = {},
                onResetApplyAppSettingsResult = {},
                onResetRevertAppSettingsResult = {},
                onResetAutoLaunchResult = {},
                onResetRequestPinShortcutResult = {},
                onResetSetPrimaryClipResult = {},
                onGetSecureSettingsByName = { _, _ -> },
                onAddAppSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onLaunchIntent = {},
            )
        }

        composeTestRule.onNodeWithTag("appSettings:lazyColumn").assertIsDisplayed()
    }
}
