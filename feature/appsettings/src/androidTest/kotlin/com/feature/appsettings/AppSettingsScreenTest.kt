package com.feature.appsettings

import androidx.activity.ComponentActivity
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.core.model.AppSettings
import com.core.model.SettingsType
import com.core.ui.rememberAddSettingsDialogState
import org.junit.Rule
import org.junit.Test

class AppSettingsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loadingPlaceHolderScreenIsDisplayed_whenAppSettingsUiStateIsLoading() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Loading,
                              addSettingsDialogState = addSettingsDialogState,
                              showCopyPermissionCommandDialog = false,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {},
                              scrollState = scrollState,
                              onAddSettings = {},
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithTag("appsettings:loadingPlaceHolderScreen").assertIsDisplayed()
    }

    @Test
    fun emptyListPlaceHolderScreenIsDisplayed_whenAppSettingsUiStateIsEmpty() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Empty,
                              addSettingsDialogState = addSettingsDialogState,
                              showCopyPermissionCommandDialog = false,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {},
                              scrollState = scrollState,
                              onAddSettings = {},
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithTag("appsettings:emptyListPlaceHolderScreen").assertIsDisplayed()
    }

    @Test
    fun lazyColumnIsDisplayed_whenAppSettingsUiStateIsSuccess() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Success(testAppSettingsList),
                              addSettingsDialogState = addSettingsDialogState,
                              showCopyPermissionCommandDialog = false,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {},
                              scrollState = scrollState,
                              onAddSettings = {},
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithTag("appsettings:lazyColumn").assertIsDisplayed()
    }

    @Test
    fun snackbarExists_whenRevertIconIsClicked_appSettingsUiStateIsEmpty() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(
                snackbarHostState = SnackbarHostState(),
                appName = "Geto",
                packageName = "Geto",
                appSettingsUiState = AppSettingsUiState.Success(testAppSettingsList),
                addSettingsDialogState = addSettingsDialogState,
                              showCopyPermissionCommandDialog = false,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {},
                              scrollState = scrollState,
                              onAddSettings = {},
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithContentDescription("Revert icon").performClick()

        composeTestRule.onNodeWithTag("appsettings:snackbar").assertExists()
    }

    @Test
    fun snackbarExists_whenLaunchIconIsClicked_appSettingsUiStateIsEmpty() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(
                snackbarHostState = SnackbarHostState(),
                appName = "Geto",
                packageName = "Geto",
                appSettingsUiState = AppSettingsUiState.Empty,
                addSettingsDialogState = addSettingsDialogState,
                              showCopyPermissionCommandDialog = false,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {},
                              scrollState = scrollState,
                              onAddSettings = {},
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithContentDescription("Launch icon").performClick()

        composeTestRule.onNodeWithTag("appsettings:snackbar").assertExists()
    }

    @Test
    fun addSettingsDialogIsDisplayed_whenAddIconIsClicked() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Empty,
                              addSettingsDialogState = addSettingsDialogState,
                              showCopyPermissionCommandDialog = false,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {},
                              scrollState = scrollState,
                              onAddSettings = {
                                  addSettingsDialogState.updateShowDialog(true)
                              },
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithContentDescription("Add icon").performClick()

        composeTestRule.onNodeWithTag("addSettingsDialog").assertIsDisplayed()
    }

    @Test
    fun copyPermissionCommandDialogIsDisplayed_whenShowCopyPermissionCommandDialogIsTrue() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(
                snackbarHostState = SnackbarHostState(),
                appName = "Geto",
                packageName = "Geto",
                appSettingsUiState = AppSettingsUiState.Empty,
                addSettingsDialogState = addSettingsDialogState,
                              showCopyPermissionCommandDialog = true,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onLaunchApp = {},
                              scrollState = scrollState,
                              onAddSettings = {},
                              onCopyPermissionCommand = {},
                              onDismissRequestCopyPermissionCommand = {})
        }

        composeTestRule.onNodeWithTag("copyPermissionCommandDialog").assertIsDisplayed()
    }
}

private val testAppSettingsList = listOf(
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