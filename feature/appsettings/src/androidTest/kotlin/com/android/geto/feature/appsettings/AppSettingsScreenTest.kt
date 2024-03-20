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
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SettingsType
import org.junit.Rule
import org.junit.Test

class AppSettingsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun getoOverlayLoadingWheelIsDisplayed_whenAppSettingsUiStateIsLoading() {
        composeTestRule.setContent {
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

        composeTestRule.onNodeWithContentDescription("GetoOverlayLoadingWheel").assertIsDisplayed()
    }

    @Test
    fun emptyListPlaceHolderScreenIsDisplayed_whenDataIsEmpty() {
        composeTestRule.setContent {
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

        composeTestRule.onNodeWithTag("appsettings:emptyListPlaceHolderScreen").assertIsDisplayed()
    }

    @Test
    fun lazyColumnIsDisplayed_whenAppSettingsUiStateIsSuccess() {
        composeTestRule.setContent {
            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Success(testAppSettingsList),
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onSettingsIconClick = {},
                              onShortcutIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithTag("appsettings:lazyColumn").assertIsDisplayed()
    }

    @Test
    fun revertTooltipIsDisplayed_whenRevertIconIsLongClicked() {
        composeTestRule.setContent {
            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Success(testAppSettingsList),
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onSettingsIconClick = {},
                              onShortcutIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithContentDescription("Revert icon").performTouchInput {
            longClick(durationMillis = 1000L)
        }

        composeTestRule.onNodeWithTag("appsettings:tooltip:revert").assertIsDisplayed()
    }

    @Test
    fun settingsTooltipIsDisplayed_whenSettingsIconIsLongClicked() {
        composeTestRule.setContent {
            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Success(testAppSettingsList),
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onSettingsIconClick = {},
                              onShortcutIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithContentDescription("Settings icon").performTouchInput {
            longClick(durationMillis = 1000L)
        }

        composeTestRule.onNodeWithTag("appsettings:tooltip:settings").assertIsDisplayed()
    }

    @Test
    fun shortcutTooltipIsDisplayed_whenShortcutIconIsLongClicked() {
        composeTestRule.setContent {
            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Success(testAppSettingsList),
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onSettingsIconClick = {},
                              onShortcutIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithContentDescription("Shortcut icon").performTouchInput {
            longClick(durationMillis = 1000L)
        }

        composeTestRule.onNodeWithTag("appsettings:tooltip:shortcut").assertIsDisplayed()
    }
}

private val testAppSettingsList = List(2) { index ->
    AppSettings(
        id = index,
        enabled = true,
        settingsType = SettingsType.SYSTEM,
        packageName = "packageName$index",
        label = "Label $index", key = "key$index",
        valueOnLaunch = "valueOnLaunch$index",
        valueOnRevert = "valueOnRevert$index"
    )
}