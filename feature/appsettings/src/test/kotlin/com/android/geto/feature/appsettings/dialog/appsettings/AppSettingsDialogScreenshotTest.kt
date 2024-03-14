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

package com.android.geto.feature.appsettings.dialog.appsettings

import androidx.activity.ComponentActivity
import androidx.compose.foundation.rememberScrollState
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
@Config(application = HiltTestApplication::class)
@LooperMode(LooperMode.Mode.PAUSED)
class AppSettingsDialogScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun app_settings_dialog_empty() {
        composeTestRule.captureMultiDevice("AppSettingsDialogEmpty") {
            GetoTheme {
                val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

                val scrollState = rememberScrollState()

                AddAppSettingsDialogScreen(addAppSettingsDialogState = addAppSettingsDialogState,
                                           scrollState = scrollState,
                                           onAddSettings = {})
            }
        }
    }

    @Test
    fun app_settings_dialog_filled_textfields() {
        composeTestRule.captureMultiDevice("AppSettingsDialogFilledTextFields") {
            GetoTheme {
                val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

                val scrollState = rememberScrollState()

                addAppSettingsDialogState.updateSelectedRadioOptionIndex(1)

                addAppSettingsDialogState.updateKey("Test")

                addAppSettingsDialogState.updateLabel("Test")

                addAppSettingsDialogState.updateValueOnLaunch("Test")

                addAppSettingsDialogState.updateValueOnRevert("Test")

                AddAppSettingsDialogScreen(addAppSettingsDialogState = addAppSettingsDialogState,
                                           scrollState = scrollState,
                                           onAddSettings = {})
            }
        }
    }

    @Test
    fun app_settings_dialog_error_textfields() {
        composeTestRule.captureMultiDevice("AppSettingsDialogErrorTextFields") {
            GetoTheme {
                val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

                val scrollState = rememberScrollState()

                addAppSettingsDialogState.getAppSettings(packageName = "Test")

                AddAppSettingsDialogScreen(addAppSettingsDialogState = addAppSettingsDialogState,
                                           scrollState = scrollState,
                                           onAddSettings = {})
            }
        }
    }

    @Test
    fun app_settings_dialog_empty_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "AppSettingsDialogEmpty",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

                    val scrollState = rememberScrollState()

                    AddAppSettingsDialogScreen(addAppSettingsDialogState = addAppSettingsDialogState,
                                               scrollState = scrollState,
                                               onAddSettings = {})
                }
            }
        }
    }

    @Test
    fun app_settings_dialog_filled_textfields_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "AppSettingsDialogFilledTextFields",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

                    val scrollState = rememberScrollState()

                    addAppSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    addAppSettingsDialogState.updateKey("Test")

                    addAppSettingsDialogState.updateLabel("Test")

                    addAppSettingsDialogState.updateValueOnLaunch("Test")

                    addAppSettingsDialogState.updateValueOnRevert("Test")

                    AddAppSettingsDialogScreen(addAppSettingsDialogState = addAppSettingsDialogState,
                                               scrollState = scrollState,
                                               onAddSettings = {})
                }
            }
        }
    }

    @Test
    fun app_settings_dialog_error_textfields_dark() {
        composeTestRule.captureForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            screenshotName = "AppSettingsDialogErrorTextFields",
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    val addAppSettingsDialogState = rememberAddAppSettingsDialogState()

                    val scrollState = rememberScrollState()

                    addAppSettingsDialogState.getAppSettings(packageName = "Test")

                    AddAppSettingsDialogScreen(addAppSettingsDialogState = addAppSettingsDialogState,
                                               scrollState = scrollState,
                                               onAddSettings = {})
                }
            }
        }
    }
}