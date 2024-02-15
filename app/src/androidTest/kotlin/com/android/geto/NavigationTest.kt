package com.android.geto

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.android.geto.navigation.GetoNavHost
import com.feature.securesettingslist.navigation.SECURE_SETTINGS_LIST_NAVIGATION_ROUTE
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class NavigationTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        hiltRule.inject()

        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            GetoNavHost(navController = navController,
                        onOpenAddSettingsDialog = {},
                        onOpenCopyPermissionCommandDialog = {})

        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        composeTestRule.onNodeWithTag("applist").assertIsDisplayed()
    }

    @Test
    fun navHost_clickSettingsIcon_navigateToSecureSettingsScreen() {
        composeTestRule.onNodeWithContentDescription(label = "Secure settings icon").performClick()

        val route = navController.currentDestination?.route

        assertEquals(expected = SECURE_SETTINGS_LIST_NAVIGATION_ROUTE, actual = route)
    }

    @Test
    fun navHost_clickAnAppItem_navigateToAppSettingsScreen() {/*
        I don't know this part.
        Have no idea how to make a test for lazy list,
        click it and pass the required arguments
        */
    }
}