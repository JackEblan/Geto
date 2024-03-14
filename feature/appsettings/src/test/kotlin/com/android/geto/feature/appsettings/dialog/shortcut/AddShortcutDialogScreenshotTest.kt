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

package com.android.geto.feature.appsettings.dialog.shortcut

import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.core.designsystem.component.GetoBackground
import com.android.geto.core.designsystem.theme.GetoTheme
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
class AddShortcutDialogScreenshotTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun add_shortcut_dialog_empty() {
        composeTestRule.captureMultiDevice("AddShortcutDialogEmpty") {
            GetoTheme {
                val shortcutDialogState = rememberAddShortcutDialogState()

                shortcutDialogState.updateIcon(icon)

                AddShortcutDialogScreen(shortcutDialogState = shortcutDialogState,
                                        onRefreshShortcut = {},
                                        onAddShortcut = {})
            }
        }
    }

    @Test
    fun add_shortcut_dialog_filled_textfields() {
        composeTestRule.captureMultiDevice("AddShortcutDialogFilledTextFields") {
            GetoTheme {
                val shortcutDialogState = rememberAddShortcutDialogState()

                shortcutDialogState.updateIcon(icon)

                shortcutDialogState.updateShortLabel("Short Label")

                shortcutDialogState.updateLongLabel("Long Label")

                AddShortcutDialogScreen(shortcutDialogState = shortcutDialogState,
                                        onRefreshShortcut = {},
                                        onAddShortcut = {})
            }
        }
    }

    @Test
    fun add_shortcut_dialog_error_textfields() {
        composeTestRule.captureMultiDevice("AddShortcutDialogErrorTextFields") {
            GetoTheme {
                val shortcutDialogState = rememberAddShortcutDialogState()

                shortcutDialogState.updateIcon(icon)

                shortcutDialogState.getShortcut(packageName = "Test")

                AddShortcutDialogScreen(shortcutDialogState = shortcutDialogState,
                                        onRefreshShortcut = {},
                                        onAddShortcut = {})
            }
        }
    }

    @Test
    fun add_shortcut_dialog_empty_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "AddShortcutDialogEmpty",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    val shortcutDialogState = rememberAddShortcutDialogState()

                    shortcutDialogState.updateIcon(icon)

                    AddShortcutDialogScreen(shortcutDialogState = shortcutDialogState,
                                            onRefreshShortcut = {},
                                            onAddShortcut = {})
                }
            }
        }
    }

    @Test
    fun add_shortcut_dialog_filled_textfields_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "AddShortcutDialogFilledTextFields",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    val shortcutDialogState = rememberAddShortcutDialogState()

                    shortcutDialogState.updateIcon(icon)

                    shortcutDialogState.updateShortLabel("Short Label")

                    shortcutDialogState.updateLongLabel("Long Label")

                    AddShortcutDialogScreen(shortcutDialogState = shortcutDialogState,
                                            onRefreshShortcut = {},
                                            onAddShortcut = {})
                }
            }
        }
    }

    @Test
    fun add_shortcut_dialog_error_textfields_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "AddShortcutDialogErrorTextFields",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    val shortcutDialogState = rememberAddShortcutDialogState()

                    shortcutDialogState.updateIcon(icon)

                    shortcutDialogState.getShortcut(packageName = "Test")

                    AddShortcutDialogScreen(shortcutDialogState = shortcutDialogState,
                                            onRefreshShortcut = {},
                                            onAddShortcut = {})
                }
            }
        }
    }
}

private val icon = ShapeDrawable(OvalShape()).apply {
    paint.color = Color.GRAY

    setBounds(0, 0, (50 * 2), (50 * 2))
}