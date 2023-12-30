package com.feature.appsettings

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.core.testing.data.appSettingsTestData
import org.junit.Rule
import org.junit.Test

class AppSettingsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun state_loading_shows_LoadingPlaceHolderScreen() {
        composeTestRule.setContent {
            AppSettingsScreen(snackbarHostState = { SnackbarHostState() },
                              appName = { "Geto" },
                              uIState = { AppSettingsUiState.Loading },
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onUserAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteUserAppSettingsItem = {},
                              onAddUserAppSettingsClick = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithTag("userappsettings:loading").assertExists()
    }

    @Test
    fun state_empty_shows_EmptyListPlaceHolderScreen() {
        composeTestRule.setContent {
            AppSettingsScreen(snackbarHostState = { SnackbarHostState() },
                              appName = { "Geto" },
                              uIState = { AppSettingsUiState.Empty },
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onUserAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteUserAppSettingsItem = {},
                              onAddUserAppSettingsClick = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithTag("userappsettings:empty").assertExists()
    }

    @Test
    fun state_empty_shows_LazyColumn() {
        composeTestRule.setContent {
            AppSettingsScreen(snackbarHostState = { SnackbarHostState() },
                              appName = { "Geto" },
                              uIState = { AppSettingsUiState.Success(appSettingsTestData) },
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onUserAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteUserAppSettingsItem = {},
                              onAddUserAppSettingsClick = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithTag("userappsettings:success").assertExists()
    }

    @Test
    fun revert_icon_clicked_when_state_is_empty_shows_SnackBar() {
        composeTestRule.setContent {
            val snackbarHostState = remember {
                SnackbarHostState()
            }

            AppSettingsScreen(snackbarHostState = { snackbarHostState },
                              appName = { "Geto" },
                              uIState = { AppSettingsUiState.Empty },
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onUserAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteUserAppSettingsItem = {},
                              onAddUserAppSettingsClick = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithContentDescription("Revert icon").performClick()

        composeTestRule.onNodeWithTag("userappsettings:snackbar").assertExists()
    }

    @Test
    fun launch_icon_clicked_when_state_is_empty_shows_SnackBar() {
        composeTestRule.setContent {
            val snackbarHostState = remember {
                SnackbarHostState()
            }

            AppSettingsScreen(snackbarHostState = { snackbarHostState },
                              appName = { "Geto" },
                              uIState = { AppSettingsUiState.Empty },
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onUserAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteUserAppSettingsItem = {},
                              onAddUserAppSettingsClick = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithContentDescription("Launch icon").performClick()

        composeTestRule.onNodeWithTag("userappsettings:snackbar").assertExists()
    }
}