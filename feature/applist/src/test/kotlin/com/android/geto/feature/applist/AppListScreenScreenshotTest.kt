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

package com.android.geto.feature.applist

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.core.designsystem.component.GetoBackground
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.model.TargetApplicationInfo
import com.android.geto.core.screenshot.testing.util.DefaultTestDevices
import com.android.geto.core.screenshot.testing.util.captureForDevice
import com.android.geto.core.screenshot.testing.util.captureMultiDevice
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode
import kotlin.test.Test


@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(application = HiltTestApplication::class, qualifiers = "480dpi")
@LooperMode(LooperMode.Mode.PAUSED)
class AppListScreenScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun app_list_populated() {
        composeTestRule.captureMultiDevice("AppListPopulated") {
            GetoTheme {
                AppListScreen(appListUiState = AppListUiState.Success(testTargetApplicationInfoLists),
                              onItemClick = { _, _ -> },
                              onSettingsClick = {})
            }
        }
    }

    @Test
    fun app_list_loading() {
        composeTestRule.captureMultiDevice("AppListLoading") {
            GetoTheme {
                AppListScreen(appListUiState = AppListUiState.Loading,
                              onItemClick = { _, _ -> },
                              onSettingsClick = {})
            }
        }
    }

    @Test
    fun app_list_populated_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "AppListPopulated",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AppListScreen(appListUiState = AppListUiState.Success(
                        testTargetApplicationInfoLists
                    ), onItemClick = { _, _ -> }, onSettingsClick = {})
                }
            }
        }
    }

    @Test
    fun app_list_loading_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "AppListLoading",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AppListScreen(appListUiState = AppListUiState.Loading,
                                  onItemClick = { _, _ -> },
                                  onSettingsClick = {})
                }
            }
        }
    }
}

private val testTargetApplicationInfoLists = List(2) { index ->
    val icon = ShapeDrawable(OvalShape()).apply {
        paint.color = Color.GRAY

        setBounds(0, 0, (50 * 2), (50 * 2))
    }

    TargetApplicationInfo(
        flags = 0, icon = icon, packageName = "packageName$index", label = "Label $index"
    )
}