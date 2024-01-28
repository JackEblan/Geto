package com.feature.applist

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

class AppListScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loadingPlaceHolderScreen_whenAppListUiStateIsLoading_showLoading() {
        composeTestRule.setContent {
            AppListScreen(appListUiState = AppListUiState.Loading,
                          onItemClick = { _, _ -> },
                          onSecureSettingsClick = {})
        }

        composeTestRule.onNodeWithTag("applist:loadingPlaceHolderScreen").assertExists()
    }
}