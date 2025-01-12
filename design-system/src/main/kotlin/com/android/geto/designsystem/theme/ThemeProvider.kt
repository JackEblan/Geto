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
package com.android.geto.designsystem.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.android.geto.domain.model.DarkThemeConfig

sealed interface ThemeProvider {
    @Composable
    fun getColorScheme(darkThemeConfig: DarkThemeConfig): ColorScheme

    data object Green : ThemeProvider {
        @Composable
        override fun getColorScheme(darkThemeConfig: DarkThemeConfig): ColorScheme {
            return when (darkThemeConfig) {
                DarkThemeConfig.FOLLOW_SYSTEM -> {
                    if (isSystemInDarkTheme()) DarkGreenColorScheme else LightGreenColorScheme
                }

                DarkThemeConfig.LIGHT -> {
                    LightGreenColorScheme
                }

                DarkThemeConfig.DARK -> {
                    DarkGreenColorScheme
                }
            }
        }
    }

    data object Purple : ThemeProvider {
        @Composable
        override fun getColorScheme(darkThemeConfig: DarkThemeConfig): ColorScheme {
            return when (darkThemeConfig) {
                DarkThemeConfig.FOLLOW_SYSTEM -> {
                    if (isSystemInDarkTheme()) DarkPurpleColorScheme else LightPurpleColorScheme
                }

                DarkThemeConfig.LIGHT -> {
                    LightPurpleColorScheme
                }

                DarkThemeConfig.DARK -> {
                    DarkPurpleColorScheme
                }
            }
        }
    }

    data object Dynamic : ThemeProvider {
        @RequiresApi(Build.VERSION_CODES.S)
        @Composable
        override fun getColorScheme(darkThemeConfig: DarkThemeConfig): ColorScheme {
            val context = LocalContext.current

            return when (darkThemeConfig) {
                DarkThemeConfig.FOLLOW_SYSTEM -> {
                    if (isSystemInDarkTheme()) {
                        dynamicDarkColorScheme(context)
                    } else {
                        dynamicLightColorScheme(
                            context,
                        )
                    }
                }

                DarkThemeConfig.LIGHT -> {
                    dynamicLightColorScheme(
                        context,
                    )
                }

                DarkThemeConfig.DARK -> {
                    dynamicDarkColorScheme(context)
                }
            }
        }
    }
}
