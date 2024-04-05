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

package com.android.geto.benchmarks.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import com.android.geto.benchmarks.PACKAGE_NAME
import com.android.geto.benchmarks.applist.appListScrollDownUp
import com.android.geto.benchmarks.applist.appListWaitForContent
import org.junit.Rule
import org.junit.Test

class AppListBaselineProfile {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun generate() = baselineProfileRule.collect(PACKAGE_NAME) {
        startActivityAndWait()

        // Scroll the app list critical user journey
        appListWaitForContent()
        appListScrollDownUp()
    }
}