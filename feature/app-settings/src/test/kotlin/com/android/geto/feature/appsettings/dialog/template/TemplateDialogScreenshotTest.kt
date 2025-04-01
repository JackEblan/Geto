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
package com.android.geto.feature.appsettings.dialog.template

import androidx.activity.ComponentActivity
import androidx.compose.material3.Surface
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.designsystem.theme.GetoTheme
import com.android.geto.domain.model.AppSettingTemplate
import com.android.geto.domain.model.DarkThemeConfig
import com.android.geto.domain.model.SettingType
import com.android.geto.domain.model.ThemeBrand
import com.android.geto.roborazzi.DefaultTestDevices
import com.android.geto.roborazzi.captureDialogForDevice
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.GraphicsMode
import org.robolectric.annotation.LooperMode
import kotlin.test.BeforeTest
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
@LooperMode(LooperMode.Mode.PAUSED)
class TemplateDialogScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val appSettingTemplates = List(5) { index ->
        AppSettingTemplate(
            settingType = SettingType.SYSTEM,
            label = "Geto",
            key = "Geto $index",
            valueOnLaunch = "0",
            valueOnRevert = "1",
        )
    }

    private lateinit var templateDialogState: TemplateDialogState

    @BeforeTest
    fun setup() {
        templateDialogState = TemplateDialogState()
    }

    @Test
    fun templateDialog_loading() {
        composeTestRule.captureDialogForDevice(
            fileName = "TemplateDialogLoading",
            deviceName = "foldable",
            deviceSpec = DefaultTestDevices.FOLDABLE.spec,
        ) {
            GetoTheme(
                themeBrand = ThemeBrand.GREEN,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                dynamicTheme = false,
            ) {
                TemplateDialog(
                    templateDialogUiState = TemplateDialogUiState.Loading,
                    templateDialogState = templateDialogState,
                    contentDescription = "Template Dialog",
                )
            }
        }
    }

    @Test
    fun templateDialog_populated() {
        composeTestRule.captureDialogForDevice(
            fileName = "TemplateDialogPopulated",
            deviceName = "foldable",
            deviceSpec = DefaultTestDevices.FOLDABLE.spec,
        ) {
            GetoTheme(
                themeBrand = ThemeBrand.GREEN,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                dynamicTheme = false,
            ) {
                TemplateDialog(
                    templateDialogUiState = TemplateDialogUiState.Success(appSettingTemplates = appSettingTemplates),
                    templateDialogState = templateDialogState,
                    contentDescription = "Template Dialog",
                )
            }
        }
    }

    @Test
    fun templateDialog_loading_dark() {
        composeTestRule.captureDialogForDevice(
            fileName = "TemplateDialogLoading",
            deviceName = "foldable_dark",
            deviceSpec = DefaultTestDevices.FOLDABLE.spec,
            darkMode = true,
        ) {
            GetoTheme(
                themeBrand = ThemeBrand.GREEN,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                dynamicTheme = false,
            ) {
                Surface {
                    TemplateDialog(
                        templateDialogUiState = TemplateDialogUiState.Loading,
                        templateDialogState = templateDialogState,
                        contentDescription = "Template Dialog",
                    )
                }
            }
        }
    }

    @Test
    fun templateDialog_populated_dark() {
        composeTestRule.captureDialogForDevice(
            fileName = "TemplateDialogPopulated",
            deviceName = "foldable_dark",
            deviceSpec = DefaultTestDevices.FOLDABLE.spec,
            darkMode = true,
        ) {
            GetoTheme(
                themeBrand = ThemeBrand.GREEN,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                dynamicTheme = false,
            ) {
                Surface {
                    TemplateDialog(
                        templateDialogUiState = TemplateDialogUiState.Success(appSettingTemplates = appSettingTemplates),
                        templateDialogState = templateDialogState,
                        contentDescription = "Template Dialog",
                    )
                }
            }
        }
    }
}
