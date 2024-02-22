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