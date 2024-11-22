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
package com.android.geto.benchmarks.service

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import com.android.geto.benchmarks.waitForLoadingWheelToDisappear

fun MacrobenchmarkScope.goToServiceScreen() {
    waitForLoadingWheelToDisappear()
    clickServiceIcon()
    device.waitForIdle()
}

private fun MacrobenchmarkScope.clickServiceIcon() {
    val settingsIcon = device.findObject(By.desc("Service icon"))
    settingsIcon.click()
}
