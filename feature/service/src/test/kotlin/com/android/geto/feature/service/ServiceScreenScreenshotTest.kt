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
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.domain.model.UpdateUsageStatsForegroundServiceResult
import com.android.geto.core.screenshottesting.util.DefaultTestDevices
import com.android.geto.core.screenshottesting.util.captureScreenForDevice
import com.android.geto.core.screenshottesting.util.captureScreenForMultiDevice
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
            GetoTheme {
                ServiceScreen(
                    updateUsageStatsForegroundServiceResult = UpdateUsageStatsForegroundServiceResult.Stop,
                    onEvent = {},
                )
            }
        }
    }

    @Test
    fun serviceScreen_active() {
        composeTestRule.captureScreenForMultiDevice("ServiceScreenActive") {
            GetoTheme {
                ServiceScreen(
                    updateUsageStatsForegroundServiceResult = UpdateUsageStatsForegroundServiceResult.Start,
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
            GetoTheme {
                Surface {
                    ServiceScreen(
                        updateUsageStatsForegroundServiceResult = UpdateUsageStatsForegroundServiceResult.Stop,
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
            GetoTheme {
                Surface {
                    ServiceScreen(
                        updateUsageStatsForegroundServiceResult = UpdateUsageStatsForegroundServiceResult.Start,
                        onEvent = {},
                    )
                }
            }
        }
    }
}
