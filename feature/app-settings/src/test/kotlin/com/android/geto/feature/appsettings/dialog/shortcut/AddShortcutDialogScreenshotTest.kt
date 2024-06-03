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

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.core.designsystem.component.GetoBackground
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.screenshottesting.util.DefaultTestDevices
import com.android.geto.core.screenshottesting.util.captureDialogForDevice
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
class AddShortcutDialogScreenshotTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var shortcutDialogState: ShortcutDialogState

    @Before
    fun setUp() {
        shortcutDialogState = ShortcutDialogState()
    }

    @Test
    fun addShortcutDialog_empty() {
        composeTestRule.captureDialogForDevice(
            fileName = "AddShortcutDialogEmpty",
            deviceName = "foldable",
            deviceSpec = DefaultTestDevices.FOLDABLE.spec,
        ) {
            GetoTheme {
                AddShortcutDialog(
                    shortcutDialogState = shortcutDialogState,
                    packageName = "",
                    contentDescription = "",
                    onAddClick = {},
                )
            }
        }
    }

    @Test
    fun addShortcutDialog_filled_textfields() {
        shortcutDialogState.updateShortLabel("Geto")

        shortcutDialogState.updateLongLabel("Geto")

        composeTestRule.captureDialogForDevice(
            fileName = "AddShortcutDialogFilledTextFields",
            deviceName = "foldable",
            deviceSpec = DefaultTestDevices.FOLDABLE.spec,
        ) {
            GetoTheme {
                AddShortcutDialog(
                    shortcutDialogState = shortcutDialogState,
                    packageName = "",
                    contentDescription = "",
                    onAddClick = {},
                )
            }
        }
    }

    @Test
    fun addShortcutDialog_error_textfields() {
        shortcutDialogState.getShortcut(packageName = "Test")

        composeTestRule.captureDialogForDevice(
            fileName = "AddShortcutDialogErrorTextFields",
            deviceName = "foldable",
            deviceSpec = DefaultTestDevices.FOLDABLE.spec,
        ) {
            GetoTheme {
                AddShortcutDialog(
                    shortcutDialogState = shortcutDialogState,
                    packageName = "",
                    contentDescription = "",
                    onAddClick = {},
                )
            }
        }
    }

    @Test
    fun addShortcutDialog_empty_dark() {
        composeTestRule.captureDialogForDevice(
            fileName = "AddShortcutDialogEmpty",
            deviceName = "foldable_dark",
            deviceSpec = DefaultTestDevices.FOLDABLE.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AddShortcutDialog(
                        shortcutDialogState = shortcutDialogState,
                        packageName = "",
                        contentDescription = "",
                        onAddClick = {},
                    )
                }
            }
        }
    }

    @Test
    fun addShortcutDialog_filled_textfields_dark() {
        shortcutDialogState.updateShortLabel("Geto")

        shortcutDialogState.updateLongLabel("Geto")

        composeTestRule.captureDialogForDevice(
            fileName = "AddShortcutDialogFilledTextFields",
            deviceName = "foldable_dark",
            deviceSpec = DefaultTestDevices.FOLDABLE.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AddShortcutDialog(
                        shortcutDialogState = shortcutDialogState,
                        packageName = "",
                        contentDescription = "",
                        onAddClick = {},
                    )
                }
            }
        }
    }

    @Test
    fun addShortcutDialog_error_textfields_dark() {
        shortcutDialogState.getShortcut(packageName = "Test")

        composeTestRule.captureDialogForDevice(
            fileName = "AddShortcutDialogErrorTextFields",
            deviceName = "foldable_dark",
            deviceSpec = DefaultTestDevices.FOLDABLE.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AddShortcutDialog(
                        shortcutDialogState = shortcutDialogState,
                        packageName = "",
                        contentDescription = "",
                        onAddClick = {},
                    )
                }
            }
        }
    }
}
