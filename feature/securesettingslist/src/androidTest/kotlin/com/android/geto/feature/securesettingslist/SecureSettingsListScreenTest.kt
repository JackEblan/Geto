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

package com.android.geto.feature.securesettingslist

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.android.geto.core.model.SecureSettings
import org.junit.Rule
import org.junit.Test

class SecureSettingsListScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loadingPlaceHolderScreenIsDisplayed_whenSecureSettingsListUiStateIsLoading() {
        composeTestRule.setContent {
            SecureSettingsListScreen(
                snackbarHostState = SnackbarHostState(),
                dropDownExpanded = false,
                onDropDownMenu = { },
                onItemClick = {},
                onNavigationIconClick = {},
                onDropDownMenuItemSelected = {},
                secureSettingsListUiState = SecureSettingsListUiState.Loading
            )
        }

        composeTestRule.onNodeWithTag("securesettingslist:loadingPlaceHolderScreen")
            .assertIsDisplayed()
    }

    @Test
    fun lazyColumnIsDisplayed_whenSecureSettingsListUiStateIsSuccess() {
        composeTestRule.setContent {
            SecureSettingsListScreen(
                snackbarHostState = SnackbarHostState(),
                dropDownExpanded = false,
                onDropDownMenu = { },
                onItemClick = {},
                onNavigationIconClick = {},
                onDropDownMenuItemSelected = {},
                secureSettingsListUiState = SecureSettingsListUiState.Success(secureSettingsList)
            )
        }

        composeTestRule.onNodeWithTag("securesettingslist:lazyColumn").assertIsDisplayed()
    }

    @Test
    fun snackBarExists_whenSecureSettingsItemIsClicked() {
        val secureSettingsItemKeyToTest = "name0"

        composeTestRule.setContent {
            SecureSettingsListScreen(
                snackbarHostState = SnackbarHostState(),
                dropDownExpanded = false,
                onDropDownMenu = { },
                onItemClick = {},
                onNavigationIconClick = {},
                onDropDownMenuItemSelected = {},
                secureSettingsListUiState = SecureSettingsListUiState.Success(secureSettingsList)
            )
        }

        composeTestRule.onNodeWithText(secureSettingsItemKeyToTest).performClick()

        composeTestRule.onNodeWithTag("securesettingslist:snackbar").assertExists()
    }
}

private val secureSettingsList = listOf(
    SecureSettings(id = 0L, name = "name0", value = "value0"),
    SecureSettings(id = 1L, name = "name1", value = "value1")
)