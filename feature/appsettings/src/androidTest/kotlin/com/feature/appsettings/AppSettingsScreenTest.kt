package com.feature.appsettings

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class AppSettingsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loadingPlaceHolderScreen_whenAppSettingsUiStateIsLoading_showLoading() {
        composeTestRule.setContent {
            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Loading,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onAddAppSettingsClick = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithTag("appsettings:loadingPlaceHolderScreen").assertExists()
    }

    @Test
    fun emptyListPlaceHolderScreen_whenAppSettingsUiStateIsEmpty_showEmptyScreen() {
        composeTestRule.setContent {
            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Empty,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onAddAppSettingsClick = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithTag("appsettings:emptyListPlaceHolderScreen").assertExists()
    }

    @Test
    fun snackbar_whenRevertIconClicked_inAppSettingsUiStateEmpty_showMessage() {
        composeTestRule.setContent {
            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Empty,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onAddAppSettingsClick = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithContentDescription("Revert icon").performClick()

        composeTestRule.onNodeWithTag("appsettings:snackbar").assertExists()
    }

    @Test
    fun snackbar_whenLaunchIconClicked_inAppSettingsUiStateEmpty_showMessage() {
        composeTestRule.setContent {

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Empty,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onAddAppSettingsClick = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithContentDescription("Launch icon").performClick()

        composeTestRule.onNodeWithTag("appsettings:snackbar").assertExists()
    }
}