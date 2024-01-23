package com.feature.appsettings

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
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
    fun testShowsLoadingPlaceHolderScreen_AppSettingsUiStateLoading() {
        composeTestRule.setContent {
            AppSettingsScreen(snackbarHostState = { SnackbarHostState() },
                              appNameProvider = { "Geto" },
                              appSettingsUiStateProvider = { AppSettingsUiState.Loading },
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onAddAppSettingsClick = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithTag("userappsettings:loading").assertExists()
    }

    @Test
    fun testShowsEmptyListPlaceHolderScreen_AppSettingsUiStateEmpty() {
        composeTestRule.setContent {
            AppSettingsScreen(snackbarHostState = { SnackbarHostState() },
                              appNameProvider = { "Geto" },
                              appSettingsUiStateProvider = { AppSettingsUiState.Empty },
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onAddAppSettingsClick = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithTag("userappsettings:empty").assertExists()
    }

    @Test
    fun testShowsLazyColumn_AppSettingsUiStateSuccess() {
        composeTestRule.setContent {
            AppSettingsScreen(snackbarHostState = { SnackbarHostState() },
                              appNameProvider = { "Geto" },
                              appSettingsUiStateProvider = {
                                  AppSettingsUiState.Success(
                                      listOf(
                                          AppSettings(
                                              id = 0,
                                              enabled = true,
                                              settingsType = SettingsType.SYSTEM,
                                              packageName = "packageNameTest",
                                              label = "system",
                                              key = "key",
                                              valueOnLaunch = "test",
                                              valueOnRevert = "test",
                                              safeToWrite = true
                                          )
                                      )
                                  )
                              },
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onAddAppSettingsClick = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithTag("userappsettings:success").assertExists()
    }

    @Test
    fun testShowsSnackbar_whenRevertIconClicked_inAppSettingsUiStateEmpty() {
        composeTestRule.setContent {
            val snackbarHostState = remember {
                SnackbarHostState()
            }

            AppSettingsScreen(snackbarHostState = { snackbarHostState },
                              appNameProvider = { "Geto" },
                              appSettingsUiStateProvider = { AppSettingsUiState.Empty },
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onAddAppSettingsClick = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithContentDescription("Revert icon").performClick()

        composeTestRule.onNodeWithTag("userappsettings:snackbar").assertExists()
    }

    @Test
    fun testShowsSnackbar_whenLaunchIconClicked_inAppSettingsUiStateEmpty() {
        composeTestRule.setContent {
            val snackbarHostState = remember {
                SnackbarHostState()
            }

            AppSettingsScreen(snackbarHostState = { snackbarHostState },
                              appNameProvider = { "Geto" },
                              appSettingsUiStateProvider = { AppSettingsUiState.Empty },
                              onNavigationIconClick = {},
                              onRevertSettingsIconClick = {},
                              onAppSettingsItemCheckBoxChange = { _, _ -> },
                              onDeleteAppSettingsItem = {},
                              onAddAppSettingsClick = {},
                              onLaunchApp = {})
        }

        composeTestRule.onNodeWithContentDescription("Launch icon").performClick()

        composeTestRule.onNodeWithTag("userappsettings:snackbar").assertExists()
    }
}