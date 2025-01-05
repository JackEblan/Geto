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
package com.android.geto.designsystem

import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import com.android.geto.designsystem.theme.DarkGreenColorScheme
import com.android.geto.designsystem.theme.DarkPurpleColorScheme
import com.android.geto.designsystem.theme.GetoTheme
import com.android.geto.designsystem.theme.LightGreenColorScheme
import com.android.geto.designsystem.theme.LightPurpleColorScheme
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Tests [GetoTheme] using different combinations of the theme mode parameters:
 * darkTheme, disableDynamicTheming, and androidTheme.
 *
 * It verifies that the various composition locals — [MaterialTheme] and design system.
 */
@RunWith(RobolectricTestRunner::class)
class ThemeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun greenThemeFalse_purpleThemeFalse_darkThemeFalse_dynamicThemeFalse() {
        composeTestRule.setContent {
            GetoTheme(
                greenTheme = false,
                purpleTheme = false,
                darkTheme = false,
                dynamicTheme = false,
            ) {
                val colorScheme = LightGreenColorScheme
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
            }
        }
    }

    @Test
    fun greenThemeTrue_purpleThemeFalse_darkThemeFalse_dynamicThemeFalse() {
        composeTestRule.setContent {
            GetoTheme(
                greenTheme = true,
                purpleTheme = false,
                darkTheme = false,
                dynamicTheme = false,
            ) {
                val colorScheme = LightGreenColorScheme
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
            }
        }
    }

    @Test
    fun greenThemeFalse_purpleThemeTrue_darkThemeFalse_dynamicThemeFalse() {
        composeTestRule.setContent {
            GetoTheme(
                greenTheme = false,
                purpleTheme = true,
                darkTheme = false,
                dynamicTheme = false,
            ) {
                val colorScheme = LightPurpleColorScheme
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
            }
        }
    }

    @Test
    fun greenThemeFalse_purpleThemeFalse_darkThemeFalse_dynamicThemeTrue() {
        composeTestRule.setContent {
            GetoTheme(
                greenTheme = false,
                purpleTheme = false,
                darkTheme = false,
                dynamicTheme = true,
            ) {
                val colorScheme = dynamicLightColorSchemeWithFallback()
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
            }
        }
    }

    @Test
    fun greenThemeFalse_purpleThemeFalse_darkThemeTrue_dynamicThemeFalse() {
        composeTestRule.setContent {
            GetoTheme(
                greenTheme = false,
                purpleTheme = false,
                darkTheme = true,
                dynamicTheme = false,
            ) {
                val colorScheme = DarkGreenColorScheme
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
            }
        }
    }

    @Test
    fun greenThemeTrue_purpleThemeFalse_darkThemeTrue_dynamicThemeFalse() {
        composeTestRule.setContent {
            GetoTheme(
                greenTheme = true,
                purpleTheme = false,
                darkTheme = true,
                dynamicTheme = false,
            ) {
                val colorScheme = DarkGreenColorScheme
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
            }
        }
    }

    @Test
    fun greenThemeFalse_purpleThemeTrue_darkThemeTrue_dynamicThemeFalse() {
        composeTestRule.setContent {
            GetoTheme(
                greenTheme = false,
                purpleTheme = true,
                darkTheme = true,
                dynamicTheme = false,
            ) {
                val colorScheme = DarkPurpleColorScheme
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
            }
        }
    }

    @Test
    fun greenThemeFalse_purpleThemeFalse_darkThemeTrue_dynamicThemeTrue() {
        composeTestRule.setContent {
            GetoTheme(
                greenTheme = false,
                purpleTheme = false,
                darkTheme = true,
                dynamicTheme = true,
            ) {
                val colorScheme = dynamicDarkColorSchemeWithFallback()
                assertColorSchemesEqual(colorScheme, MaterialTheme.colorScheme)
            }
        }
    }

    @Composable
    private fun dynamicLightColorSchemeWithFallback(): ColorScheme = when {
        SDK_INT >= VERSION_CODES.S -> dynamicLightColorScheme(LocalContext.current)
        else -> LightGreenColorScheme
    }

    @Composable
    private fun dynamicDarkColorSchemeWithFallback(): ColorScheme = when {
        SDK_INT >= VERSION_CODES.S -> dynamicDarkColorScheme(LocalContext.current)
        else -> DarkGreenColorScheme
    }

    /**
     * Workaround for the fact that the SocialWorkReviewer design system specify all color scheme values.
     */
    private fun assertColorSchemesEqual(
        expectedColorScheme: ColorScheme,
        actualColorScheme: ColorScheme,
    ) {
        assertEquals(expectedColorScheme.primary, actualColorScheme.primary)
        assertEquals(expectedColorScheme.onPrimary, actualColorScheme.onPrimary)
        assertEquals(expectedColorScheme.primaryContainer, actualColorScheme.primaryContainer)
        assertEquals(expectedColorScheme.onPrimaryContainer, actualColorScheme.onPrimaryContainer)
        assertEquals(expectedColorScheme.secondary, actualColorScheme.secondary)
        assertEquals(expectedColorScheme.onSecondary, actualColorScheme.onSecondary)
        assertEquals(expectedColorScheme.secondaryContainer, actualColorScheme.secondaryContainer)
        assertEquals(
            expectedColorScheme.onSecondaryContainer,
            actualColorScheme.onSecondaryContainer,
        )
        assertEquals(expectedColorScheme.tertiary, actualColorScheme.tertiary)
        assertEquals(expectedColorScheme.onTertiary, actualColorScheme.onTertiary)
        assertEquals(expectedColorScheme.tertiaryContainer, actualColorScheme.tertiaryContainer)
        assertEquals(expectedColorScheme.onTertiaryContainer, actualColorScheme.onTertiaryContainer)
        assertEquals(expectedColorScheme.error, actualColorScheme.error)
        assertEquals(expectedColorScheme.onError, actualColorScheme.onError)
        assertEquals(expectedColorScheme.errorContainer, actualColorScheme.errorContainer)
        assertEquals(expectedColorScheme.onErrorContainer, actualColorScheme.onErrorContainer)
        assertEquals(expectedColorScheme.background, actualColorScheme.background)
        assertEquals(expectedColorScheme.onBackground, actualColorScheme.onBackground)
        assertEquals(expectedColorScheme.surface, actualColorScheme.surface)
        assertEquals(expectedColorScheme.onSurface, actualColorScheme.onSurface)
        assertEquals(expectedColorScheme.surfaceVariant, actualColorScheme.surfaceVariant)
        assertEquals(expectedColorScheme.onSurfaceVariant, actualColorScheme.onSurfaceVariant)
        assertEquals(expectedColorScheme.outline, actualColorScheme.outline)
        assertEquals(expectedColorScheme.outlineVariant, actualColorScheme.outlineVariant)
        assertEquals(expectedColorScheme.scrim, actualColorScheme.scrim)
        assertEquals(expectedColorScheme.inverseSurface, actualColorScheme.inverseSurface)
        assertEquals(expectedColorScheme.inverseOnSurface, actualColorScheme.inverseOnSurface)
        assertEquals(expectedColorScheme.inversePrimary, actualColorScheme.inversePrimary)
        assertEquals(expectedColorScheme.surfaceDim, actualColorScheme.surfaceDim)
        assertEquals(expectedColorScheme.surfaceBright, actualColorScheme.surfaceBright)
        assertEquals(
            expectedColorScheme.surfaceContainerLowest,
            expectedColorScheme.surfaceContainerLowest,
        )
        assertEquals(expectedColorScheme.surfaceContainerLow, actualColorScheme.surfaceContainerLow)
        assertEquals(expectedColorScheme.surfaceContainer, actualColorScheme.surfaceContainer)
        assertEquals(
            expectedColorScheme.surfaceContainerHigh,
            actualColorScheme.surfaceContainerHigh,
        )
        assertEquals(
            expectedColorScheme.surfaceContainerHighest,
            expectedColorScheme.surfaceContainerHighest,
        )
    }
}
