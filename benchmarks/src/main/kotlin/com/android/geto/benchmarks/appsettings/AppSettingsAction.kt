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
package com.android.geto.benchmarks.appsettings

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import com.android.geto.benchmarks.apps.appsClickFirstItem
import com.android.geto.benchmarks.waitForLoadingWheelToDisappear
import org.junit.Assert.fail

fun MacrobenchmarkScope.goToAppSettingsScreen() {
    waitForLoadingWheelToDisappear()
    appsClickFirstItem()
    device.waitForIdle()

    getTopAppBar()
    waitForLoadingWheelToDisappear()
    openAppSettingsDialog()
    openShortcutDialog()
}

private fun MacrobenchmarkScope.getTopAppBar() {
    val topAppBar = device.findObject(By.res("appSettings:topAppBar"))

    if (topAppBar.text.isNullOrEmpty()) fail("No application found.")
}

private fun MacrobenchmarkScope.openAppSettingsDialog() {
    val settingsIcon = device.findObject(By.desc("Settings icon"))
    settingsIcon.click()
    device.waitForIdle()
    val dialog = device.findObject(By.desc("Add App Settings Dialog"))
    val dialogCancelButton = dialog.findObject(By.text("Cancel"))
    dialogCancelButton.click()
    device.waitForIdle()
}

private fun MacrobenchmarkScope.openShortcutDialog() {
    val settingsIcon = device.findObject(By.desc("Shortcut icon"))
    settingsIcon.click()
    device.waitForIdle()
    val dialog = device.findObject(By.desc("Add Shortcut Dialog"))
    val dialogCancelButton = dialog.findObject(By.text("Cancel"))
    dialogCancelButton.click()
    device.waitForIdle()
}
