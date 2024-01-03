package com.feature.applist

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.core.testing.data.nonSystemAppsTestData
import org.junit.Rule
import org.junit.Test

class AppListScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun state_loading_shows_LoadingPlaceHolderScreen() {
        composeTestRule.setContent {
            AppListScreen(
                uIState = { AppListUiState.Loading },
                onItemClick = { _, _ -> },
                onSecureSettingsClick = {})
        }

        composeTestRule.onNodeWithTag("userapplist:loading").assertExists()
    }

    @Test
    fun state_success_shows_LazyList() {
        composeTestRule.setContent {
            AppListScreen(uIState = { AppListUiState.Success(nonSystemAppsTestData) },
                          onItemClick = { _, _ -> },
                          onSecureSettingsClick = {})
        }

        composeTestRule.onNodeWithTag("userapplist:applist").assertExists()
    }
}