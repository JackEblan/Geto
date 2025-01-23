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
package com.android.geto.framework.packagemanager

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

/** Our package is always be installed to different environment e.g CI so assume we
 * will use that to test against. Robolectric can do test by mocking installed applications
 * but kinda messy to deal with.
 */
class PackageManagerWrapperTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var packageManagerWrapper: AndroidPackageManagerWrapper

    @BeforeTest
    fun setUp() {
        packageManagerWrapper = AndroidPackageManagerWrapper(
            defaultDispatcher = testDispatcher,
            ioDispatcher = testDispatcher,
            context = context,
        )
    }

    @Test
    fun queryIntentActivities_filterInstalledApplications() = runTest {
        assertTrue(
            packageManagerWrapper.queryIntentActivities().all { getoApplicationInfo ->
                (getoApplicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0
            },
        )
    }

    @Test
    fun getApplicationIcon_forGeto() = runTest {
        assertNotNull(packageManagerWrapper.getApplicationIcon(context.packageName))
    }

    @Test
    fun getApplicationIcon_forInvalidPackageName_isNull() = runTest {
        assertNull(packageManagerWrapper.getApplicationIcon(""))
    }

    @Test
    fun queryIntentActivities_filterInstalledApplications_byLabel() = runTest {
        assertTrue(packageManagerWrapper.queryIntentActivitiesByLabel(label = "Geto").isNotEmpty())
    }
}
