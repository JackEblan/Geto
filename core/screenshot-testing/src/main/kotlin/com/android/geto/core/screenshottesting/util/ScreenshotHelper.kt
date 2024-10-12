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
package com.android.geto.core.screenshottesting.util

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.takahirom.roborazzi.ExperimentalRoborazziApi
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.RoborazziOptions.CompareOptions
import com.github.takahirom.roborazzi.RoborazziOptions.RecordOptions
import com.github.takahirom.roborazzi.captureRoboImage

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
    darkMode: Boolean = false,
    body: @Composable () -> Unit,
) {
    captureScreenForDevice(
        fileName = fileName,
        deviceName = deviceName,
        deviceSpec = deviceSpec,
        darkMode = darkMode,
        body = body,
        onCapture = { filePath, roborazziOptions ->
            onRoot().captureRoboImage(
                filePath = filePath,
                roborazziOptions = roborazziOptions,
            )
        },
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
    content: @Composable (description: String) -> Unit,
) {
    captureScreenMultiTheme(
        name = name,
        overrideFileName = overrideFileName,
        content = content,
        onCapture = { filePath, roborazziOptions ->
            onRoot().captureRoboImage(
                filePath = filePath,
                roborazziOptions = roborazziOptions,
            )
        },
    )
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
