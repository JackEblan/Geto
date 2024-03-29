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
import androidx.compose.foundation.ScrollState
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
class AddAppSettingDialogScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var resourcesWrapper: ResourcesWrapper

    private lateinit var addAppSettingDialogState: AppSettingDialogState

    private lateinit var scrollState: ScrollState

    @Before
    fun setUp() {
        hiltRule.inject()

        addAppSettingDialogState = AppSettingDialogState(resourcesWrapper = resourcesWrapper)

        scrollState = ScrollState(0)
    }

    @Test
    fun appSettingDialog_empty() {
        composeTestRule.captureScreenRoboImageMultiDevice(path = "AppSettingDialog/AppSettingDialogEmpty") {
            GetoTheme {
                AppSettingDialog(
                    addAppSettingDialogState = addAppSettingDialogState,
                    scrollState = scrollState,
                    onAddSetting = {},
                    contentDescription = "AppSettingDialog"
                )
            }
        }
    }

    @Test
    fun appSettingDialog_filled_textfields() {
        addAppSettingDialogState.updateSelectedRadioOptionIndex(1)

        addAppSettingDialogState.updateKey("Geto")

        addAppSettingDialogState.updateLabel("Geto")

        addAppSettingDialogState.updateValueOnLaunch("0")

        addAppSettingDialogState.updateValueOnRevert("1")

        composeTestRule.captureScreenRoboImageMultiDevice(
            path = "AppSettingDialog/AppSettingDialogFilledTextFields"
        ) {
            GetoTheme {
                AppSettingDialog(
                    addAppSettingDialogState = addAppSettingDialogState,
                    scrollState = scrollState,
                    onAddSetting = {},
                    contentDescription = "AppSettingDialog"
                )
            }
        }
    }

    @Test
    fun appSettingDialog_error_textfields() {
        addAppSettingDialogState.getAppSetting(packageName = "")

        composeTestRule.captureScreenRoboImageMultiDevice(
            path = "AppSettingDialog/AppSettingDialogErrorTextFields"
        ) {
            GetoTheme {
                AppSettingDialog(
                    addAppSettingDialogState = addAppSettingDialogState,
                    scrollState = scrollState,
                    onAddSetting = {},
                    contentDescription = "AppSettingDialog"
                )
            }
        }
    }

    @Test
    fun appSettingDialog_empty_dark() {
        composeTestRule.captureScreenRoboImageForDevice(
            path = "AppSettingDialog/AppSettingDialogEmpty",
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AppSettingDialog(
                        addAppSettingDialogState = addAppSettingDialogState,
                        scrollState = scrollState,
                        onAddSetting = {},
                        contentDescription = "AppSettingDialog"
                    )
                }
            }
        }
    }

    @Test
    fun appSettingDialog_filled_textfields_dark() {
        addAppSettingDialogState.updateSelectedRadioOptionIndex(1)

        addAppSettingDialogState.updateKey("Geto")

        addAppSettingDialogState.updateLabel("Geto")

        addAppSettingDialogState.updateValueOnLaunch("0")

        addAppSettingDialogState.updateValueOnRevert("1")

        composeTestRule.captureScreenRoboImageForDevice(
            path = "AppSettingDialog/AppSettingDialogFilledTextFields",
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AppSettingDialog(
                        addAppSettingDialogState = addAppSettingDialogState,
                        scrollState = scrollState,
                        onAddSetting = {},
                        contentDescription = "AppSettingDialog"
                    )
                }
            }
        }
    }

    @Test
    fun appSettingDialog_error_textfields_dark() {
        addAppSettingDialogState.getAppSetting(packageName = "")

        composeTestRule.captureScreenRoboImageForDevice(
            path = "AppSettingDialog/AppSettingDialogErrorTextFields",
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AppSettingDialog(
                        addAppSettingDialogState = addAppSettingDialogState,
                        scrollState = scrollState,
                        onAddSetting = {},
                        contentDescription = "AppSettingDialog"
                    )
                }
            }
        }
    }
}