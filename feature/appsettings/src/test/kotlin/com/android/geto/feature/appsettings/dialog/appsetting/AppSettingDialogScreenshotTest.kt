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
package com.android.geto.feature.appsettings.dialog.appsetting

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.core.designsystem.component.GetoBackground
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.screenshot.testing.util.DefaultTestDevices
import com.android.geto.core.screenshot.testing.util.captureDialogForDevice
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
class AppSettingDialogScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var appSettingDialogState: AppSettingDialogState

    @Before
    fun setup() {
        appSettingDialogState = AppSettingDialogState()
    }

    @Test
    fun appSettingDialog_empty() {
        composeTestRule.captureDialogForDevice(
            name = "AppSettingDialog",
            fileName = "AppSettingDialogEmpty",
            deviceName = "tablet",
            deviceSpec = DefaultTestDevices.TABLET.spec,
        ) {
            GetoTheme {
                AppSettingDialog(
                    appSettingDialogState = appSettingDialogState,
                    packageName = "com.android.geto",
                    onAddSetting = {},
                    contentDescription = "AppSettingDialog",
                )
            }
        }
    }

    @Test
    fun appSettingDialog_filled_textfields() {
        appSettingDialogState.updateSelectedRadioOptionIndex(1)

        appSettingDialogState.updateKey("Geto")

        appSettingDialogState.updateLabel("Geto")

        appSettingDialogState.updateValueOnLaunch("0")

        appSettingDialogState.updateValueOnRevert("1")

        composeTestRule.captureDialogForDevice(
            name = "AppSettingDialog",
            fileName = "AppSettingDialogFilledTextFields",
            deviceName = "tablet",
            deviceSpec = DefaultTestDevices.TABLET.spec,
        ) {
            GetoTheme {
                AppSettingDialog(
                    appSettingDialogState = appSettingDialogState,
                    packageName = "com.android.geto",
                    onAddSetting = {},
                    contentDescription = "AppSettingDialog",
                )
            }
        }
    }

    @Test
    fun appSettingDialog_error_textfields() {
        appSettingDialogState.getAppSetting(packageName = "")

        composeTestRule.captureDialogForDevice(
            name = "AppSettingDialog",
            fileName = "AppSettingDialogErrorTextFields",
            deviceName = "tablet",
            deviceSpec = DefaultTestDevices.TABLET.spec,
        ) {
            GetoTheme {
                AppSettingDialog(
                    appSettingDialogState = appSettingDialogState,
                    packageName = "com.android.geto",
                    onAddSetting = {},
                    contentDescription = "AppSettingDialog",
                )
            }
        }
    }

    @Test
    fun appSettingDialog_empty_dark() {
        composeTestRule.captureDialogForDevice(
            name = "AppSettingDialog",
            fileName = "AppSettingDialogEmpty",
            deviceName = "tablet_dark",
            deviceSpec = DefaultTestDevices.TABLET.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AppSettingDialog(
                        appSettingDialogState = appSettingDialogState,
                        packageName = "com.android.geto",
                        onAddSetting = {},
                        contentDescription = "AppSettingDialog",
                    )
                }
            }
        }
    }

    @Test
    fun appSettingDialog_filled_textfields_dark() {
        appSettingDialogState.updateSelectedRadioOptionIndex(1)

        appSettingDialogState.updateKey("Geto")

        appSettingDialogState.updateLabel("Geto")

        appSettingDialogState.updateValueOnLaunch("0")

        appSettingDialogState.updateValueOnRevert("1")

        composeTestRule.captureDialogForDevice(
            name = "AppSettingDialog",
            fileName = "AppSettingDialogFilledTextFields",
            deviceName = "tablet_dark",
            deviceSpec = DefaultTestDevices.TABLET.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AppSettingDialog(
                        appSettingDialogState = appSettingDialogState,
                        packageName = "com.android.geto",
                        onAddSetting = {},
                        contentDescription = "AppSettingDialog",
                    )
                }
            }
        }
    }

    @Test
    fun appSettingDialog_error_textfields_dark() {
        appSettingDialogState.getAppSetting(packageName = "")

        composeTestRule.captureDialogForDevice(
            name = "AppSettingDialog",
            fileName = "AppSettingDialogErrorTextFields",
            deviceName = "tablet_dark",
            deviceSpec = DefaultTestDevices.TABLET.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AppSettingDialog(
                        appSettingDialogState = appSettingDialogState,
                        packageName = "com.android.geto",
                        onAddSetting = {},
                        contentDescription = "AppSettingDialog",
                    )
                }
            }
        }
    }
}
