package com.feature.securesettingslist

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.core.model.SecureSettings
import org.junit.Rule
import org.junit.Test

class SecureSettingsListScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testShowsLoadingPlaceHolderScreen_SecureSettingsListUiStateLoading() {
        composeTestRule.setContent {
            SecureSettingsListScreen(snackbarHostState = { SnackbarHostState() },
                                     selectedRadioOptionIndex = { -1 },
                                     onRadioOptionSelected = {},
                                     onItemClick = {},
                                     onNavigationIconClick = {},
                                     uIState = { SecureSettingsListUiState.Loading })
        }

        composeTestRule.onNodeWithTag("securesettingslist:loading").assertExists()
    }

    @Test
    fun testShowsLazyColumn_SecureSettingsListUiStateSuccess() {
        composeTestRule.setContent {
            SecureSettingsListScreen(snackbarHostState = { SnackbarHostState() },
                                     selectedRadioOptionIndex = { -1 },
                                     onRadioOptionSelected = {},
                                     onItemClick = {},
                                     onNavigationIconClick = {},
                                     uIState = {
                                         SecureSettingsListUiState.Success(
                                             listOf(
                                                 SecureSettings(id = 0, name = "Key", value = "0")
                                             )
                                         )
                                     })
        }

        composeTestRule.onNodeWithTag("securesettingslist:success").assertExists()
    }

    @Test
    fun testShowsSnackbar_onItemClick() {
        val secureSettingsItemKeyToTest = "Key"

        composeTestRule.setContent {
            SecureSettingsListScreen(snackbarHostState = { SnackbarHostState() },
                                     selectedRadioOptionIndex = { 0 },
                                     onRadioOptionSelected = {},
                                     onItemClick = {},
                                     onNavigationIconClick = {},
                                     uIState = {
                                         SecureSettingsListUiState.Success(
                                             listOf(
                                                 SecureSettings(
                                                     id = 0,
                                                     name = secureSettingsItemKeyToTest,
                                                     value = "0"
                                                 )
                                             )
                                         )
                                     })
        }

        composeTestRule.onNodeWithText(secureSettingsItemKeyToTest).performClick()

        composeTestRule.onNodeWithTag("securesettingslist:snackbar").assertExists()
    }
}