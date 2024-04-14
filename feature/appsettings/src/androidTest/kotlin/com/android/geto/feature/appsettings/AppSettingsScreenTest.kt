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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.SettingType
import com.android.geto.feature.appsettings.dialog.appsetting.rememberAppSettingDialogState
import com.android.geto.feature.appsettings.dialog.shortcut.rememberShortcutDialogState
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
                snackbarHostState = SnackbarHostState(),
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Loading,
                onNavigationIconClick = {},
                onRevertSettingsIconClick = {},
                onSettingsIconClick = {},
                onShortcutIconClick = {},
                onAppSettingsItemCheckBoxChange = { _, _ -> },
                onDeleteAppSettingsItem = {},
                onLaunchApp = {},
            )
        }

        composeTestRule.onNodeWithContentDescription("GetoLoadingWheel").assertIsDisplayed()
    }

    @Test
    fun emptyListPlaceHolderScreen_isDisplayed_whenAppSettings_isEmpty() {
        composeTestRule.setContent {
            AppSettingsScreen(
                snackbarHostState = SnackbarHostState(),
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(emptyList()),
                onNavigationIconClick = {},
                onRevertSettingsIconClick = {},
                onSettingsIconClick = {},
                onShortcutIconClick = {},
                onAppSettingsItemCheckBoxChange = { _, _ -> },
                onDeleteAppSettingsItem = {},
                onLaunchApp = {},
            )
        }

        composeTestRule.onNodeWithTag("appSettings:emptyListPlaceHolderScreen").assertIsDisplayed()
    }

    @Test
    fun lazyColumn_isDisplayed_whenAppSettingsUiState_isSuccess() {
        composeTestRule.setContent {
            AppSettingsScreen(
                snackbarHostState = SnackbarHostState(),
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(appSettings),
                onNavigationIconClick = {},
                onRevertSettingsIconClick = {},
                onSettingsIconClick = {},
                onShortcutIconClick = {},
                onAppSettingsItemCheckBoxChange = { _, _ -> },
                onDeleteAppSettingsItem = {},
                onLaunchApp = {},
            )
        }

        composeTestRule.onNodeWithTag("appSettings:lazyColumn").assertIsDisplayed()
    }

    @Test
    fun appSettingDialog_isDisplayed_whenSettingsIcon_isClicked_thenDismissed() {
        composeTestRule.setContent {
            val appSettingDialogState = rememberAppSettingDialogState()

            AppSettingsDialogs(
                showCopyPermissionCommandDialog = false,
                appSettingDialogState = appSettingDialogState,
                addShortcutDialogState = rememberShortcutDialogState(),
                updateShortcutDialogState = rememberShortcutDialogState(),
                onAddSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onUpdateShortcut = {},
                onCopyPermissionCommandDialogDismissRequest = {},
            )

            AppSettingsScreen(
                snackbarHostState = SnackbarHostState(),
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(appSettings),
                onNavigationIconClick = {},
                onRevertSettingsIconClick = {},
                onSettingsIconClick = {
                    appSettingDialogState.updateShowDialog(true)
                },
                onShortcutIconClick = {},
                onAppSettingsItemCheckBoxChange = { _, _ -> },
                onDeleteAppSettingsItem = {},
                onLaunchApp = {},
            )
        }

        composeTestRule.onNodeWithContentDescription(
            label = "Settings icon",
            useUnmergedTree = true,
        ).performClick()

        composeTestRule.onNodeWithContentDescription("Add App Settings Dialog").assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancel").performClick()

        composeTestRule.onNodeWithContentDescription("Add App Settings Dialog")
            .assertIsNotDisplayed()
    }

    @Test
    fun copyPermissionCommandDialog_isDisplayed_thenDismissed() {
        composeTestRule.setContent {
            var showCopyPermissionCommandDialog by remember {
                mutableStateOf(true)
            }

            AppSettingsDialogs(
                showCopyPermissionCommandDialog = showCopyPermissionCommandDialog,
                appSettingDialogState = rememberAppSettingDialogState(),
                addShortcutDialogState = rememberShortcutDialogState(),
                updateShortcutDialogState = rememberShortcutDialogState(),
                onAddSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onUpdateShortcut = {},
                onCopyPermissionCommandDialogDismissRequest = {
                    showCopyPermissionCommandDialog = false
                },
            )

            AppSettingsScreen(
                snackbarHostState = SnackbarHostState(),
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(appSettings),
                onNavigationIconClick = {},
                onRevertSettingsIconClick = {},
                onSettingsIconClick = {},
                onShortcutIconClick = {},
                onAppSettingsItemCheckBoxChange = { _, _ -> },
                onDeleteAppSettingsItem = {},
                onLaunchApp = {},
            )
        }

        composeTestRule.onNodeWithContentDescription("Copy Permission Command Dialog")
            .assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancel").performClick()

        composeTestRule.onNodeWithContentDescription("Copy Permission Command Dialog")
            .assertIsNotDisplayed()
    }

    @Test
    fun addShortcutDialog_isDisplayed_whenSettingsIcon_isClicked_thenDismissed() {
        composeTestRule.setContent {
            val shortcutDialogState = rememberShortcutDialogState()

            AppSettingsDialogs(
                showCopyPermissionCommandDialog = false,
                appSettingDialogState = rememberAppSettingDialogState(),
                addShortcutDialogState = shortcutDialogState,
                updateShortcutDialogState = rememberShortcutDialogState(),
                onAddSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onUpdateShortcut = {},
                onCopyPermissionCommandDialogDismissRequest = {},
            )

            AppSettingsScreen(
                snackbarHostState = SnackbarHostState(),
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(appSettings),
                onNavigationIconClick = {},
                onRevertSettingsIconClick = {},
                onSettingsIconClick = {},
                onShortcutIconClick = {
                    shortcutDialogState.updateShowDialog(true)
                },
                onAppSettingsItemCheckBoxChange = { _, _ -> },
                onDeleteAppSettingsItem = {},
                onLaunchApp = {},
            )
        }

        composeTestRule.onNodeWithContentDescription(
            label = "Shortcut icon",
            useUnmergedTree = true,
        ).performClick()

        composeTestRule.onNodeWithContentDescription("Add Shortcut Dialog").assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancel").performClick()

        composeTestRule.onNodeWithContentDescription("Add Shortcut Dialog").assertIsNotDisplayed()
    }

    @Test
    fun updateShortcutDialog_isDisplayed_whenShortcutIcon_isClicked_thenDismissed() {
        composeTestRule.setContent {
            val shortcutDialogState = rememberShortcutDialogState()

            AppSettingsDialogs(
                showCopyPermissionCommandDialog = false,
                appSettingDialogState = rememberAppSettingDialogState(),
                addShortcutDialogState = rememberShortcutDialogState(),
                updateShortcutDialogState = shortcutDialogState,
                onAddSetting = {},
                onCopyPermissionCommand = {},
                onAddShortcut = {},
                onUpdateShortcut = {},
                onCopyPermissionCommandDialogDismissRequest = {},
            )

            AppSettingsScreen(
                snackbarHostState = SnackbarHostState(),
                appName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(appSettings),
                onNavigationIconClick = {},
                onRevertSettingsIconClick = {},
                onSettingsIconClick = {},
                onShortcutIconClick = {
                    shortcutDialogState.updateShowDialog(true)
                },
                onAppSettingsItemCheckBoxChange = { _, _ -> },
                onDeleteAppSettingsItem = {},
                onLaunchApp = {},
            )
        }

        composeTestRule.onNodeWithContentDescription(
            label = "Shortcut icon",
            useUnmergedTree = true,
        ).performClick()

        composeTestRule.onNodeWithContentDescription("Update Shortcut Dialog").assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancel").performClick()

        composeTestRule.onNodeWithContentDescription("Update Shortcut Dialog")
            .assertIsNotDisplayed()
    }
}
