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
package com.android.geto.core.designsystem

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.core.designsystem.component.SingleSelectionDialog
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.screenshot.testing.util.captureDialogMultiTheme
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
@Config(application = HiltTestApplication::class, qualifiers = "480dpi")
@LooperMode(LooperMode.Mode.PAUSED)
class SingleSelectionDialogScreenshotTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun singleSelectionDialog_multipleThemes() {
        composeTestRule.captureDialogMultiTheme(
            name = "SingleSelectionDialog",
            overrideFileName = "SingleSelectionDialog",
        ) {
            GetoTheme {
                SingleSelectionDialog(
                    title = "Single Selection",
                    items = arrayOf(
                        "Item 0",
                        "Item 1",
                    ),
                    onDismissRequest = {},
                    selected = 0,
                    onSelect = {},
                    negativeButtonText = "Cancel",
                    positiveButtonText = "Okay",
                    onNegativeButtonClick = {},
                    onPositiveButtonClick = {},
                    contentDescription = "",
                )
            }
        }
    }
}
