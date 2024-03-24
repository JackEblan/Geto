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
class AppSettingsDialogScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var resourcesWrapper: ResourcesWrapper

    private lateinit var addAppSettingsDialogState: AppSettingsDialogState

    private lateinit var scrollState: ScrollState

    @Before
    fun setUp() {
        hiltRule.inject()

        addAppSettingsDialogState = AppSettingsDialogState(resourcesWrapper = resourcesWrapper)

        scrollState = ScrollState(0)
    }

    @Test
    fun appSettingsDialog_empty() {
        composeTestRule.captureScreenRoboImageMultiDevice(path = "AppSettingsDialog/AppSettingsDialogEmpty") {
            GetoTheme {
                AddAppSettingsDialog(
                    addAppSettingsDialogState = addAppSettingsDialogState,
                    scrollState = scrollState,
                    onAddSettings = {},
                    contentDescription = "AppSettingsDialog"
                )
            }
        }
    }

    @Test
    fun appSettingsDialog_filled_textfields() {
        addAppSettingsDialogState.updateSelectedRadioOptionIndex(1)

        addAppSettingsDialogState.updateKey("Geto")

        addAppSettingsDialogState.updateLabel("Geto")

        addAppSettingsDialogState.updateValueOnLaunch("0")

        addAppSettingsDialogState.updateValueOnRevert("1")

        composeTestRule.captureScreenRoboImageMultiDevice(
            path = "AppSettingsDialog/AppSettingsDialogFilledTextFields"
        ) {
            GetoTheme {
                AddAppSettingsDialog(
                    addAppSettingsDialogState = addAppSettingsDialogState,
                    scrollState = scrollState,
                    onAddSettings = {},
                    contentDescription = "AppSettingsDialog"
                )
            }
        }
    }

    @Test
    fun appSettingsDialog_error_textfields() {
        addAppSettingsDialogState.getAppSettings(packageName = "")

        composeTestRule.captureScreenRoboImageMultiDevice(
            path = "AppSettingsDialog/AppSettingsDialogErrorTextFields"
        ) {
            GetoTheme {
                AddAppSettingsDialog(
                    addAppSettingsDialogState = addAppSettingsDialogState,
                    scrollState = scrollState,
                    onAddSettings = {},
                    contentDescription = "AppSettingsDialog"
                )
            }
        }
    }

    @Test
    fun appSettingsDialog_empty_dark() {
        composeTestRule.captureScreenRoboImageForDevice(
            path = "AppSettingsDialog/AppSettingsDialogEmpty",
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AddAppSettingsDialog(
                        addAppSettingsDialogState = addAppSettingsDialogState,
                        scrollState = scrollState,
                        onAddSettings = {},
                        contentDescription = "AppSettingsDialog"
                    )
                }
            }
        }
    }

    @Test
    fun appSettingsDialog_filled_textfields_dark() {
        addAppSettingsDialogState.updateSelectedRadioOptionIndex(1)

        addAppSettingsDialogState.updateKey("Test")

        addAppSettingsDialogState.updateLabel("Test")

        addAppSettingsDialogState.updateValueOnLaunch("Test")

        addAppSettingsDialogState.updateValueOnRevert("Test")

        composeTestRule.captureScreenRoboImageForDevice(
            path = "AppSettingsDialog/AppSettingsDialogFilledTextFields",
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AddAppSettingsDialog(
                        addAppSettingsDialogState = addAppSettingsDialogState,
                        scrollState = scrollState,
                        onAddSettings = {},
                        contentDescription = "AppSettingsDialog"
                    )
                }
            }
        }
    }

    @Test
    fun appSettingsDialog_error_textfields_dark() {
        addAppSettingsDialogState.getAppSettings(packageName = "Test")

        composeTestRule.captureScreenRoboImageForDevice(
            path = "AppSettingsDialog/AppSettingsDialogErrorTextFields",
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            darkMode = true,
        ) {
            GetoTheme {
                GetoBackground {
                    AddAppSettingsDialog(
                        addAppSettingsDialogState = addAppSettingsDialogState,
                        scrollState = scrollState,
                        onAddSettings = {},
                        contentDescription = "AppSettingsDialog"
                    )
                }
            }
        }
    }
}