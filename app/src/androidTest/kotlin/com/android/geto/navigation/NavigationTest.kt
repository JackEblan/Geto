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

package com.android.geto.navigation

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.android.geto.MainActivity
import com.android.geto.feature.applist.navigation.APP_LIST_NAVIGATION_ROUTE
import com.android.geto.feature.settings.navigation.SETTINGS_NAVIGATION_ROUTE
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class NavigationTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        composeTestRule.activity.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            GetoNavHost(navController = navController)
        }
    }

    @Test
    fun appListSreen_isDisplayed_whenStarted() {
        composeTestRule.onNodeWithTag("appList").assertIsDisplayed()
    }

    @Test
    fun appSettingsScreen_isDisplayed_whenTargetApplicationInfoItem_isClicked() {
        composeTestRule.onAllNodes(hasTestTag("appList:appItem"))[0].performClick()

        val appSettingsRoute = navController.currentBackStackEntry?.destination?.route

        assertEquals(
            expected = "app_settings_route/{package_name}/{app_name}", actual = appSettingsRoute
        )
    }

    @Test
    fun appListScreen_isDisplayed_whenNavigateBackFromAppSettingsScreen() {
        composeTestRule.onAllNodes(hasTestTag("appList:appItem"))[0].performClick()

        composeTestRule.onNodeWithContentDescription(
            label = "Navigation icon", useUnmergedTree = true
        ).performClick()

        val appListRoute = navController.currentBackStackEntry?.destination?.route

        assertEquals(
            expected = APP_LIST_NAVIGATION_ROUTE, actual = appListRoute
        )
    }

    @Test
    fun settingsScreen_isDisplayed_whenSettingsIcon_isClicked() {
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
