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
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.android.geto.core.model.SecureSetting
import com.android.geto.core.resources.ResourcesWrapper
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AppSettingDialogTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Inject
    lateinit var resourcesWrapper: ResourcesWrapper

    private lateinit var appSettingsDialogState: AppSettingDialogState

    private val secureSettings = List(5) { index ->
        SecureSetting(id = index.toLong(), name = "Geto $index", value = "0")
    }

    @Before
    fun setUp() {
        hiltRule.inject()

        appSettingsDialogState = AppSettingDialogState(resourcesWrapper = resourcesWrapper)

        appSettingsDialogState.updateSecureSettings(secureSettings)
    }

    @Test
    fun labelSupportingText_isDisplayed_whenLabelTextField_isBlank() {
        composeTestRule.setContent {
            AppSettingDialog(
                addAppSettingDialogState = appSettingsDialogState, onAddSetting = {
                    appSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    appSettingsDialogState.updateKey("Geto 1")

                    appSettingsDialogState.updateLabel("")

                    appSettingsDialogState.updateValueOnLaunch("Geto")

                    appSettingsDialogState.updateValueOnRevert("Geto")

                    appSettingsDialogState.getAppSetting(packageName = "com.android.geto")
                }, contentDescription = "Add App Setting Dialog"
            )
        }

        composeTestRule.onNodeWithTag("appSettingDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "appSettingDialog:labelSupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun keySupportingText_isDisplayed_whenKeyTextField_isBlank() {
        composeTestRule.setContent {
            AppSettingDialog(
                addAppSettingDialogState = appSettingsDialogState, onAddSetting = {
                    appSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    appSettingsDialogState.updateLabel("Geto")

                    appSettingsDialogState.updateKey("")

                    appSettingsDialogState.updateValueOnLaunch("Geto")

                    appSettingsDialogState.updateValueOnRevert("Geto")

                    appSettingsDialogState.getAppSetting(packageName = "com.android.geto")
                }, contentDescription = "Add App Setting Dialog"
            )
        }

        composeTestRule.onNodeWithTag("appSettingDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "appSettingDialog:keySupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun settingsKeyNotFoundSupportingText_isDisplayed_whenSettingsKey_notFound() {
        composeTestRule.setContent {
            AppSettingDialog(
                addAppSettingDialogState = appSettingsDialogState, onAddSetting = {
                    appSettingsDialogState.updateSecureSettings(
                        secureSettings
                    )

                    appSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    appSettingsDialogState.updateLabel("Geto")

                    appSettingsDialogState.updateKey("_")

                    appSettingsDialogState.updateValueOnLaunch("Geto")

                    appSettingsDialogState.updateValueOnRevert("Geto")

                    appSettingsDialogState.getAppSetting(packageName = "com.android.geto")
                }, contentDescription = "Add App Setting Dialog"
            )
        }

        composeTestRule.onNodeWithTag("appSettingDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "appSettingDialog:settingsKeyNotFoundSupportingText",
            useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun valueOnLaunchSupportingText_isDisplayed_whenValueOnLaunchTextField_isBlank() {
        composeTestRule.setContent {
            AppSettingDialog(
                addAppSettingDialogState = appSettingsDialogState, onAddSetting = {
                    appSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    appSettingsDialogState.updateKey("Geto 1")

                    appSettingsDialogState.updateLabel("Geto")

                    appSettingsDialogState.updateValueOnLaunch("")

                    appSettingsDialogState.updateValueOnRevert("Geto")

                    appSettingsDialogState.getAppSetting(packageName = "com.android.geto")
                }, contentDescription = "Add App Setting Dialog"
            )
        }

        composeTestRule.onNodeWithTag("appSettingDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "appSettingDialog:valueOnLaunchSupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun valueOnRevertSupportingText_isDisplayed_whenValueOnRevertTextField_isBlank() {
        composeTestRule.setContent {
            AppSettingDialog(
                addAppSettingDialogState = appSettingsDialogState, onAddSetting = {
                    appSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    appSettingsDialogState.updateKey("Geto 1")

                    appSettingsDialogState.updateLabel("Geto")

                    appSettingsDialogState.updateValueOnLaunch("Geto")

                    appSettingsDialogState.updateValueOnRevert("")

                    appSettingsDialogState.getAppSetting(packageName = "com.android.geto")
                }, contentDescription = "Add App Setting Dialog"
            )
        }

        composeTestRule.onNodeWithTag("appSettingDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "appSettingDialog:valueOnRevertSupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun exposedDropdownMenuBox_isDisplayed_whenSecureSettingsListExpanded_isTrue() {
        composeTestRule.setContent {
            appSettingsDialogState.updateSecureSettings(secureSettings)

            AppSettingDialog(
                addAppSettingDialogState = appSettingsDialogState, onAddSetting = {
                    appSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    appSettingsDialogState.updateKey("Geto 1")

                    appSettingsDialogState.updateLabel("Geto")

                    appSettingsDialogState.updateValueOnLaunch("Geto")

                    appSettingsDialogState.updateValueOnRevert("Geto")

                    appSettingsDialogState.getAppSetting(packageName = "com.android.geto")
                }, contentDescription = "Add App Setting Dialog"
            )
        }

        composeTestRule.onNodeWithTag("appSettingDialog:keyTextField").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "appSettingDialog:exposedDropdownMenuBox"
        ).assertIsDisplayed()
    }

    @Test
    fun keyTextField_has_value_whenSecureSettingsListItem_isClicked() {
        composeTestRule.setContent {
            AppSettingDialog(
                addAppSettingDialogState = appSettingsDialogState, onAddSetting = {
                    appSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    appSettingsDialogState.updateKey("Geto 1")

                    appSettingsDialogState.updateLabel("Geto")

                    appSettingsDialogState.updateValueOnLaunch("")

                    appSettingsDialogState.updateValueOnRevert("Geto")

                    appSettingsDialogState.getAppSetting(packageName = "com.android.geto")
                }, contentDescription = "Add App Setting Dialog"
            )
        }

        composeTestRule.onNodeWithTag("appSettingDialog:keyTextField").performClick()

        composeTestRule.onNodeWithText("Geto 1").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "appSettingDialog:keyTextField"
        ).assertTextEquals("Setting key", "Geto 1")

        composeTestRule.onNodeWithTag(
            testTag = "appSettingDialog:valueOnRevertTextField"
        ).assertTextEquals("Setting value on revert", "0")
    }
}
