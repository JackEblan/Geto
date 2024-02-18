package com.feature.applist

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.core.model.NonSystemApp
import org.junit.Rule
import org.junit.Test

class AppListScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loadingPlaceHolderScreen_whenAppListUiStateIsLoading_isDisplayed() {
        composeTestRule.setContent {
            AppListScreen(appListUiState = AppListUiState.Loading,
                          onItemClick = { _, _ -> },
                          onSecureSettingsClick = {})
        }

        composeTestRule.onNodeWithTag("applist:loadingPlaceHolderScreen").assertIsDisplayed()
    }

    @Test
    fun lazyColumn_whenAppListUiStateIsSuccess_isDisplayed() {
        composeTestRule.setContent {
            AppListScreen(appListUiState = AppListUiState.Success(testNonSystemAppList),
                          onItemClick = { _, _ -> },
                          onSecureSettingsClick = {})
        }

        composeTestRule.onNodeWithTag("applist:lazyColumn").assertIsDisplayed()
    }
}

private val testNonSystemAppList = listOf(
    NonSystemApp(
        icon = null, packageName = "packageName0", label = "label0"
    ), NonSystemApp(
        icon = null, packageName = "packageName1", label = "label1"
    )
)