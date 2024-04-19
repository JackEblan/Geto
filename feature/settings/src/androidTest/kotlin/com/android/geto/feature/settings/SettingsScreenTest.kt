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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.android.geto.core.model.DarkThemeConfig
import com.android.geto.core.model.ThemeBrand
import com.android.geto.core.model.UserData
import org.junit.Rule
import org.junit.Test

class SettingsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun getoLoadingWheel_isDisplayed_whenSettingsUiState_isLoading() {
        composeTestRule.setContent {
            SettingsScreen(
                settingsUiState = SettingsUiState.Loading,
                supportDynamicColor = false,
                showThemeDialog = false,
                showDarkDialog = false,
                showCleanDialog = false,
                onShowThemeDialog = {},
                onShowDarkDialog = {},
                onShowCleanDialog = {},
                onUpdateThemeBrand = {},
                onUpdateDarkThemeConfig = {},
                onCleanAppSettings = {},
                onThemeDialogDismissRequest = {},
                onDarkDialogDismissRequest = {},
                onCleanDialogDismissRequest = {},
                onChangeDynamicColorPreference = {},
                onChangeAutoLaunchPreference = {},
                onNavigationIconClick = {},
            )
        }

        composeTestRule.onNodeWithContentDescription("GetoLoadingWheel").assertIsDisplayed()
    }

    @Test
    fun settings_isDisplayed_whenSettingsUiState_isSuccess() {
        composeTestRule.setContent {
            SettingsScreen(
                settingsUiState = SettingsUiState.Success(
                    UserData(
                        themeBrand = ThemeBrand.DEFAULT,
                        useDynamicColor = true,
                        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                        useAutoLaunch = false,
                    ),
                ),
                supportDynamicColor = false,
                showThemeDialog = false,
                showDarkDialog = false,
                showCleanDialog = false,
                onShowThemeDialog = {},
                onShowDarkDialog = {},
                onShowCleanDialog = {},
                onUpdateThemeBrand = {},
                onUpdateDarkThemeConfig = {},
                onCleanAppSettings = {},
                onThemeDialogDismissRequest = {},
                onDarkDialogDismissRequest = {},
                onCleanDialogDismissRequest = {},
                onChangeDynamicColorPreference = {},
                onChangeAutoLaunchPreference = {},
                onNavigationIconClick = {},
            )
        }

        composeTestRule.onNodeWithTag("settings:success").assertIsDisplayed()
    }

    @Test
    fun dynamicColorSwitch_isOn_whenUseDynamicColor_isTrue() {
        composeTestRule.setContent {
            SettingsScreen(
                settingsUiState = SettingsUiState.Success(
                    UserData(
                        themeBrand = ThemeBrand.DEFAULT,
                        useDynamicColor = true,
                        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                        useAutoLaunch = false,
                    ),
                ),
                supportDynamicColor = true,
                showThemeDialog = false,
                showDarkDialog = false,
                showCleanDialog = false,
                onShowThemeDialog = {},
                onShowDarkDialog = {},
                onShowCleanDialog = {},
                onUpdateThemeBrand = {},
                onUpdateDarkThemeConfig = {},
                onCleanAppSettings = {},
                onThemeDialogDismissRequest = {},
                onDarkDialogDismissRequest = {},
                onCleanDialogDismissRequest = {},
                onChangeDynamicColorPreference = {},
                onChangeAutoLaunchPreference = {},
                onNavigationIconClick = {},
            )
        }

        composeTestRule.onNodeWithTag("settings:dynamicSwitch").assertIsOn()
    }

    @Test
    fun dynamicColorSwitch_isOff_whenUseDynamicColor_isFalse() {
        composeTestRule.setContent {
            SettingsScreen(
                settingsUiState = SettingsUiState.Success(
                    UserData(
                        themeBrand = ThemeBrand.DEFAULT,
                        useDynamicColor = false,
                        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                        useAutoLaunch = false,
                    ),
                ),
                supportDynamicColor = true,
                showThemeDialog = false,
                showDarkDialog = false,
                showCleanDialog = false,
                onShowThemeDialog = {},
                onShowDarkDialog = {},
                onShowCleanDialog = {},
                onUpdateThemeBrand = {},
                onUpdateDarkThemeConfig = {},
                onCleanAppSettings = {},
                onThemeDialogDismissRequest = {},
                onDarkDialogDismissRequest = {},
                onCleanDialogDismissRequest = {},
                onChangeDynamicColorPreference = {},
                onChangeAutoLaunchPreference = {},
                onNavigationIconClick = {},
            )
        }

        composeTestRule.onNodeWithTag("settings:dynamicSwitch").assertIsOff()
    }

    @Test
    fun dynamicRow_isDisplayed_whenThemeBrand_isDefault() {
        composeTestRule.setContent {
            SettingsScreen(
                settingsUiState = SettingsUiState.Success(
                    UserData(
                        themeBrand = ThemeBrand.DEFAULT,
                        useDynamicColor = true,
                        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                        useAutoLaunch = false,
                    ),
                ),
                supportDynamicColor = true,
                showThemeDialog = false,
                showDarkDialog = false,
                showCleanDialog = false,
                onShowThemeDialog = {},
                onShowDarkDialog = {},
                onShowCleanDialog = {},
                onUpdateThemeBrand = {},
                onUpdateDarkThemeConfig = {},
                onCleanAppSettings = {},
                onThemeDialogDismissRequest = {},
                onDarkDialogDismissRequest = {},
                onCleanDialogDismissRequest = {},
                onChangeDynamicColorPreference = {},
                onChangeAutoLaunchPreference = {},
                onNavigationIconClick = {},
            )
        }

        composeTestRule.onNodeWithTag("settings:dynamic").assertIsDisplayed()
    }

    @Test
    fun dynamicRow_isNotDisplayed_whenThemeBrand_isAndroid() {
        composeTestRule.setContent {
            SettingsScreen(
                settingsUiState = SettingsUiState.Success(
                    UserData(
                        themeBrand = ThemeBrand.ANDROID,
                        useDynamicColor = true,
                        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                        useAutoLaunch = false,
                    ),
                ),
                supportDynamicColor = false,
                showThemeDialog = false,
                showDarkDialog = false,
                showCleanDialog = false,
                onShowThemeDialog = {},
                onShowDarkDialog = {},
                onShowCleanDialog = {},
                onUpdateThemeBrand = {},
                onUpdateDarkThemeConfig = {},
                onCleanAppSettings = {},
                onThemeDialogDismissRequest = {},
                onDarkDialogDismissRequest = {},
                onCleanDialogDismissRequest = {},
                onChangeDynamicColorPreference = {},
                onChangeAutoLaunchPreference = {},
                onNavigationIconClick = {},
            )
        }

        composeTestRule.onNodeWithTag("settings:dynamic").assertIsNotDisplayed()
    }

    @Test
    fun dynamicRow_isNotDisplayed_whenUnSupportDynamicColor() {
        composeTestRule.setContent {
            SettingsScreen(
                settingsUiState = SettingsUiState.Success(
                    UserData(
                        themeBrand = ThemeBrand.DEFAULT,
                        useDynamicColor = true,
                        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                        useAutoLaunch = false,
                    ),
                ),
                supportDynamicColor = false,
                showThemeDialog = false,
                showDarkDialog = false,
                showCleanDialog = false,
                onShowThemeDialog = {},
                onShowDarkDialog = {},
                onShowCleanDialog = {},
                onUpdateThemeBrand = {},
                onUpdateDarkThemeConfig = {},
                onCleanAppSettings = {},
                onThemeDialogDismissRequest = {},
                onDarkDialogDismissRequest = {},
                onCleanDialogDismissRequest = {},
                onChangeDynamicColorPreference = {},
                onChangeAutoLaunchPreference = {},
                onNavigationIconClick = {},
            )
        }

        composeTestRule.onNodeWithTag("settings:dynamic").assertIsNotDisplayed()
    }

    @Test
    fun autoLaunchSwitch_isOff_whenUseAutoLaunch_isFalse() {
        composeTestRule.setContent {
            SettingsScreen(
                settingsUiState = SettingsUiState.Success(
                    UserData(
                        themeBrand = ThemeBrand.DEFAULT,
                        useDynamicColor = true,
                        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                        useAutoLaunch = false,
                    ),
                ),
                supportDynamicColor = true,
                showThemeDialog = false,
                showDarkDialog = false,
                showCleanDialog = false,
                onShowThemeDialog = {},
                onShowDarkDialog = {},
                onShowCleanDialog = {},
                onUpdateThemeBrand = {},
                onUpdateDarkThemeConfig = {},
                onCleanAppSettings = {},
                onThemeDialogDismissRequest = {},
                onDarkDialogDismissRequest = {},
                onCleanDialogDismissRequest = {},
                onChangeDynamicColorPreference = {},
                onChangeAutoLaunchPreference = {},
                onNavigationIconClick = {},
            )
        }

        composeTestRule.onNodeWithTag("settings:autoLaunchSwitch").assertIsOff()
    }

    @Test
    fun autoLaunchSwitch_isOn_whenUseAutoLaunch_isTrue() {
        composeTestRule.setContent {
            SettingsScreen(
                settingsUiState = SettingsUiState.Success(
                    UserData(
                        themeBrand = ThemeBrand.DEFAULT,
                        useDynamicColor = true,
                        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                        useAutoLaunch = true,
                    ),
                ),
                supportDynamicColor = true,
                showThemeDialog = false,
                showDarkDialog = false,
                showCleanDialog = false,
                onShowThemeDialog = {},
                onShowDarkDialog = {},
                onShowCleanDialog = {},
                onUpdateThemeBrand = {},
                onUpdateDarkThemeConfig = {},
                onCleanAppSettings = {},
                onThemeDialogDismissRequest = {},
                onDarkDialogDismissRequest = {},
                onCleanDialogDismissRequest = {},
                onChangeDynamicColorPreference = {},
                onChangeAutoLaunchPreference = {},
                onNavigationIconClick = {},
            )
        }

        composeTestRule.onNodeWithTag("settings:autoLaunchSwitch").assertIsOn()
    }

    @Test
    fun themeDialog_isDisplayed_whenThemeSetting_isClicked_thenDismissed() {
        composeTestRule.setContent {
            var showThemeDialog by rememberSaveable { mutableStateOf(false) }

            SettingsScreen(
                settingsUiState = SettingsUiState.Success(
                    UserData(
                        themeBrand = ThemeBrand.DEFAULT,
                        useDynamicColor = true,
                        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                        useAutoLaunch = true,
                    ),
                ),
                supportDynamicColor = true,
                showThemeDialog = showThemeDialog,
                showDarkDialog = false,
                showCleanDialog = false,
                onShowThemeDialog = {
                    showThemeDialog = true
                },
                onShowDarkDialog = {},
                onShowCleanDialog = {},
                onUpdateThemeBrand = {},
                onUpdateDarkThemeConfig = {},
                onCleanAppSettings = {},
                onThemeDialogDismissRequest = {
                    showThemeDialog = false
                },
                onDarkDialogDismissRequest = {},
                onCleanDialogDismissRequest = {},
                onChangeDynamicColorPreference = {},
                onChangeAutoLaunchPreference = {},
                onNavigationIconClick = {},
            )
        }

        composeTestRule.onNodeWithTag("settings:theme").performClick()

        composeTestRule.onNodeWithContentDescription("Theme Dialog").assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancel").performClick()

        composeTestRule.onNodeWithContentDescription("Theme Dialog").assertIsNotDisplayed()
    }

    @Test
    fun darkDialog_isDisplayed_whenDarkSetting_isClicked_thenDismissed() {
        composeTestRule.setContent {
            var showDarkDialog by rememberSaveable { mutableStateOf(false) }

            SettingsScreen(
                settingsUiState = SettingsUiState.Success(
                    UserData(
                        themeBrand = ThemeBrand.DEFAULT,
                        useDynamicColor = true,
                        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                        useAutoLaunch = true,
                    ),
                ),
                supportDynamicColor = true,
                showThemeDialog = false,
                showDarkDialog = showDarkDialog,
                showCleanDialog = false,
                onShowThemeDialog = {},
                onShowDarkDialog = {
                    showDarkDialog = true
                },
                onShowCleanDialog = {},
                onUpdateThemeBrand = {},
                onUpdateDarkThemeConfig = {},
                onCleanAppSettings = {},
                onThemeDialogDismissRequest = {},
                onDarkDialogDismissRequest = {
                    showDarkDialog = false
                },
                onCleanDialogDismissRequest = {},
                onChangeDynamicColorPreference = {},
                onChangeAutoLaunchPreference = {},
                onNavigationIconClick = {},
            )
        }

        composeTestRule.onNodeWithTag("settings:dark").performClick()

        composeTestRule.onNodeWithContentDescription("Dark Dialog").assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancel").performClick()

        composeTestRule.onNodeWithContentDescription("Dark Dialog").assertIsNotDisplayed()
    }

    @Test
    fun cleanDialog_isDisplayed_whenCleanSetting_isClicked_thenDismissed() {
        composeTestRule.setContent {
            var showCleanDialog by rememberSaveable { mutableStateOf(false) }

            SettingsScreen(
                settingsUiState = SettingsUiState.Success(
                    UserData(
                        themeBrand = ThemeBrand.DEFAULT,
                        useDynamicColor = true,
                        darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                        useAutoLaunch = true,
                    ),
                ),
                supportDynamicColor = true,
                showThemeDialog = false,
                showDarkDialog = false,
                showCleanDialog = showCleanDialog,
                onShowThemeDialog = {},
                onShowDarkDialog = {},
                onShowCleanDialog = {
                    showCleanDialog = true
                },
                onUpdateThemeBrand = {},
                onUpdateDarkThemeConfig = {},
                onCleanAppSettings = {},
                onThemeDialogDismissRequest = {},
                onDarkDialogDismissRequest = {},
                onCleanDialogDismissRequest = {
                    showCleanDialog = false
                },
                onChangeDynamicColorPreference = {},
                onChangeAutoLaunchPreference = {},
                onNavigationIconClick = {},
            )
        }

        composeTestRule.onNodeWithTag("settings:clean").performClick()

        composeTestRule.onNodeWithContentDescription("Clean Dialog").assertIsDisplayed()

        composeTestRule.onNodeWithText("Cancel").performClick()

        composeTestRule.onNodeWithContentDescription("Clean Dialog").assertIsNotDisplayed()
    }
}
