package com.feature.applist

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.core.model.NonSystemApp
import org.junit.Rule
import org.junit.Test

class AppListScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testShowsLoadingPlaceHolderScreen_AppListUiStateLoading() {
        composeTestRule.setContent {
            AppListScreen(uIState = { AppListUiState.Loading },
                          onItemClick = { _, _ -> },
                          onSecureSettingsClick = {})
        }

        composeTestRule.onNodeWithTag("userapplist:loading").assertExists()
    }

    @Test
    fun testShowsLazyColumn_AppListUiStateSuccess() {
        composeTestRule.setContent {
            AppListScreen(uIState = { AppListUiState.Success(nonSystemAppsTestData) },
                          onItemClick = { _, _ -> },
                          onSecureSettingsClick = {})
        }

        composeTestRule.onNodeWithTag("userapplist:applist").assertExists()
    }
}

private val nonSystemAppsTestData = listOf(
    NonSystemApp(packageName = "com.android.geto", label = "Geto"),
    NonSystemApp(packageName = "com.android.geto1", label = "Geto1"),
    NonSystemApp(packageName = "com.android.geto2", label = "Geto2")
)