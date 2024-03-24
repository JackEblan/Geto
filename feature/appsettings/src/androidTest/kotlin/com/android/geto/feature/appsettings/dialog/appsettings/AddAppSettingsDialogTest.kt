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
class AddAppSettingsDialogTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var resourcesWrapper: ResourcesWrapper

    private lateinit var appSettingsDialogState: AppSettingsDialogState

    private lateinit var scrollState: ScrollState

    @Before
    fun setUp() {
        hiltRule.inject()

        appSettingsDialogState = AppSettingsDialogState(resourcesWrapper = resourcesWrapper)

        scrollState = ScrollState(0)
    }

    @Test
    fun labelSupportingTextIsDisplayed_whenLabelTextFieldIsBlank() {
        composeTestRule.setContent {
            AddAppSettingsDialog(
                addAppSettingsDialogState = appSettingsDialogState,
                scrollState = scrollState,
                onAddSettings = {

                    appSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    appSettingsDialogState.updateLabel("")

                    appSettingsDialogState.updateKey("Test")

                    appSettingsDialogState.updateValueOnLaunch("Test")

                    appSettingsDialogState.updateValueOnRevert("Test")

                    appSettingsDialogState.getAppSettings(packageName = "packageName")
                },
                contentDescription = "Add App Settings Dialog"
            )
        }

        composeTestRule.onNodeWithTag("addAppSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addAppSettingsDialog:labelSupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun keySupportingTextIsDisplayed_whenKeyTextFieldIsBlank() {
        composeTestRule.setContent {
            AddAppSettingsDialog(
                addAppSettingsDialogState = appSettingsDialogState,
                scrollState = scrollState,
                onAddSettings = {
                    appSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    appSettingsDialogState.updateLabel("Test")

                    appSettingsDialogState.updateKey("")

                    appSettingsDialogState.updateValueOnLaunch("Test")

                    appSettingsDialogState.updateValueOnRevert("Test")

                    appSettingsDialogState.getAppSettings(packageName = "packageName")
                },
                contentDescription = "Add App Settings Dialog"
            )
        }

        composeTestRule.onNodeWithTag("addAppSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addAppSettingsDialog:keySupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun settingsKeyNotFoundSupportingTextIsDisplayed_whenSettingsKeyNotFound() {
        composeTestRule.setContent {
            AddAppSettingsDialog(
                addAppSettingsDialogState = appSettingsDialogState,
                scrollState = scrollState,
                onAddSettings = {
                    appSettingsDialogState.updateSecureSettings(
                        testSecureSettingList
                    )

                    appSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    appSettingsDialogState.updateLabel("Test")

                    appSettingsDialogState.updateKey("nameNotFound")

                    appSettingsDialogState.updateValueOnLaunch("Test")

                    appSettingsDialogState.updateValueOnRevert("Test")

                    appSettingsDialogState.getAppSettings(packageName = "packageName")
                },
                contentDescription = "Add App Settings Dialog"
            )
        }

        composeTestRule.onNodeWithTag("addAppSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addAppSettingsDialog:settingsKeyNotFoundSupportingText",
            useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun valueOnLaunchSupportingTextIsDisplayed_whenValueOnLaunchTextFieldIsBlank() {
        composeTestRule.setContent {
            AddAppSettingsDialog(
                addAppSettingsDialogState = appSettingsDialogState,
                scrollState = scrollState,
                onAddSettings = {
                    appSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    appSettingsDialogState.updateKey("Test")

                    appSettingsDialogState.updateLabel("Test")

                    appSettingsDialogState.updateValueOnLaunch("")

                    appSettingsDialogState.updateValueOnRevert("Test")

                    appSettingsDialogState.getAppSettings(packageName = "packageName")
                },
                contentDescription = "Add App Settings Dialog"
            )
        }

        composeTestRule.onNodeWithTag("addAppSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addAppSettingsDialog:valueOnLaunchSupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun valueOnRevertSupportingTextIsDisplayed_whenValueOnRevertTextFieldIsBlank() {
        composeTestRule.setContent {
            AddAppSettingsDialog(
                addAppSettingsDialogState = appSettingsDialogState,
                scrollState = scrollState,
                onAddSettings = {

                    appSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    appSettingsDialogState.updateKey("Test")

                    appSettingsDialogState.updateLabel("Test")

                    appSettingsDialogState.updateValueOnLaunch("Test")

                    appSettingsDialogState.updateValueOnRevert("")

                    appSettingsDialogState.getAppSettings(packageName = "packageName")
                },
                contentDescription = "Add App Settings Dialog"
            )
        }

        composeTestRule.onNodeWithTag("addAppSettingsDialog:add").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addAppSettingsDialog:valueOnRevertSupportingText", useUnmergedTree = true
        ).assertIsDisplayed()
    }

    @Test
    fun exposedDropdownMenuBoxIsDisplayed_whenSecureSettingsListExpandedIsTrue() {
        composeTestRule.setContent {
            appSettingsDialogState.updateSecureSettings(testSecureSettingList)

            AddAppSettingsDialog(
                addAppSettingsDialogState = appSettingsDialogState,
                scrollState = scrollState,
                onAddSettings = {
                    appSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    appSettingsDialogState.updateKey("Test")

                    appSettingsDialogState.updateLabel("Test")

                    appSettingsDialogState.updateValueOnLaunch("")

                    appSettingsDialogState.updateValueOnRevert("Test")

                    appSettingsDialogState.getAppSettings(packageName = "packageName")
                },
                contentDescription = "Add App Settings Dialog"
            )
        }

        composeTestRule.onNodeWithTag("addAppSettingsDialog:keyTextField").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addAppSettingsDialog:exposedDropdownMenuBox"
        ).assertIsDisplayed()
    }

    @Test
    fun keyTextFieldValue_whenSecureSettingsListItemIsClicked() {
        composeTestRule.setContent {
            appSettingsDialogState.updateSecureSettings(testSecureSettingList)

            AddAppSettingsDialog(
                addAppSettingsDialogState = appSettingsDialogState,
                scrollState = scrollState,
                onAddSettings = {
                    appSettingsDialogState.updateSelectedRadioOptionIndex(1)

                    appSettingsDialogState.updateKey("Test")

                    appSettingsDialogState.updateLabel("Test")

                    appSettingsDialogState.updateValueOnLaunch("")

                    appSettingsDialogState.updateValueOnRevert("Test")

                    appSettingsDialogState.getAppSettings(packageName = "packageName")
                },
                contentDescription = "Add App Settings Dialog"
            )
        }

        composeTestRule.onNodeWithTag("addAppSettingsDialog:keyTextField").performClick()

        composeTestRule.onNodeWithText("name0").performClick()

        composeTestRule.onNodeWithTag(
            testTag = "addAppSettingsDialog:keyTextField"
        ).assertTextEquals("Settings key", "name0")

        composeTestRule.onNodeWithTag(
            testTag = "addAppSettingsDialog:valueOnRevertTextField"
        ).assertTextEquals("Settings value on revert", "value0")
    }
}

private val testSecureSettingList = listOf(
    SecureSetting(id = 0, name = "name0", value = "value0"),
)
