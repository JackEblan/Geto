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

import androidx.annotation.StringRes
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isSelectable
import androidx.compose.ui.test.isSelected
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.android.geto.MainActivity
import com.android.geto.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.properties.ReadOnlyProperty

@HiltAndroidTest
class NavigationTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private fun AndroidComposeTestRule<*, *>.stringResource(@StringRes resId: Int) =
        ReadOnlyProperty<Any, String> { _, _ -> activity.getString(resId) }

    private val apps by composeTestRule.stringResource(R.string.apps)
    private val settings by composeTestRule.stringResource(R.string.settings)

    @Before
    fun setup() = hiltRule.inject()

    @Test
    fun appsScreen_isSelected() {
        composeTestRule.apply {
            onNode(isSelected()).assertTextEquals(apps)
        }
    }

    @Test
    fun settingsScreen_isSelected() {
        composeTestRule.apply {
            onNode(isSelectable() and hasText(settings)).performClick()

            onNode(isSelected()).assertTextEquals(settings)
        }
    }

    @Test
    fun appSettingsScreen_isDisplayed_whenGetoApplicationInfoItem_isClicked() {
        composeTestRule.apply {
            onAllNodes(hasTestTag("apps:appItem"))[0].performClick()

            onNodeWithTag("appSettings:topAppBar").assertTextEquals("Geto 0")
        }
    }

    @Test
    fun appsScreen_isDisplayed_whenNavigateUp_fromAppSettingsScreen() {
        composeTestRule.apply {
            onAllNodes(hasTestTag("apps:appItem"))[0].performClick()

            onNodeWithContentDescription(
                label = "Navigation icon",
                useUnmergedTree = true,
            ).performClick()

            onNode(isSelected()).assertTextEquals(apps)
        }
    }
}
