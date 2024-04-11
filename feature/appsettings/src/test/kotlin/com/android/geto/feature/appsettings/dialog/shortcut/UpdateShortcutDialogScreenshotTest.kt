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

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.core.designsystem.component.GetoBackground
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.screenshot.testing.util.DefaultTestDevices
import com.android.geto.core.screenshot.testing.util.captureScreenRoboImageForDevice
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode
import kotlin.test.Test

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(application = HiltTestApplication::class)
@LooperMode(LooperMode.Mode.PAUSED)
class UpdateShortcutDialogScreenshotTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var shortcutDialogState: ShortcutDialogState

    @Before
    fun setUp() {
        shortcutDialogState = ShortcutDialogState()

        shortcutDialogState.setStringResources(
            shortLabelIsBlank = "Short label is blank",
            longLabelIsBlank = "Long label is blank",
        )
    }

    @Test
    fun updateShortcutDialog_empty() {
        composeTestRule.captureScreenRoboImageForDevice(
            path = "UpdateShortcutDialog/UpdateShortcutDialogEmpty",
            deviceName = "tablet",
            deviceSpec = DefaultTestDevices.TABLET.spec,
        ) {
            GetoTheme {
                UpdateShortcutDialog(
                    shortcutDialogState = shortcutDialogState,
                    onUpdateShortcut = {},
                    contentDescription = "UpdateShortcutDialog",
                )
            }
        }
    }

    @Test
    fun updateShortcutDialog_filled_textfields() {
        shortcutDialogState.updateShortLabel("Geto")

        shortcutDialogState.updateLongLabel("Geto")

        composeTestRule.captureScreenRoboImageForDevice(
            path = "UpdateShortcutDialog/UpdateShortcutDialogFilledTextFields",
            deviceName = "tablet",
            deviceSpec = DefaultTestDevices.TABLET.spec,
        ) {
            GetoTheme {
                UpdateShortcutDialog(
                    shortcutDialogState = shortcutDialogState,
                    onUpdateShortcut = {},
                    contentDescription = "UpdateShortcutDialog",
                )
            }
        }
    }

    @Test
    fun updateShortcutDialog_error_textfields() {
        shortcutDialogState.getShortcut(packageName = "Geto", shortcutIntent = Intent())

        composeTestRule.captureScreenRoboImageForDevice(
            path = "UpdateShortcutDialog/UpdateShortcutDialogErrorTextFields",
            deviceName = "tablet",
            deviceSpec = DefaultTestDevices.TABLET.spec,
        ) {
            GetoTheme {
                UpdateShortcutDialog(
                    shortcutDialogState = shortcutDialogState,
                    onUpdateShortcut = {},
                    contentDescription = "UpdateShortcutDialog",
                )
            }
        }
    }

    @Test
    fun updateShortcutDialog_empty_dark() {
        composeTestRule.captureScreenRoboImageForDevice(
            path = "UpdateShortcutDialog/UpdateShortcutDialogEmpty",
            deviceName = "tablet_dark",
            deviceSpec = DefaultTestDevices.TABLET.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    UpdateShortcutDialog(
                        shortcutDialogState = shortcutDialogState,
                        onUpdateShortcut = {},
                        contentDescription = "UpdateShortcutDialog",
                    )
                }
            }
        }
    }

    @Test
    fun updateShortcutDialog_filled_textfields_dark() {
        shortcutDialogState.updateShortLabel("Geto")

        shortcutDialogState.updateLongLabel("Geto")

        composeTestRule.captureScreenRoboImageForDevice(
            path = "UpdateShortcutDialog/UpdateShortcutDialogFilledTextFields",
            deviceName = "tablet_dark",
            deviceSpec = DefaultTestDevices.TABLET.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    UpdateShortcutDialog(
                        shortcutDialogState = shortcutDialogState,
                        onUpdateShortcut = {},
                        contentDescription = "UpdateShortcutDialog",
                    )
                }
            }
        }
    }

    @Test
    fun updateShortcutDialog_error_textfields_dark() {
        shortcutDialogState.getShortcut(packageName = "Geto", shortcutIntent = Intent())

        composeTestRule.captureScreenRoboImageForDevice(
            path = "UpdateShortcutDialog/UpdateShortcutDialogErrorTextFields",
            deviceName = "tablet_dark",
            deviceSpec = DefaultTestDevices.TABLET.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    UpdateShortcutDialog(
                        shortcutDialogState = shortcutDialogState,
                        onUpdateShortcut = {},
                        contentDescription = "UpdateShortcutDialog",
                    )
                }
            }
        }
    }
}
