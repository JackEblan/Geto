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

package com.android.geto.feature.settings.dialog.theme

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.core.designsystem.component.GetoBackground
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.screenshot.testing.util.DefaultTestDevices
import com.android.geto.core.screenshot.testing.util.captureForDevice
import com.android.geto.core.screenshot.testing.util.captureMultiDevice
import org.junit.Rule
import kotlin.test.Test

class ThemeDialogScreenshotTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun theme_dialog() {
        composeTestRule.captureMultiDevice("ThemeDialog") {
            GetoTheme {
                val themeDialogState = rememberThemeDialogState()

                ThemeDialog(
                    themeDialogState = themeDialogState,
                    onChangeTheme = {},
                    contentDescription = "ThemeDialog"
                )
            }
        }
    }

    @Test
    fun theme_dialog_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "ThemeDialog",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    val themeDialogState = rememberThemeDialogState()

                    ThemeDialog(
                        themeDialogState = themeDialogState,
                        onChangeTheme = {},
                        contentDescription = "ThemeDialog"
                    )
                }
            }
        }
    }
}