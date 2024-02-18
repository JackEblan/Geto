package com.feature.appsettings

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.core.model.AppSettings
import com.core.model.SettingsType
import org.junit.Rule
import org.junit.Test

class AppSettingsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loadingPlaceHolderScreen_whenAppSettingsUiStateIsLoading_isDisplayed() {
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

        composeTestRule.onNodeWithTag("appsettings:loadingPlaceHolderScreen").assertIsDisplayed()
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

        composeTestRule.onNodeWithTag("appsettings:emptyListPlaceHolderScreen").assertIsDisplayed()
    }

    @Test
    fun lazyColumn_whenAppSettingsUiStateIsSuccess_isDisplayed() {
        composeTestRule.setContent {
            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Success(appSettingsList),
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onAddAppSettingsClick = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithTag("appsettings:lazyColumn").assertIsDisplayed()
    }

    @Test
    fun snackbar_whenRevertIconClicked_inAppSettingsUiStateEmpty_exists() {
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
    fun snackbar_whenLaunchIconClicked_inAppSettingsUiStateEmpty_exists() {
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

private val appSettingsList = listOf(
    AppSettings(
        id = 0,
        enabled = true,
        settingsType = SettingsType.SYSTEM,
        packageName = "packageName0",
        label = "label0",
        key = "key0",
        valueOnLaunch = "valueOnLaunch0",
        valueOnRevert = "valueOnRevert0",
        safeToWrite = true
    ), AppSettings(
        id = 1,
        enabled = true,
        settingsType = SettingsType.SYSTEM,
        packageName = "packageName1",
        label = "label1",
        key = "key1",
        valueOnLaunch = "valueOnLaunch1",
        valueOnRevert = "valueOnRevert1",
        safeToWrite = true
    )
)