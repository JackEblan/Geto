package com.feature.userapplist

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.core.testing.data.nonSystemAppsTestData
import org.junit.Rule
import org.junit.Test

class UserAppListScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun state_loading_shows_LoadingPlaceHolderScreen() {
        composeTestRule.setContent {
            UserAppListScreen(uIState = { UserAppListUiState.Loading }, onItemClick = { _, _ -> })
        }

        composeTestRule.onNodeWithTag("userapplist:loading").assertExists()
    }

    @Test
    fun state_showAppList_shows_LazyList() {
        composeTestRule.setContent {
            UserAppListScreen(uIState = { UserAppListUiState.ShowAppList(nonSystemAppsTestData) },
                              onItemClick = { _, _ -> })
        }

        composeTestRule.onNodeWithTag("userapplist:applist").assertExists()
    }
}