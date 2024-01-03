package com.feature.securesettingslist

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.core.testing.data.secureSettingsTestData
import org.junit.Rule
import org.junit.Test

class SecureSettingsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun state_loading_shows_LoadingPlaceHolderScreen() {
        composeTestRule.setContent {
            SecureSettingsScreen(snackbarHostState = { SnackbarHostState() },
                                 selectedRadioOptionIndex = { -1 },
                                 onRadioOptionSelected = {},
                                 onItemClick = {},
                                 onNavigationIconClick = {},
                                 uIState = { SecureSettingsUiState.Loading })
        }

        composeTestRule.onNodeWithTag("securesettingslist:loading").assertExists()
    }

    @Test
    fun state_success_shows_LazyColumn() {
        composeTestRule.setContent {
            SecureSettingsScreen(snackbarHostState = { SnackbarHostState() },
                                 selectedRadioOptionIndex = { -1 },
                                 onRadioOptionSelected = {},
                                 onItemClick = {},
                                 onNavigationIconClick = {},
                                 uIState = { SecureSettingsUiState.Success(secureSettingsTestData) })
        }

        composeTestRule.onNodeWithTag("securesettingslist:success").assertExists()
    }

    @Test
    fun on_item_click_shows_Snackbar_copied_settings_key() {
        composeTestRule.setContent {
            SecureSettingsScreen(snackbarHostState = { SnackbarHostState() },
                                 selectedRadioOptionIndex = { 0 },
                                 onRadioOptionSelected = {},
                                 onItemClick = {},
                                 onNavigationIconClick = {},
                                 uIState = { SecureSettingsUiState.Success(secureSettingsTestData) })
        }

        composeTestRule.onNodeWithText("name").performClick()

        composeTestRule.onNodeWithTag("securesettingslist:snackbar").assertExists()
    }
}