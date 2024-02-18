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
import com.feature.appsettings.dialog.addsettings.AddSettingsDialogUiState
import com.feature.appsettings.dialog.addsettings.rememberAddSettingsDialogState
import com.feature.appsettings.dialog.copypermissioncommand.CopyPermissionCommandUiState
import org.junit.Rule
import org.junit.Test

class AppSettingsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loadingPlaceHolderScreen_whenAppSettingsUiStateIsLoading_isDisplayed() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Loading,
                              addSettingsDialogUiState = AddSettingsDialogUiState.HideAddSettingsDialog,
                              copyPermissionCommandDialogUiState = CopyPermissionCommandUiState.HideCopyPermissionCommandDialog,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onAddAppSettingsClick = {},
                              onLaunchApp = {},
                              addSettingsDialogState = addSettingsDialogState,
                              scrollState = scrollState,
                              onDismissRequestAddSettings = {},
                              onDismissRequestCopyPermissionCommand = {},

                              onAddSettings = {},
                              onShowCopyPermissionCommandDialog = {})
        }

        composeTestRule.onNodeWithTag("appsettings:loadingPlaceHolderScreen").assertIsDisplayed()
    }

    @Test
    fun emptyListPlaceHolderScreen_whenAppSettingsUiStateIsEmpty_showEmptyScreen() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Empty,
                              addSettingsDialogUiState = AddSettingsDialogUiState.HideAddSettingsDialog,
                              copyPermissionCommandDialogUiState = CopyPermissionCommandUiState.HideCopyPermissionCommandDialog,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onAddAppSettingsClick = {},
                              onLaunchApp = {},
                              addSettingsDialogState = addSettingsDialogState,
                              scrollState = scrollState,
                              onDismissRequestAddSettings = {},
                              onDismissRequestCopyPermissionCommand = {},
                              onAddSettings = {},
                              onShowCopyPermissionCommandDialog = {})
        }

        composeTestRule.onNodeWithTag("appsettings:emptyListPlaceHolderScreen").assertIsDisplayed()
    }

    @Test
    fun lazyColumn_whenAppSettingsUiStateIsSuccess_isDisplayed() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Success(testAppSettingsList),
                              addSettingsDialogUiState = AddSettingsDialogUiState.HideAddSettingsDialog,
                              copyPermissionCommandDialogUiState = CopyPermissionCommandUiState.HideCopyPermissionCommandDialog,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onAddAppSettingsClick = {},
                              onLaunchApp = {},
                              addSettingsDialogState = addSettingsDialogState,
                              scrollState = scrollState,
                              onDismissRequestAddSettings = {},
                              onDismissRequestCopyPermissionCommand = {},
                              onAddSettings = {},
                              onShowCopyPermissionCommandDialog = {})
        }

        composeTestRule.onNodeWithTag("appsettings:lazyColumn").assertIsDisplayed()
    }

    @Test
    fun snackbar_whenRevertIconClicked_inAppSettingsUiStateEmpty_exists() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Success(testAppSettingsList),
                              addSettingsDialogUiState = AddSettingsDialogUiState.HideAddSettingsDialog,
                              copyPermissionCommandDialogUiState = CopyPermissionCommandUiState.HideCopyPermissionCommandDialog,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onAddAppSettingsClick = {},
                              onLaunchApp = {},
                              addSettingsDialogState = addSettingsDialogState,
                              scrollState = scrollState,
                              onDismissRequestAddSettings = {},
                              onDismissRequestCopyPermissionCommand = {},
                              onAddSettings = {},
                              onShowCopyPermissionCommandDialog = {})
        }

        composeTestRule.onNodeWithContentDescription("Revert icon").performClick()

        composeTestRule.onNodeWithTag("appsettings:snackbar").assertExists()
    }

    @Test
    fun snackbar_whenLaunchIconClicked_inAppSettingsUiStateEmpty_exists() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Empty,
                              addSettingsDialogUiState = AddSettingsDialogUiState.HideAddSettingsDialog,
                              copyPermissionCommandDialogUiState = CopyPermissionCommandUiState.HideCopyPermissionCommandDialog,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onAddAppSettingsClick = {},
                              onLaunchApp = {},
                              addSettingsDialogState = addSettingsDialogState,
                              scrollState = scrollState,
                              onDismissRequestAddSettings = {},
                              onDismissRequestCopyPermissionCommand = {},
                              onAddSettings = {},
                              onShowCopyPermissionCommandDialog = {})
        }

        composeTestRule.onNodeWithContentDescription("Launch icon").performClick()

        composeTestRule.onNodeWithTag("appsettings:snackbar").assertExists()
    }

    @Test
    fun addSettingsDialog_whenAddIconIsClicked_isDisplayed() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Empty,
                              addSettingsDialogUiState = AddSettingsDialogUiState.ShowAddSettingsDialog,
                              copyPermissionCommandDialogUiState = CopyPermissionCommandUiState.HideCopyPermissionCommandDialog,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onAddAppSettingsClick = {},
                              onLaunchApp = {},
                              addSettingsDialogState = addSettingsDialogState,
                              scrollState = scrollState,
                              onDismissRequestAddSettings = {},
                              onDismissRequestCopyPermissionCommand = {},
                              onAddSettings = {},
                              onShowCopyPermissionCommandDialog = {})
        }

        composeTestRule.onNodeWithContentDescription("Add icon").performClick()

        composeTestRule.onNodeWithTag("addSettingsDialog").assertIsDisplayed()
    }

    @Test
    fun copyPermissionCommandDialog_copyPermissionCommandDialogUiStateIsShow_isDisplayed() {
        composeTestRule.setContent {
            val addSettingsDialogState = rememberAddSettingsDialogState()

            val scrollState = rememberScrollState()

            AppSettingsScreen(snackbarHostState = SnackbarHostState(),
                              appName = "Geto",
                              packageName = "Geto",
                              appSettingsUiState = AppSettingsUiState.Empty,
                              addSettingsDialogUiState = AddSettingsDialogUiState.ShowAddSettingsDialog,
                              copyPermissionCommandDialogUiState = CopyPermissionCommandUiState.ShowCopyPermissionCommandDialog,
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onAddAppSettingsClick = {},
                              onLaunchApp = {},
                              addSettingsDialogState = addSettingsDialogState,
                              scrollState = scrollState,
                              onDismissRequestAddSettings = {},
                              onDismissRequestCopyPermissionCommand = {},
                              onAddSettings = {},
                              onShowCopyPermissionCommandDialog = {})
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