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
import androidx.activity.compose.setContent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.github.takahirom.roborazzi.RoborazziOptions
import com.github.takahirom.roborazzi.captureRoboImage
import com.google.accompanist.testharness.TestHarness
import org.robolectric.RuntimeEnvironment

fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.captureSnackbarForMultiDevice(
    snackbarHostState: SnackbarHostState,
    message: String,
    testTag: String,
    fileName: String,
    body: @Composable () -> Unit,
) {
    DefaultTestDevices.entries.forEach {
        this.captureSnackbarForDevice(
            snackbarHostState = snackbarHostState,
            message = message,
            testTag = testTag,
            fileName = fileName,
            deviceName = it.description,
            deviceSpec = it.spec,
            body = body,
        )
    }
}

@OptIn(ExperimentalTestApi::class)
fun <A : ComponentActivity> AndroidComposeTestRule<ActivityScenarioRule<A>, A>.captureSnackbarForDevice(
    snackbarHostState: SnackbarHostState,
    message: String,
    testTag: String,
    fileName: String,
    deviceName: String,
    deviceSpec: String,
    roborazziOptions: RoborazziOptions = DefaultRoborazziOptions,
    darkMode: Boolean = false,
    body: @Composable () -> Unit,
) {
    val (width, height, dpi) = extractSpecs(deviceSpec)

    // Set qualifiers from specs
    RuntimeEnvironment.setQualifiers("w${width}dp-h${height}dp-${dpi}dpi")

    this.activity.setContent {
        CompositionLocalProvider(
            LocalInspectionMode provides true,
        ) {
            TestHarness(darkMode = darkMode) {
                LaunchedEffect(key1 = true) {
                    snackbarHostState.showSnackbar(message = message)
                }

                body()
            }
        }
    }

    waitUntilAtLeastOneExists(matcher = hasTestTag(testTag = testTag))

    this.onRoot().captureRoboImage(
        filePath = "src/test/screenshots/${fileName}_$deviceName.png",
        roborazziOptions = roborazziOptions,
    )
}
