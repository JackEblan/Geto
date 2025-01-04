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
package com.android.geto.feature.apps

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import com.android.geto.core.domain.model.GetoApplicationInfo
import org.junit.Rule
import kotlin.test.Test

class AppsScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun getoLoadingWheel_isDisplayed_whenAppsUiState_isLoading() {
        composeTestRule.setContent {
            AppsScreen(
                appsUiState = AppsUiState.Loading,
                onItemClick = { _, _ -> },
            )
        }

        composeTestRule.onNodeWithContentDescription("GetoLoadingWheel").assertIsDisplayed()
    }

    @Test
    fun lazyColumn_isDisplayed_whenAppsUiState_isSuccess() {
        val mappedGetoApplicationInfos = List(2) { index ->
            GetoApplicationInfo(
                flags = 0,
                icon = ByteArray(0),
                packageName = "com.android.geto$index",
                label = "Geto $index",
            )
        }

        composeTestRule.setContent {
            AppsScreen(
                appsUiState = AppsUiState.Success(mappedGetoApplicationInfos),
                onItemClick = { _, _ -> },
            )
        }

        composeTestRule.onNodeWithTag("apps:lazyVerticalGrid").assertIsDisplayed()
    }
}
