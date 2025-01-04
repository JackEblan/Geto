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
package com.android.geto.roborazzi

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
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.android.geto.designsystem.theme.GetoTheme
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.RoborazziOptions.CompareOptions
import com.github.takahirom.roborazzi.RoborazziOptions.RecordOptions
import com.github.takahirom.roborazzi.captureRoboImage
import com.google.accompanist.testharness.TestHarness
import org.robolectric.RuntimeEnvironment

@OptIn(ExperimentalRoborazziApi::class)
val DefaultRoborazziOptions = RoborazziOptions(
    // Pixel-perfect matching
    compareOptions = CompareOptions(changeThreshold = 0f),
    // Reduce the size of the PNGs
    recordOptions = RecordOptions(resizeScale = 0.5),
)

enum class DefaultTestDevices(val description: String, val spec: String) {
    PHONE("phone", "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480"),
    FOLDABLE(
        "foldable",
        "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480",
    ),
    TABLET("tablet", "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480"),
}

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.captureScreenForDevice(
    fileName: String,
    deviceName: String,
    deviceSpec: String,
    roborazziOptions: RoborazziOptions = DefaultRoborazziOptions,
    darkMode: Boolean = false,
    body: @Composable () -> Unit,
) {
    val (width, height, dpi) = extractSpecs(deviceSpec)

    RuntimeEnvironment.setQualifiers("w${width}dp-h${height}dp-${dpi}dpi")

    activity.setContent {
        CompositionLocalProvider(
            LocalInspectionMode provides true,
        ) {
            TestHarness(darkMode = darkMode) {
                body()
            }
        }
    }

    onRoot().captureRoboImage(
        filePath = "src/test/screenshots/${fileName}_$deviceName.png",
        roborazziOptions = roborazziOptions,
    )
}

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.captureScreenForMultiDevice(
    fileName: String,
    body: @Composable () -> Unit,
) {
    DefaultTestDevices.entries.forEach {
        captureScreenForDevice(
            deviceName = it.description,
            deviceSpec = it.spec,
            fileName = fileName,
            body = body,
        )
    }
}

/**
 * Takes six screenshots combining light/dark and default/Android themes and whether dynamic color
 * is enabled.
 */
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.captureScreenMultiTheme(
    name: String,
    overrideFileName: String? = null,
    roborazziOptions: RoborazziOptions = DefaultRoborazziOptions,
    content: @Composable (description: String) -> Unit,
) {
    var description by mutableStateOf("")

    var greenTheme by mutableStateOf(false)

    var purpleTheme by mutableStateOf(false)

    var darkTheme by mutableStateOf(false)

    var dynamicTheme by mutableStateOf(false)

    this.setContent {
        CompositionLocalProvider(
            LocalInspectionMode provides true,
        ) {
            GetoTheme(
                greenTheme = greenTheme,
                purpleTheme = purpleTheme,
                darkTheme = darkTheme,
                dynamicTheme = dynamicTheme,
            ) {
                key(greenTheme, purpleTheme, darkTheme, dynamicTheme) {
                    content(description)
                }
            }
        }
    }

    getoMultiThemes.forEach { (getoGreenTheme, getoPurpleTheme, getoDarkTheme, getoDynamicTheme, getoDescription) ->
        description = getoDescription
        greenTheme = getoGreenTheme
        purpleTheme = getoPurpleTheme
        darkTheme = getoDarkTheme
        dynamicTheme = getoDynamicTheme

        onRoot().captureRoboImage(
            filePath = "src/test/screenshots/" + "$name/${overrideFileName ?: name}" + getoDescription + ".png",
            roborazziOptions = roborazziOptions,
        )
    }
}

/**
 * Extracts some properties from the spec string. Note that this function is not exhaustive.
 */
internal fun extractSpecs(deviceSpec: String): TestDeviceSpecs {
    val specs = deviceSpec.substringAfter("spec:").split(",").map { it.split("=") }
        .associate { it[0] to it[1] }
    val width = specs["width"]?.toInt() ?: 640
    val height = specs["height"]?.toInt() ?: 480
    val dpi = specs["dpi"]?.toInt() ?: 480
    return TestDeviceSpecs(width, height, dpi)
}

data class TestDeviceSpecs(val width: Int, val height: Int, val dpi: Int)

private data class GetoMultiTheme(
    val greenTheme: Boolean,
    val purpleTheme: Boolean,
    val darkTheme: Boolean,
    val dynamicTheme: Boolean,
    val description: String,
)

private val getoMultiThemes = listOf(
    GetoMultiTheme(
        greenTheme = true,
        purpleTheme = false,
        darkTheme = false,
        dynamicTheme = false,
        description = "GreenTheme",
    ),
    GetoMultiTheme(
        greenTheme = false,
        purpleTheme = true,
        darkTheme = false,
        dynamicTheme = false,
        description = "PurpleTheme",
    ),
    GetoMultiTheme(
        greenTheme = true,
        purpleTheme = false,
        darkTheme = true,
        dynamicTheme = false,
        description = "GreenDarkTheme",
    ),
    GetoMultiTheme(
        greenTheme = false,
        purpleTheme = true,
        darkTheme = true,
        dynamicTheme = false,
        description = "PurpleDarkTheme",
    ),
    GetoMultiTheme(
        greenTheme = false,
        purpleTheme = false,
        darkTheme = false,
        dynamicTheme = true,
        description = "DynamicTheme",
    ),
    GetoMultiTheme(
        greenTheme = false,
        purpleTheme = false,
        darkTheme = true,
        dynamicTheme = true,
        description = "DynamicDarkTheme",
    ),
)
