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
package com.android.geto.feature.service

import androidx.activity.ComponentActivity
import androidx.compose.material3.Surface
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.designsystem.theme.GetoTheme
import com.android.geto.domain.model.DarkThemeConfig
import com.android.geto.domain.model.ThemeBrand
import com.android.geto.roborazzi.DefaultTestDevices
import com.android.geto.roborazzi.captureScreenForDevice
import com.android.geto.roborazzi.captureScreenForMultiDevice
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
@Config(application = HiltTestApplication::class)
@LooperMode(LooperMode.Mode.PAUSED)
class ServiceScreenScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun serviceScreen_inActive() {
        composeTestRule.captureScreenForMultiDevice("ServiceScreenInActive") {
            GetoTheme(
                themeBrand = ThemeBrand.GREEN,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                dynamicTheme = false,
            ) {
                ServiceScreen(
                    usageStatsForegroundServiceActive = false,
                    onEvent = {},
                )
            }
        }
    }

    @Test
    fun serviceScreen_active() {
        composeTestRule.captureScreenForMultiDevice("ServiceScreenActive") {
            GetoTheme(
                themeBrand = ThemeBrand.GREEN,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                dynamicTheme = false,
            ) {
                ServiceScreen(
                    usageStatsForegroundServiceActive = true,
                    onEvent = {},
                )
            }
        }
    }

    @Test
    fun serviceScreen_inActive_dark() {
        composeTestRule.captureScreenForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            fileName = "ServiceScreenInActive",
            darkMode = true,
        ) {
            GetoTheme(
                themeBrand = ThemeBrand.GREEN,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                dynamicTheme = false,
            ) {
                Surface {
                    ServiceScreen(
                        usageStatsForegroundServiceActive = false,
                        onEvent = {},
                    )
                }
            }
        }
    }

    @Test
    fun serviceScreen_active_dark() {
        composeTestRule.captureScreenForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            fileName = "ServiceScreenActive",
            darkMode = true,
        ) {
            GetoTheme(
                themeBrand = ThemeBrand.GREEN,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                dynamicTheme = false,
            ) {
                Surface {
                    ServiceScreen(
                        usageStatsForegroundServiceActive = true,
                        onEvent = {},
                    )
                }
            }
        }
    }
}
