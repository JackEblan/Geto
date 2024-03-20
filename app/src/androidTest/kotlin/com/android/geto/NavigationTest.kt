/*
 *
 *   Copyright 2023 Einstein Blanco
 *
 *   Licensed under the GNU General Public License v3.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.gnu.org/licenses/gpl-3.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.android.geto

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.android.geto.core.testing.resources.TestResourcesWrapper
import com.android.geto.feature.applist.navigation.APP_LIST_NAVIGATION_ROUTE
import com.android.geto.feature.settings.navigation.SETTINGS_NAVIGATION_ROUTE
import com.android.geto.navigation.GetoNavHost
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

    private lateinit var resourcesWrapper: TestResourcesWrapper

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        hiltRule.inject()

        resourcesWrapper = TestResourcesWrapper()

        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            GetoNavHost(navController = navController, resourcesWrapper = resourcesWrapper)

        }
    }

    @Test
    fun appListSreenIsDisplayed_whenStarted() {
        composeTestRule.onNodeWithTag("applist").assertIsDisplayed()
    }

    @Test
    fun appSettingsScreenIsDisplayed_whenTargetApplicationInfoItemIsClicked() {
        composeTestRule.onNodeWithText("Label 0").performClick()

        val appSettingsRoute = navController.currentBackStackEntry?.destination?.route

        assertEquals(
            expected = "app_settings_route/{package_name}/{app_name}", actual = appSettingsRoute
        )

        composeTestRule.onNodeWithTag("appsettings:topAppBar").assertIsDisplayed()
    }

    @Test
    fun appListScreenIsDisplayed_whenNavigateBackFromAppSettingsScreen() {
        composeTestRule.onNodeWithText("Label 0").performClick()

        composeTestRule.onNodeWithContentDescription(
            label = "Navigation icon", useUnmergedTree = true
        ).performClick()

        val appListRoute = navController.currentBackStackEntry?.destination?.route

        assertEquals(
            expected = APP_LIST_NAVIGATION_ROUTE, actual = appListRoute
        )
    }

    @Test
    fun settingsScreenIsDisplayed_whenSettingsIconIsClicked() {
        composeTestRule.onNodeWithContentDescription("Settings icon").performClick()

        val settingsRoute = navController.currentBackStackEntry?.destination?.route

        assertEquals(
            expected = SETTINGS_NAVIGATION_ROUTE, actual = settingsRoute
        )
    }

    @Test
    fun appListScreenIsDisplayed_whenNavigateBackFromSettingsScreen() {
        composeTestRule.onNodeWithContentDescription("Settings icon").performClick()

        composeTestRule.onNodeWithContentDescription(
            label = "Navigation icon", useUnmergedTree = true
        ).performClick()

        val appListRoute = navController.currentBackStackEntry?.destination?.route

        assertEquals(
            expected = APP_LIST_NAVIGATION_ROUTE, actual = appListRoute
        )
    }
}
