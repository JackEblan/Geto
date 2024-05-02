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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.core.designsystem.component.GetoBackground
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.screenshot.testing.util.DefaultTestDevices
import com.android.geto.core.screenshot.testing.util.captureDialogForDevice
import com.android.geto.core.screenshot.testing.util.captureDialogMultiDevice
import com.android.geto.feature.appsettings.R
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
class ShortcutDialogScreenshotTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var shortcutDialogState: ShortcutDialogState

    @Before
    fun setUp() {
        shortcutDialogState = ShortcutDialogState()
    }

    @Test
    fun shortcutDialog_empty() {
        composeTestRule.captureDialogMultiDevice(
            fileName = "ShortcutDialogEmpty",
        ) {
            GetoTheme {
                ShortcutDialog(
                    shortcutDialogState = shortcutDialogState,
                    packageName = "com.android.geto",
                    contentDescription = "Shortcut Dialog",
                    title = stringResource(id = R.string.add_shortcut),
                    negativeButtonText = stringResource(id = R.string.cancel),
                    positiveButtonText = stringResource(id = R.string.add),
                    onPositiveButtonClick = {},
                )
            }
        }
    }

    @Test
    fun shortcutDialog_filled_textfields() {
        shortcutDialogState.updateShortLabel("Short Label")

        shortcutDialogState.updateLongLabel("Long Label")

        composeTestRule.captureDialogMultiDevice(
            fileName = "ShortcutDialogFilledTextFields",
        ) {
            GetoTheme {
                ShortcutDialog(
                    shortcutDialogState = shortcutDialogState,
                    packageName = "com.android.geto",
                    contentDescription = "Shortcut Dialog",
                    title = stringResource(id = R.string.add_shortcut),
                    negativeButtonText = stringResource(id = R.string.cancel),
                    positiveButtonText = stringResource(id = R.string.add),
                    onPositiveButtonClick = {},
                )
            }
        }
    }

    @Test
    fun shortcutDialog_error_textfields() {
        shortcutDialogState.getShortcut(packageName = "Test")

        composeTestRule.captureDialogMultiDevice(
            fileName = "ShortcutDialogErrorTextFields",
        ) {
            GetoTheme {
                ShortcutDialog(
                    shortcutDialogState = shortcutDialogState,
                    packageName = "com.android.geto",
                    contentDescription = "Shortcut Dialog",
                    title = stringResource(id = R.string.add_shortcut),
                    negativeButtonText = stringResource(id = R.string.cancel),
                    positiveButtonText = stringResource(id = R.string.add),
                    onPositiveButtonClick = {},
                )
            }
        }
    }

    @Test
    fun shortcutDialog_empty_dark() {
        composeTestRule.captureDialogForDevice(
            fileName = "ShortcutDialogEmpty",
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    ShortcutDialog(
                        shortcutDialogState = shortcutDialogState,
                        packageName = "com.android.geto",
                        contentDescription = "Shortcut Dialog",
                        title = stringResource(id = R.string.add_shortcut),
                        negativeButtonText = stringResource(id = R.string.cancel),
                        positiveButtonText = stringResource(id = R.string.add),
                        onPositiveButtonClick = {},
                    )
                }
            }
        }
    }

    @Test
    fun shortcutDialog_filled_textfields_dark() {
        shortcutDialogState.updateShortLabel("Short Label")

        shortcutDialogState.updateLongLabel("Long Label")

        composeTestRule.captureDialogForDevice(
            fileName = "ShortcutDialogFilledTextFields",
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    ShortcutDialog(
                        shortcutDialogState = shortcutDialogState,
                        packageName = "com.android.geto",
                        contentDescription = "Add Shortcut Dialog",
                        title = stringResource(id = R.string.add_shortcut),
                        negativeButtonText = stringResource(id = R.string.cancel),
                        positiveButtonText = stringResource(id = R.string.add),
                        onPositiveButtonClick = {},
                    )
                }
            }
        }
    }

    @Test
    fun shortcutDialog_error_textfields_dark() {
        shortcutDialogState.getShortcut(packageName = "Test")

        composeTestRule.captureDialogForDevice(
            fileName = "ShortcutDialogErrorTextFields",
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    ShortcutDialog(
                        shortcutDialogState = shortcutDialogState,
                        packageName = "com.android.geto",
                        contentDescription = "Add Shortcut Dialog",
                        title = stringResource(id = R.string.add_shortcut),
                        negativeButtonText = stringResource(id = R.string.cancel),
                        positiveButtonText = stringResource(id = R.string.add),
                        onPositiveButtonClick = {},
                    )
                }
            }
        }
    }
}
