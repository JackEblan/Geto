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
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(application = HiltTestApplication::class)
@LooperMode(LooperMode.Mode.PAUSED)
class AppsScreenScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val context = RuntimeEnvironment.getApplication()

    private val installedApplications = List(5) { index ->
        TargetApplicationInfo(
            flags = 0,
            icon = context.getDrawable(R.drawable.baseline_android_24),
            packageName = "com.android.geto$index",
            label = "Geto $index",
        )
    }

    @Test
    fun appsScreen_populated() {
        composeTestRule.captureMultiDevice("AppsScreenPopulated") {
            GetoTheme {
                AppsScreen(
                    appsUiState = AppsUiState.Success(installedApplications),
                    onItemClick = { _, _ -> },
                    onSettingsClick = {},
                )
            }
        }
    }

    @Test
    fun appsScreen_loading() {
        composeTestRule.captureMultiDevice("AppsScreenLoading") {
            GetoTheme {
                AppsScreen(
                    appsUiState = AppsUiState.Loading,
                    onItemClick = { _, _ -> },
                    onSettingsClick = {},
                )
            }
        }
    }

    @Test
    fun appsScreen_populated_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            fileName = "AppsScreenPopulated",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AppsScreen(
                        appsUiState = AppsUiState.Success(
                            installedApplications,
                        ),
                        onItemClick = { _, _ -> },
                        onSettingsClick = {},
                    )
                }
            }
        }
    }

    @Test
    fun appsScreen_loading_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            fileName = "AppsScreenLoading",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AppsScreen(
                        appsUiState = AppsUiState.Loading,
                        onItemClick = { _, _ -> },
                        onSettingsClick = {},
                    )
                }
            }
        }
    }
}
