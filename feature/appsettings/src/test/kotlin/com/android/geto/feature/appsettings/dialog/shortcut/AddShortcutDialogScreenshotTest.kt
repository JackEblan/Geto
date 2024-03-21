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
import com.android.geto.core.resources.ResourcesWrapper
import com.android.geto.core.screenshot.testing.util.DefaultTestDevices
import com.android.geto.core.screenshot.testing.util.captureScreenRoboImageForDevice
import com.android.geto.core.screenshot.testing.util.captureScreenRoboImageMultiDevice
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode
import javax.inject.Inject
import kotlin.test.Test

@HiltAndroidTest
@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@Config(application = HiltTestApplication::class)
@LooperMode(LooperMode.Mode.PAUSED)
class AddShortcutDialogScreenshotTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var resourcesWrapper: ResourcesWrapper

    private lateinit var shortcutDialogState: ShortcutDialogState

    @Before
    fun setUp() {
        hiltRule.inject()

        shortcutDialogState = ShortcutDialogState(resourcesWrapper = resourcesWrapper)
    }

    @Test
    fun add_shortcut_dialog_empty() {
        composeTestRule.captureScreenRoboImageMultiDevice(
            path = "AddShortcutDialog/AddShortcutDialogEmpty"
        ) {
            GetoTheme {
                AddShortcutDialog(
                    shortcutDialogState = shortcutDialogState,
                    onAddShortcut = {},
                    contentDescription = "AddShortcutDialog"
                )
            }
        }
    }

    @Test
    fun add_shortcut_dialog_filled_textfields() {
        shortcutDialogState.updateShortLabel("Short Label")

        shortcutDialogState.updateLongLabel("Long Label")

        composeTestRule.captureScreenRoboImageMultiDevice(
            path = "AddShortcutDialog/AddShortcutDialogFilledTextFields"
        ) {
            GetoTheme {
                AddShortcutDialog(
                    shortcutDialogState = shortcutDialogState,
                    onAddShortcut = {},
                    contentDescription = "AddShortcutDialog"
                )
            }
        }
    }

    @Test
    fun add_shortcut_dialog_error_textfields() {
        shortcutDialogState.getShortcut(packageName = "Test", shortcutIntent = Intent())

        composeTestRule.captureScreenRoboImageMultiDevice(
            path = "AddShortcutDialog/AddShortcutDialogErrorTextFields"
        ) {
            GetoTheme {
                AddShortcutDialog(
                    shortcutDialogState = shortcutDialogState,
                    onAddShortcut = {},
                    contentDescription = "AddShortcutDialog"
                )
            }
        }
    }

    @Test
    fun add_shortcut_dialog_empty_dark() {
        composeTestRule.captureScreenRoboImageForDevice(
            path = "AddShortcutDialog/AddShortcutDialogEmpty",
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AddShortcutDialog(
                        shortcutDialogState = shortcutDialogState,
                        onAddShortcut = {},
                        contentDescription = "AddShortcutDialog"
                    )
                }
            }
        }
    }

    @Test
    fun add_shortcut_dialog_filled_textfields_dark() {
        shortcutDialogState.updateShortLabel("Short Label")

        shortcutDialogState.updateLongLabel("Long Label")

        composeTestRule.captureScreenRoboImageForDevice(
            path = "AddShortcutDialog/AddShortcutDialogFilledTextFields",
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AddShortcutDialog(
                        shortcutDialogState = shortcutDialogState,
                        onAddShortcut = {},
                        contentDescription = "AddShortcutDialog"
                    )
                }
            }
        }
    }

    @Test
    fun add_shortcut_dialog_error_textfields_dark() {
        shortcutDialogState.getShortcut(packageName = "Test", shortcutIntent = Intent())

        composeTestRule.captureScreenRoboImageForDevice(
            path = "AddShortcutDialog/AddShortcutDialogErrorTextFields",
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,

            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AddShortcutDialog(
                        shortcutDialogState = shortcutDialogState,
                        onAddShortcut = {},
                        contentDescription = "AddShortcutDialog"
                    )
                }
            }
        }
    }
}