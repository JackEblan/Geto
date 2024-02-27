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
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SettingsType
import com.android.geto.core.ui.rememberAddSettingsDialogState
import com.android.geto.core.ui.rememberAddShortcutDialogState
import org.junit.Rule
import org.junit.Test

class AppSettingsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loadingPlaceHolderScreenIsDisplayed_whenAppSettingsUiStateIsLoading() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              intent = Intent(),
                              appSettingsUiState = AppSettingsUiState.Loading,
                              addSettingsDialogState = addSettingsDialogState,
                              addShortcutDialogState = addShortcutDialogState,
                              showCopyPermissionCommandDialog = false,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {},
                              scrollState = scrollState,
                              onAddSettings = {},
                              onAddShortcut = {},
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithTag("appsettings:loadingPlaceHolderScreen").assertIsDisplayed()
    }

    @Test
    fun emptyListPlaceHolderScreenIsDisplayed_whenAppSettingsUiStateIsEmpty() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              intent = Intent(),
                              appSettingsUiState = AppSettingsUiState.Empty,
                              addSettingsDialogState = addSettingsDialogState,
                              addShortcutDialogState = addShortcutDialogState,
                              showCopyPermissionCommandDialog = false,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {},
                              scrollState = scrollState,
                              onAddSettings = {},
                              onAddShortcut = {},
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithTag("appsettings:emptyListPlaceHolderScreen").assertIsDisplayed()
    }

    @Test
    fun lazyColumnIsDisplayed_whenAppSettingsUiStateIsSuccess() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              intent = Intent(),
                              appSettingsUiState = AppSettingsUiState.Success(testAppSettingsList),
                              addSettingsDialogState = addSettingsDialogState,
                              addShortcutDialogState = addShortcutDialogState,
                              showCopyPermissionCommandDialog = false,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {},
                              scrollState = scrollState,
                              onAddSettings = {},
                              onAddShortcut = {},
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithTag("appsettings:lazyColumn").assertIsDisplayed()
    }

    @Test
    fun snackbarExists_whenRevertIconIsClicked_appSettingsUiStateIsEmpty() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              intent = Intent(),
                              appSettingsUiState = AppSettingsUiState.Success(testAppSettingsList),
                              addSettingsDialogState = addSettingsDialogState,
                              addShortcutDialogState = addShortcutDialogState,
                              showCopyPermissionCommandDialog = false,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {},
                              scrollState = scrollState,
                              onAddSettings = {},
                              onAddShortcut = {},
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithContentDescription("Revert icon").performClick()

        composeTestRule.onNodeWithTag("appsettings:snackbar").assertExists()
    }

    @Test
    fun snackbarExists_whenLaunchIconIsClicked_appSettingsUiStateIsEmpty() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              intent = Intent(),
                              appSettingsUiState = AppSettingsUiState.Empty,
                              addSettingsDialogState = addSettingsDialogState,
                              addShortcutDialogState = addShortcutDialogState,
                              showCopyPermissionCommandDialog = false,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {},
                              scrollState = scrollState,
                              onAddSettings = {},
                              onAddShortcut = {},
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithContentDescription("Launch icon").performClick()

        composeTestRule.onNodeWithTag("appsettings:snackbar").assertExists()
    }

    @Test
    fun addSettingsDialogIsDisplayed_whenAddIconIsClicked() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              intent = Intent(),
                              appSettingsUiState = AppSettingsUiState.Empty,
                              addSettingsDialogState = addSettingsDialogState,
                              addShortcutDialogState = addShortcutDialogState,
                              showCopyPermissionCommandDialog = false,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {},
                              scrollState = scrollState,
                              onAddSettings = {
                                  addSettingsDialogState.updateShowDialog(true)
                              },
                              onAddShortcut = {},
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithContentDescription("Add icon").performClick()

        composeTestRule.onNodeWithTag("addSettingsDialog").assertIsDisplayed()
    }

    @Test
    fun copyPermissionCommandDialogIsDisplayed_whenAddShortcutIsClicked() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              intent = Intent(),
                              appSettingsUiState = AppSettingsUiState.Empty,
                              addSettingsDialogState = addSettingsDialogState,
                              addShortcutDialogState = addShortcutDialogState,
                              showCopyPermissionCommandDialog = true,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {},
                              scrollState = scrollState,
                              onAddSettings = {},
                              onAddShortcut = {},
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithTag("copyPermissionCommandDialog").assertIsDisplayed()
    }

    @Test
    fun addShortcutDialogIsDisplayed_whenShowCopyPermissionCommandDialogIsTrue() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              intent = Intent(),
                              appSettingsUiState = AppSettingsUiState.Empty,
                              addSettingsDialogState = addSettingsDialogState,
                              addShortcutDialogState = addShortcutDialogState,
                              showCopyPermissionCommandDialog = true,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {},
                              scrollState = scrollState,
                              onAddSettings = {},
                              onAddShortcut = {
                                  addShortcutDialogState.updateShowDialog(true)
                              },
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithContentDescription("Add shortcut icon").performClick()

        composeTestRule.onNodeWithTag("addShortcutDialog").assertIsDisplayed()
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