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
package com.android.geto.core.designsystem.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext

@VisibleForTesting
val LightGreenColorScheme = lightColorScheme(
    primary = Green.primaryLight,
    onPrimary = Green.onPrimaryLight,
    primaryContainer = Green.primaryContainerLight,
    onPrimaryContainer = Green.onPrimaryContainerLight,
    secondary = Green.secondaryLight,
    onSecondary = Green.onSecondaryLight,
    secondaryContainer = Green.secondaryContainerLight,
    onSecondaryContainer = Green.onSecondaryContainerLight,
    tertiary = Green.tertiaryLight,
    onTertiary = Green.onTertiaryLight,
    tertiaryContainer = Green.tertiaryContainerLight,
    onTertiaryContainer = Green.onTertiaryContainerLight,
    error = Green.errorLight,
    onError = Green.onErrorLight,
    errorContainer = Green.errorContainerLight,
    onErrorContainer = Green.onErrorContainerLight,
    background = Green.backgroundLight,
    onBackground = Green.onBackgroundLight,
    surface = Green.surfaceLight,
    onSurface = Green.onSurfaceLight,
    surfaceVariant = Green.surfaceVariantLight,
    onSurfaceVariant = Green.onSurfaceVariantLight,
    outline = Green.outlineLight,
    outlineVariant = Green.outlineVariantLight,
    scrim = Green.scrimLight,
    inverseSurface = Green.inverseSurfaceLight,
    inverseOnSurface = Green.inverseOnSurfaceLight,
    inversePrimary = Green.inversePrimaryLight,
    surfaceDim = Green.surfaceDimLight,
    surfaceBright = Green.surfaceBrightLight,
    surfaceContainerLowest = Green.surfaceContainerLowestLight,
    surfaceContainerLow = Green.surfaceContainerLowLight,
    surfaceContainer = Green.surfaceContainerLight,
    surfaceContainerHigh = Green.surfaceContainerHighLight,
    surfaceContainerHighest = Green.surfaceContainerHighestLight,
)

@VisibleForTesting
val DarkGreenColorScheme = darkColorScheme(
    primary = Green.primaryDark,
    onPrimary = Green.onPrimaryDark,
    primaryContainer = Green.primaryContainerDark,
    onPrimaryContainer = Green.onPrimaryContainerDark,
    secondary = Green.secondaryDark,
    onSecondary = Green.onSecondaryDark,
    secondaryContainer = Green.secondaryContainerDark,
    onSecondaryContainer = Green.onSecondaryContainerDark,
    tertiary = Green.tertiaryDark,
    onTertiary = Green.onTertiaryDark,
    tertiaryContainer = Green.tertiaryContainerDark,
    onTertiaryContainer = Green.onTertiaryContainerDark,
    error = Green.errorDark,
    onError = Green.onErrorDark,
    errorContainer = Green.errorContainerDark,
    onErrorContainer = Green.onErrorContainerDark,
    background = Green.backgroundDark,
    onBackground = Green.onBackgroundDark,
    surface = Green.surfaceDark,
    onSurface = Green.onSurfaceDark,
    surfaceVariant = Green.surfaceVariantDark,
    onSurfaceVariant = Green.onSurfaceVariantDark,
    outline = Green.outlineDark,
    outlineVariant = Green.outlineVariantDark,
    scrim = Green.scrimDark,
    inverseSurface = Green.inverseSurfaceDark,
    inverseOnSurface = Green.inverseOnSurfaceDark,
    inversePrimary = Green.inversePrimaryDark,
    surfaceDim = Green.surfaceDimDark,
    surfaceBright = Green.surfaceBrightDark,
    surfaceContainerLowest = Green.surfaceContainerLowestDark,
    surfaceContainerLow = Green.surfaceContainerLowDark,
    surfaceContainer = Green.surfaceContainerDark,
    surfaceContainerHigh = Green.surfaceContainerHighDark,
    surfaceContainerHighest = Green.surfaceContainerHighestDark,
)

