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
package com.android.geto.feature.apps

import androidx.activity.ComponentActivity
import androidx.compose.material3.Surface
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.android.geto.designsystem.theme.GetoTheme
import com.android.geto.domain.model.DarkThemeConfig
import com.android.geto.domain.model.GetoApplicationInfo
import com.android.geto.domain.model.ThemeBrand
import com.android.geto.roborazzi.DefaultTestDevices
import com.android.geto.roborazzi.captureScreenForDevice
import com.android.geto.roborazzi.captureScreenForMultiDevice
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
class AppsScreenScreenshotTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val mappedGetoApplicationInfos = List(5) { index ->
        GetoApplicationInfo(
            flags = 0,
            icon = ByteArray(0),
            packageName = "com.android.geto$index",
            label = "Geto $index",
        )
    }

    @Test
    fun appsScreen_populated() {
        composeTestRule.captureScreenForMultiDevice("AppsScreenPopulated") {
            GetoTheme(
                themeBrand = ThemeBrand.GREEN,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                dynamicTheme = false,
            ) {
                AppsScreen(
                    appsUiState = AppsUiState.Success(mappedGetoApplicationInfos),
                    searchGetoApplicationInfos = emptyList(),
                    dockedSearchBarQuery = "",
                    dockedSearchBarExpanded = false,
                    onItemClick = { _, _ -> },
                    onSearch = {},
                    onQueryChange = {},
                    onExpandedChange = {},
                )
            }
        }
    }

    @Test
    fun appsScreen_loading() {
        composeTestRule.captureScreenForMultiDevice("AppsScreenLoading") {
            GetoTheme(
                themeBrand = ThemeBrand.GREEN,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                dynamicTheme = false,
            ) {
                AppsScreen(
                    appsUiState = AppsUiState.Loading,
                    searchGetoApplicationInfos = emptyList(),
                    dockedSearchBarQuery = "",
                    dockedSearchBarExpanded = false,
                    onItemClick = { _, _ -> },
                    onSearch = {},
                    onQueryChange = {},
                    onExpandedChange = {},
                )
            }
        }
    }

    @Test
    fun appsScreen_dockedSearchBar_populated() {
        composeTestRule.captureScreenForMultiDevice("DockedSearchBarPopulated") {
            GetoTheme(
                themeBrand = ThemeBrand.GREEN,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                dynamicTheme = false,
            ) {
                AppsScreen(
                    appsUiState = AppsUiState.Success(emptyList()),
                    searchGetoApplicationInfos = mappedGetoApplicationInfos,
                    dockedSearchBarQuery = "",
                    dockedSearchBarExpanded = true,
                    onItemClick = { _, _ -> },
                    onSearch = {},
                    onQueryChange = {},
                    onExpandedChange = {},
                )
            }
        }
    }

    @Test
    fun appsScreen_populated_dark() {
        composeTestRule.captureScreenForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            fileName = "AppsScreenPopulated",
            darkMode = true,
        ) {
            GetoTheme(
                themeBrand = ThemeBrand.GREEN,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                dynamicTheme = false,
            ) {
                Surface {
                    AppsScreen(
                        appsUiState = AppsUiState.Success(
                            mappedGetoApplicationInfos,
                        ),
                        searchGetoApplicationInfos = emptyList(),
                        dockedSearchBarQuery = "",
                        dockedSearchBarExpanded = false,
                        onItemClick = { _, _ -> },
                        onSearch = {},
                        onQueryChange = {},
                        onExpandedChange = {},
                    )
                }
            }
        }
    }

    @Test
    fun appsScreen_loading_dark() {
        composeTestRule.captureScreenForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            fileName = "AppsScreenLoading",
            darkMode = true,
        ) {
            GetoTheme(
                themeBrand = ThemeBrand.GREEN,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                dynamicTheme = false,
            ) {
                Surface {
                    AppsScreen(
                        appsUiState = AppsUiState.Loading,
                        searchGetoApplicationInfos = emptyList(),
                        dockedSearchBarQuery = "",
                        dockedSearchBarExpanded = false,
                        onItemClick = { _, _ -> },
                        onSearch = {},
                        onQueryChange = {},
                        onExpandedChange = {},
                    )
                }
            }
        }
    }

    @Test
    fun appsScreen_dockedSearchBar_populated_dark() {
        composeTestRule.captureScreenForDevice(
            deviceName = "phone_dark",
            deviceSpec = DefaultTestDevices.PHONE.spec,
            fileName = "DockedSearchBarPopulated",
            darkMode = true,
        ) {
            GetoTheme(
                themeBrand = ThemeBrand.GREEN,
                darkThemeConfig = DarkThemeConfig.FOLLOW_SYSTEM,
                dynamicTheme = false,
            ) {
                Surface {
                    AppsScreen(
                        appsUiState = AppsUiState.Success(
                            emptyList(),
                        ),
                        searchGetoApplicationInfos = mappedGetoApplicationInfos,
                        dockedSearchBarQuery = "",
                        dockedSearchBarExpanded = true,
                        onItemClick = { _, _ -> },
                        onSearch = {},
                        onQueryChange = {},
                        onExpandedChange = {},
                    )
                }
            }
        }
    }
}
