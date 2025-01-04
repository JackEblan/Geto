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
package com.android.geto.feature.settings.dialog.dark

import androidx.activity.ComponentActivity
import androidx.compose.material3.Surface
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.designsystem.theme.GetoTheme
import com.android.geto.roborazzi.DefaultTestDevices
import com.android.geto.roborazzi.captureDialogForDevice
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@LooperMode(LooperMode.Mode.PAUSED)
class DarkDialogScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun darkDialog() {
        composeTestRule.captureDialogForDevice(
            fileName = "DarkDialog",
            deviceName = "foldable",
            deviceSpec = DefaultTestDevices.FOLDABLE.spec,
        ) {
            GetoTheme {
                DarkDialog(
                    onDismissRequest = {},
                    selected = 0,
                    onSelect = {},
                    onCancelClick = {},
                    onChangeClick = {},
                    contentDescription = "",
                )
            }
        }
    }

    @Test
    fun darkDialog_dark() {
        composeTestRule.captureDialogForDevice(
            fileName = "DarkDialog",
            deviceName = "foldable_dark",
            deviceSpec = DefaultTestDevices.FOLDABLE.spec,
            darkMode = true,
        ) {
            GetoTheme {
                Surface {
                    DarkDialog(
                        onDismissRequest = {},
                        selected = 0,
                        onSelect = {},
                        onCancelClick = {},
                        onChangeClick = {},
                        contentDescription = "",
                    )
                }
            }
        }
    }
}