@VisibleForTesting
val LightPurpleColorScheme = lightColorScheme(
    primary = Purple.primaryLight,
    onPrimary = Purple.onPrimaryLight,
    primaryContainer = Purple.primaryContainerLight,
    onPrimaryContainer = Purple.onPrimaryContainerLight,
    secondary = Purple.secondaryLight,
    onSecondary = Purple.onSecondaryLight,
    secondaryContainer = Purple.secondaryContainerLight,
    onSecondaryContainer = Purple.onSecondaryContainerLight,
    tertiary = Purple.tertiaryLight,
    onTertiary = Purple.onTertiaryLight,
    tertiaryContainer = Purple.tertiaryContainerLight,
    onTertiaryContainer = Purple.onTertiaryContainerLight,
    error = Purple.errorLight,
    onError = Purple.onErrorLight,
    errorContainer = Purple.errorContainerLight,
    onErrorContainer = Purple.onErrorContainerLight,
    background = Purple.backgroundLight,
    onBackground = Purple.onBackgroundLight,
    surface = Purple.surfaceLight,
    onSurface = Purple.onSurfaceLight,
    surfaceVariant = Purple.surfaceVariantLight,
    onSurfaceVariant = Purple.onSurfaceVariantLight,
    outline = Purple.outlineLight,
    outlineVariant = Purple.outlineVariantLight,
    scrim = Purple.scrimLight,
    inverseSurface = Purple.inverseSurfaceLight,
    inverseOnSurface = Purple.inverseOnSurfaceLight,
    inversePrimary = Purple.inversePrimaryLight,
    surfaceDim = Purple.surfaceDimLight,
    surfaceBright = Purple.surfaceBrightLight,
    surfaceContainerLowest = Purple.surfaceContainerLowestLight,
    surfaceContainerLow = Purple.surfaceContainerLowLight,
    surfaceContainer = Purple.surfaceContainerLight,
    surfaceContainerHigh = Purple.surfaceContainerHighLight,
    surfaceContainerHighest = Purple.surfaceContainerHighestLight,
)

@VisibleForTesting
val DarkPurpleColorScheme = darkColorScheme(
    primary = Purple.primaryDark,
    onPrimary = Purple.onPrimaryDark,
    primaryContainer = Purple.primaryContainerDark,
    onPrimaryContainer = Purple.onPrimaryContainerDark,
    secondary = Purple.secondaryDark,
    onSecondary = Purple.onSecondaryDark,
    secondaryContainer = Purple.secondaryContainerDark,
    onSecondaryContainer = Purple.onSecondaryContainerDark,
    tertiary = Purple.tertiaryDark,
    onTertiary = Purple.onTertiaryDark,
    tertiaryContainer = Purple.tertiaryContainerDark,
    onTertiaryContainer = Purple.onTertiaryContainerDark,
    error = Purple.errorDark,
    onError = Purple.onErrorDark,
    errorContainer = Purple.errorContainerDark,
    onErrorContainer = Purple.onErrorContainerDark,
    background = Purple.backgroundDark,
    onBackground = Purple.onBackgroundDark,
    surface = Purple.surfaceDark,
    onSurface = Purple.onSurfaceDark,
    surfaceVariant = Purple.surfaceVariantDark,
    onSurfaceVariant = Purple.onSurfaceVariantDark,
    outline = Purple.outlineDark,
    outlineVariant = Purple.outlineVariantDark,
    scrim = Purple.scrimDark,
    inverseSurface = Purple.inverseSurfaceDark,
    inverseOnSurface = Purple.inverseOnSurfaceDark,
    inversePrimary = Purple.inversePrimaryDark,
    surfaceDim = Purple.surfaceDimDark,
    surfaceBright = Purple.surfaceBrightDark,
    surfaceContainerLowest = Purple.surfaceContainerLowestDark,
    surfaceContainerLow = Purple.surfaceContainerLowDark,
    surfaceContainer = Purple.surfaceContainerDark,
    surfaceContainerHigh = Purple.surfaceContainerHighDark,
    surfaceContainerHighest = Purple.surfaceContainerHighestDark,
)

@Composable
fun GetoTheme(
    greenTheme: Boolean = false,
    purpleTheme: Boolean = false,
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        supportsDynamicTheming() && dynamicTheme -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        greenTheme -> if (darkTheme) DarkGreenColorScheme else LightGreenColorScheme

        purpleTheme -> if (darkTheme) DarkPurpleColorScheme else LightPurpleColorScheme

        else -> if (darkTheme) DarkGreenColorScheme else LightGreenColorScheme
    }

    CompositionLocalProvider {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = SwrTypography,
            content = content,
        )
    }
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
