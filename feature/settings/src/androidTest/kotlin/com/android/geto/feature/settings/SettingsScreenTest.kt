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
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.android.geto.core.model.DarkThemeConfig
import com.android.geto.core.model.ThemeBrand
import com.android.geto.feature.settings.dialog.dark.rememberDarkDialogState
import com.android.geto.feature.settings.dialog.theme.rememberThemeDialogState
import org.junit.Rule
import org.junit.Test

class SettingsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun getoOverlayLoadingWheelIsDisplayed_whenSettingsUiStateIsLoading() {
        composeTestRule.setContent {
            val themeDialogState = rememberThemeDialogState()

            val darkDialogState = rememberDarkDialogState()

            SettingsScreen(settingsUiState = SettingsUiState.Loading,
                           themeDialogState = themeDialogState,
                           darkDialogState = darkDialogState,
                           supportDynamicColor = true,
                           onChangeThemeBrand = {},
                           onChangeDynamicColorPreference = {},
                           onChangeDarkThemeConfig = {},
                           onNavigationIconClick = {})
        }

        composeTestRule.onNodeWithContentDescription("GetoOverlayLoadingWheel").assertIsDisplayed()
    }

    @Test
    fun settingsIsDisplayed_whenSettingsUiStateIsSuccess() {
        composeTestRule.setContent {
            val themeDialogState = rememberThemeDialogState()

            val darkDialogState = rememberDarkDialogState()

            SettingsScreen(settingsUiState = SettingsUiState.Success(
                UserEditableSettings(
                    brand = ThemeBrand.DEFAULT,
                    useDynamicColor = true,
                    darkThemeConfig = DarkThemeConfig.DARK
                )
            ),
                           themeDialogState = themeDialogState,
                           darkDialogState = darkDialogState,
                           supportDynamicColor = true,
                           onChangeThemeBrand = {},
                           onChangeDynamicColorPreference = {},
                           onChangeDarkThemeConfig = {},
                           onNavigationIconClick = {})
        }

        composeTestRule.onNodeWithTag("settings:column").assertIsDisplayed()
    }

    @Test
    fun dynamicColorSwitchIsOn_whenDynamicColorIsTrue() {
        composeTestRule.setContent {
            val themeDialogState = rememberThemeDialogState()

            val darkDialogState = rememberDarkDialogState()

            SettingsScreen(settingsUiState = SettingsUiState.Success(
                UserEditableSettings(
                    brand = ThemeBrand.DEFAULT,
                    useDynamicColor = true,
                    darkThemeConfig = DarkThemeConfig.DARK
                )
            ),
                           themeDialogState = themeDialogState,
                           darkDialogState = darkDialogState,
                           supportDynamicColor = true,
                           onChangeThemeBrand = {},
                           onChangeDynamicColorPreference = {},
                           onChangeDarkThemeConfig = {},
                           onNavigationIconClick = {})
        }

        composeTestRule.onNodeWithTag("settings:dynamic:switch").assertIsOn()
    }

    @Test
    fun dynamicColorSwitchIsOff_whenDynamicColorIsFalse() {
        composeTestRule.setContent {
            val themeDialogState = rememberThemeDialogState()

            val darkDialogState = rememberDarkDialogState()

            SettingsScreen(settingsUiState = SettingsUiState.Success(
                UserEditableSettings(
                    brand = ThemeBrand.DEFAULT,
                    useDynamicColor = false,
                    darkThemeConfig = DarkThemeConfig.DARK
                )
            ),
                           themeDialogState = themeDialogState,
                           darkDialogState = darkDialogState,
                           supportDynamicColor = true,
                           onChangeThemeBrand = {},
                           onChangeDynamicColorPreference = {},
                           onChangeDarkThemeConfig = {},
                           onNavigationIconClick = {})
        }

        composeTestRule.onNodeWithTag("settings:dynamic:switch").assertIsOff()
    }

    @Test
    fun themeDialogIsDisplayed_whenThemeColumnIsClicked() {
        composeTestRule.setContent {
            val themeDialogState = rememberThemeDialogState()

            val darkDialogState = rememberDarkDialogState()

            SettingsScreen(settingsUiState = SettingsUiState.Success(
                UserEditableSettings(
                    brand = ThemeBrand.DEFAULT,
                    useDynamicColor = true,
                    darkThemeConfig = DarkThemeConfig.DARK
                )
            ),
                           themeDialogState = themeDialogState,
                           darkDialogState = darkDialogState,
                           supportDynamicColor = true,
                           onChangeThemeBrand = {},
                           onChangeDynamicColorPreference = {},
                           onChangeDarkThemeConfig = {},
                           onNavigationIconClick = {})
        }

        composeTestRule.onNodeWithTag("settings:theme").performClick()

        composeTestRule.onNodeWithContentDescription("Theme Dialog").assertIsDisplayed()
    }

    @Test
    fun dynamicRowIsDisplayed_whenThemeBrandIsDefault() {
        composeTestRule.setContent {
            val themeDialogState = rememberThemeDialogState()

            val darkDialogState = rememberDarkDialogState()

            SettingsScreen(settingsUiState = SettingsUiState.Success(
                UserEditableSettings(
                    brand = ThemeBrand.DEFAULT,
                    useDynamicColor = true,
                    darkThemeConfig = DarkThemeConfig.DARK
                )
            ),
                           themeDialogState = themeDialogState,
                           darkDialogState = darkDialogState,
                           supportDynamicColor = true,
                           onChangeThemeBrand = {},
                           onChangeDynamicColorPreference = {},
                           onChangeDarkThemeConfig = {},
                           onNavigationIconClick = {})
        }

        composeTestRule.onNodeWithTag("settings:dynamic").assertIsDisplayed()
    }

    @Test
    fun dynamicRowIsNotDisplayed_whenThemeBrandIsAndroid() {
        composeTestRule.setContent {
            val themeDialogState = rememberThemeDialogState()

            val darkDialogState = rememberDarkDialogState()

            SettingsScreen(settingsUiState = SettingsUiState.Success(
                UserEditableSettings(
                    brand = ThemeBrand.ANDROID,
                    useDynamicColor = true,
                    darkThemeConfig = DarkThemeConfig.DARK
                )
            ),
                           themeDialogState = themeDialogState,
                           darkDialogState = darkDialogState,
                           supportDynamicColor = true,
                           onChangeThemeBrand = {},
                           onChangeDynamicColorPreference = {},
                           onChangeDarkThemeConfig = {},
                           onNavigationIconClick = {})
        }

        composeTestRule.onNodeWithTag("settings:dynamic").assertIsNotDisplayed()
    }

    @Test
    fun darkDialogIsDisplayed_whenDarkColumnIsClicked() {
        composeTestRule.setContent {
            val themeDialogState = rememberThemeDialogState()

            val darkDialogState = rememberDarkDialogState()

            SettingsScreen(settingsUiState = SettingsUiState.Success(
                UserEditableSettings(
                    brand = ThemeBrand.DEFAULT,
                    useDynamicColor = true,
                    darkThemeConfig = DarkThemeConfig.DARK
                )
            ),
                           themeDialogState = themeDialogState,
                           darkDialogState = darkDialogState,
                           supportDynamicColor = true,
                           onChangeThemeBrand = {},
                           onChangeDynamicColorPreference = {},
                           onChangeDarkThemeConfig = {},
                           onNavigationIconClick = {})
        }

        composeTestRule.onNodeWithTag("settings:dark").performClick()

        composeTestRule.onNodeWithContentDescription("Dark Dialog").assertIsDisplayed()
    }
}