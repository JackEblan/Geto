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
package com.android.geto.core.designsystem

import androidx.activity.ComponentActivity
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.core.designsystem.component.GetoDropdownMenuItem
import com.android.geto.core.designsystem.component.GetoExposedDropdownMenu
import com.android.geto.core.designsystem.component.GetoExposedDropdownMenuBox
import com.android.geto.core.screenshot.testing.util.captureMultiTheme
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(application = HiltTestApplication::class, qualifiers = "480dpi")
@LooperMode(LooperMode.Mode.PAUSED)
class ExposedDropdownMenuScreenshotTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun getoExposedDropdownMenu_multipleThemes() {
        composeTestRule.captureMultiTheme("GetoExposedDropdownMenu") {
            Surface {
                GetoExposedDropdownMenuBox(
                    expanded = true,
                    onExpandedChange = {},
                    content = {
                        GetoExposedDropdownMenu(
                            expanded = true,
                            onDismissRequest = {},
                            content = {},
                        )
                    },
                )
            }
        }
    }

    @Test
    fun getoExposedDropdownMenuItem_multipleThemes() {
        composeTestRule.captureMultiTheme("GetoExposedDropdownMenuItem") {
            Surface {
                GetoDropdownMenuItem(
                    text = {},
                    onClick = {},
                )
            }
        }
    }
}
