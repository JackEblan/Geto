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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SettingsType
import com.android.geto.core.ui.rememberAddAppSettingsDialogState
import com.android.geto.core.ui.rememberAddShortcutDialogState
import com.android.geto.core.ui.rememberUpdateShortcutDialogState
import org.junit.Rule
import org.junit.Test

class AppSettingsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loadingPlaceHolderScreenIsDisplayed_whenAppSettingsUiStateIsLoading() {
        composeTestRule.setContent {
            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val updateShortcutDialogState = rememberUpdateShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
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

        composeTestRule.onNodeWithTag("appsettings:loadingPlaceHolderScreen").assertIsDisplayed()
    }

    @Test
    fun emptyListPlaceHolderScreenIsDisplayed_whenAppSettingsUiStateIsEmpty() {
        composeTestRule.setContent {
            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val updateShortcutDialogState = rememberUpdateShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
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

        composeTestRule.onNodeWithTag("appsettings:emptyListPlaceHolderScreen").assertIsDisplayed()
    }

    @Test
    fun lazyColumnIsDisplayed_whenAppSettingsUiStateIsSuccess() {
        composeTestRule.setContent {
            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val updateShortcutDialogState = rememberUpdateShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Success(testAppSettingsList),
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

        composeTestRule.onNodeWithTag("appsettings:lazyColumn").assertIsDisplayed()
    }

    @Test
    fun snackbarExists_whenRevertIconIsClicked_appSettingsUiStateIsEmpty() {
        composeTestRule.setContent {
            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val updateShortcutDialogState = rememberUpdateShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Success(testAppSettingsList),
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

        composeTestRule.onNodeWithContentDescription("Revert icon").performClick()

        composeTestRule.onNodeWithTag("appsettings:snackbar").assertExists()
    }

    @Test
    fun snackbarExists_whenLaunchIconIsClicked_appSettingsUiStateIsEmpty() {
        composeTestRule.setContent {
            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val updateShortcutDialogState = rememberUpdateShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
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

        composeTestRule.onNodeWithContentDescription("Launch icon").performClick()

        composeTestRule.onNodeWithTag("appsettings:snackbar").assertExists()
    }

    @Test
    fun addAppSettingsDialogIsDisplayed_whenSettingsIconIsClicked() {
        composeTestRule.setContent {
            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val updateShortcutDialogState = rememberUpdateShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Empty,
                              addAppSettingsDialogState = addAppSettingsDialogState,
                              addShortcutDialogState = addShortcutDialogState,
                              updateShortcutDialogState = updateShortcutDialogState,
                              showCopyPermissionCommandDialog = false,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onSettingsIconClick = {
                                  addAppSettingsDialogState.updateShowDialog(true)
                              },
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

        composeTestRule.onNodeWithContentDescription("Settings icon").performClick()

        composeTestRule.onNodeWithTag("addAppSettingsDialog").assertIsDisplayed()
    }

    @Test
    fun copyPermissionCommandDialogIsDisplayed_whenShowCopyPermissionCommandDialogIsTrue() {
        composeTestRule.setContent {
            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val updateShortcutDialogState = rememberUpdateShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Empty,
                              addAppSettingsDialogState = addAppSettingsDialogState,
                              addShortcutDialogState = addShortcutDialogState,
                              updateShortcutDialogState = updateShortcutDialogState,
                              showCopyPermissionCommandDialog = true,
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

        composeTestRule.onNodeWithTag("copyPermissionCommandDialog").assertIsDisplayed()
    }

    @Test
    fun addShortcutDialogIsDisplayed_whenShortcutIconIsClicked() {
        composeTestRule.setContent {
            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val updateShortcutDialogState = rememberUpdateShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Empty,
                              addAppSettingsDialogState = addAppSettingsDialogState,
                              addShortcutDialogState = addShortcutDialogState,
                              updateShortcutDialogState = updateShortcutDialogState,
                              showCopyPermissionCommandDialog = false,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onSettingsIconClick = {},
                              onShortcutIconClick = {
                                  addShortcutDialogState.updateShowDialog(true)
                              },
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

        composeTestRule.onNodeWithContentDescription("Shortcut icon").performClick()

        composeTestRule.onNodeWithTag("addShortcutDialog").assertIsDisplayed()
    }

    @Test
    fun updateShortcutDialogIsDisplayed_whenShortcutIconIsClicked() {
        composeTestRule.setContent {
            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val updateShortcutDialogState = rememberUpdateShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Empty,
                              addAppSettingsDialogState = addAppSettingsDialogState,
                              addShortcutDialogState = addShortcutDialogState,
                              updateShortcutDialogState = updateShortcutDialogState,
                              showCopyPermissionCommandDialog = false,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onSettingsIconClick = {},
                              onShortcutIconClick = {
                                  updateShortcutDialogState.updateShowDialog(true)
                              },
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {},
                              scrollState = scrollState,
                              onAddSettings = {},
                              onAddShortcut = {},
                              onUpdateShortcut = {},
                              onRefreshShortcut = {},
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithContentDescription("Shortcut icon").performClick()

        composeTestRule.onNodeWithTag("updateShortcutDialog").assertIsDisplayed()
    }

    @Test
    fun revertTooltipIsDisplayed_whenRevertIconIsLongClicked() {
        composeTestRule.setContent {
            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val updateShortcutDialogState = rememberUpdateShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
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
                              onRefreshShortcut = {},
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithContentDescription("Revert icon").performTouchInput {
            longClick(durationMillis = 1000L)
        }

        composeTestRule.onNodeWithTag("appsettings:tooltip:revert").assertIsDisplayed()
    }

    @Test
    fun settingsTooltipIsDisplayed_whenSettingsIconIsLongClicked() {
        composeTestRule.setContent {
            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val updateShortcutDialogState = rememberUpdateShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
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
                              onRefreshShortcut = {},
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithContentDescription("Settings icon").performTouchInput {
            longClick(durationMillis = 1000L)
        }

        composeTestRule.onNodeWithTag("appsettings:tooltip:settings").assertIsDisplayed()
    }

    @Test
    fun shortcutTooltipIsDisplayed_whenShortcutIconIsLongClicked() {
        composeTestRule.setContent {
            val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

            val addShortcutDialogState = rememberAddShortcutDialogState()

            val updateShortcutDialogState = rememberUpdateShortcutDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
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
                              onRefreshShortcut = {},
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithContentDescription("Shortcut icon").performTouchInput {
            longClick(durationMillis = 1000L)
        }

        composeTestRule.onNodeWithTag("appsettings:tooltip:shortcut").assertIsDisplayed()
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