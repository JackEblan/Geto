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
package com.android.geto.core.screenshot.testing.util

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.android.geto.core.designsystem.theme.GetoTheme
import com.github.takahirom.roborazzi.RoborazziOptions
import com.google.accompanist.testharness.TestHarness
import org.robolectric.RuntimeEnvironment

internal fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.captureForDevice(
    fileName: String,
    deviceName: String,
    deviceSpec: String,
    roborazziOptions: RoborazziOptions = DefaultRoborazziOptions,
    darkMode: Boolean = false,
    body: @Composable () -> Unit,
    onCapture: (filePath: String, roborazziOptions: RoborazziOptions) -> Unit,
) {
    val (width, height, dpi) = extractSpecs(deviceSpec)

    // Set qualifiers from specs
    RuntimeEnvironment.setQualifiers("w${width}dp-h${height}dp-${dpi}dpi")

    this.activity.setContent {
        CompositionLocalProvider(
            LocalInspectionMode provides true,
        ) {
            TestHarness(darkMode = darkMode) {
                body()
            }
        }
    }

    onCapture("src/test/screenshots/${fileName}_$deviceName.png", roborazziOptions)
}

internal fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.captureMultiTheme(
    name: String,
    overrideFileName: String? = null,
    roborazziOptions: RoborazziOptions = DefaultRoborazziOptions,
    shouldCompareDarkMode: Boolean = true,
    shouldCompareDynamicColor: Boolean = true,
    shouldCompareAndroidTheme: Boolean = true,
    content: @Composable (desc: String) -> Unit,
    onCapture: (
        filePath: String,
        roborazziOptions: RoborazziOptions,
    ) -> Unit,
) {
    val darkModeValues = if (shouldCompareDarkMode) listOf(true, false) else listOf(false)
    val dynamicThemingValues = if (shouldCompareDynamicColor) listOf(true, false) else listOf(false)
    val androidThemeValues = if (shouldCompareAndroidTheme) listOf(true, false) else listOf(false)

    var darkMode by mutableStateOf(true)
    var dynamicTheming by mutableStateOf(false)
    var androidTheme by mutableStateOf(false)

    this.setContent {
        CompositionLocalProvider(
            LocalInspectionMode provides true,
        ) {
            GetoTheme(
                androidTheme = androidTheme,
                darkTheme = darkMode,
                disableDynamicTheming = !dynamicTheming,
            ) {
                // Keying is necessary in some cases (e.g. animations)
                key(androidTheme, darkMode, dynamicTheming) {
                    val description = generateDescription(
                        shouldCompareDarkMode = shouldCompareDarkMode,
                        darkMode = darkMode,
                        shouldCompareAndroidTheme = shouldCompareAndroidTheme,
                        androidTheme = androidTheme,
                        shouldCompareDynamicColor = shouldCompareDynamicColor,
                        dynamicTheming = dynamicTheming,
                    )
                    content(description)
                }
            }
        }
    }

    // Create permutations
    darkModeValues.forEach { isDarkMode ->
        darkMode = isDarkMode
        val darkModeDesc = if (isDarkMode) "dark" else "light"

        androidThemeValues.forEach { isAndroidTheme ->
            androidTheme = isAndroidTheme
            val androidThemeDesc = if (isAndroidTheme) "androidTheme" else "defaultTheme"

            dynamicThemingValues.forEach dynamicTheme@{ isDynamicTheming ->
                // Skip tests with both Android Theme and Dynamic color as they're incompatible.
                if (isAndroidTheme && isDynamicTheming) return@dynamicTheme

                dynamicTheming = isDynamicTheming
                val dynamicThemingDesc = if (isDynamicTheming) "dynamic" else "notDynamic"

                val filename = overrideFileName ?: name

                onCapture(
                    "src/test/screenshots/" + "$name/$filename" + "_$darkModeDesc" + "_$androidThemeDesc" + "_$dynamicThemingDesc" + ".png",
                    roborazziOptions,
                )
            }
        }
    }
}
