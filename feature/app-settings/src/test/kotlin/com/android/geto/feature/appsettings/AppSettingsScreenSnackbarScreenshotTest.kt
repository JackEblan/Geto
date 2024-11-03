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
package com.android.geto.feature.appsettings

import androidx.activity.ComponentActivity
import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.core.designsystem.theme.GetoTheme
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.SettingType
import com.android.geto.core.screenshottesting.util.captureSnackbarForMultiDevice
import com.android.geto.core.testing.util.MainDispatcherRule
import com.android.geto.feature.appsettings.dialog.template.TemplateDialogUiState
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.test.runTest
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
class AppSettingsScreenSnackbarScreenshotTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val appSettings = List(5) { index ->
        AppSetting(
            id = index,
            enabled = true,
            settingType = SettingType.SYSTEM,
            packageName = "com.android.geto",
            label = "Geto",
            key = "Geto $index",
            valueOnLaunch = "0",
            valueOnRevert = "1",
        )
    }

    @Test
    fun appSettingsScreen_snackbar() = runTest {
        val snackbarHostState = SnackbarHostState()

        composeTestRule.captureSnackbarForMultiDevice(
            snackbarHostState = snackbarHostState,
            message = "This is a snackbar",
            testTag = "appSettings:snackbar",
            fileName = "AppSettingsScreenSnackbar",
        ) {
            GetoTheme {
                AppSettingsScreen(
                    packageName = "com.android.geto",
                    appName = "Geto",
                    appSettingsUiState = AppSettingsUiState.Success(appSettings),
                    snackbarHostState = snackbarHostState,
                    applicationIcon = null,
                    secureSettings = emptyList(),
                    addAppSettingResult = null,
                    appSettingsResult = null,
                    revertAppSettingsResult = null,
                    autoLaunchResult = null,
                    requestPinShortcutResult = null,
                    setPrimaryClipResult = false,
                    templateDialogUiState = TemplateDialogUiState.Loading,
                    onNavigationIconClick = {},
                    onEvent = {},
                )
            }
        }
    }
}
