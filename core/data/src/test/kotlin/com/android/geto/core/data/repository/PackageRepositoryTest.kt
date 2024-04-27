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
package com.android.geto.core.data.repository

import android.content.Intent
import com.android.geto.core.data.testdoubles.TestPackageManagerWrapper
import com.android.geto.core.model.MappedApplicationInfo
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PackageRepositoryTest {
    private lateinit var packageManagerWrapper: TestPackageManagerWrapper

    private lateinit var subject: PackageRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        packageManagerWrapper = TestPackageManagerWrapper()

        subject = DefaultPackageRepository(
            packageManagerWrapper = packageManagerWrapper,
            ioDispatcher = testDispatcher,
        )
    }

    @Test
    fun queryIntentActivities_isNotEmpty() = runTest(testDispatcher) {
        val mappedApplicationInfos = List(20) { index ->
            MappedApplicationInfo(
                flags = 0,
                packageName = "com.android.geto$index",
                label = "Geto $index",
            )
        }
        packageManagerWrapper.setMappedApplicationInfos(mappedApplicationInfos)

        assertTrue(subject.queryIntentActivities(intent = Intent(), flags = 0).isNotEmpty())
    }

    @Test
    fun queryIntentActivities_isEmpty() = runTest(testDispatcher) {
        val mappedApplicationInfos = List(20) { index ->
            MappedApplicationInfo(
                flags = 1,
                packageName = "com.android.geto$index",
                label = "Geto $index",
            )
        }

        packageManagerWrapper.setMappedApplicationInfos(mappedApplicationInfos)

        assertTrue(subject.queryIntentActivities(intent = Intent(), flags = 0).isEmpty())
    }

    @Test
    fun getApplicationIcon_isNull() = runTest(testDispatcher) {
        val mappedApplicationInfos = List(20) { index ->
            MappedApplicationInfo(
                flags = 0,
                packageName = "com.android.geto$index",
                label = "Geto $index",
            )
        }

        packageManagerWrapper.setMappedApplicationInfos(mappedApplicationInfos)

        assertNull(subject.getApplicationIcon(""))
    }

    @Test
    fun getApplicationIcon_isNotNull() = runTest(testDispatcher) {
        val mappedApplicationInfos = List(20) { index ->
            MappedApplicationInfo(
                flags = 0,
                packageName = "com.android.geto$index",
                label = "Geto $index",
            )
        }

        packageManagerWrapper.setMappedApplicationInfos(mappedApplicationInfos)

        assertNotNull(subject.getApplicationIcon("com.android.geto1"))
    }

    @Test
    fun getLaunchIntentForPackage_isNull() = runTest(testDispatcher) {
        val mappedApplicationInfos = List(20) { index ->
            MappedApplicationInfo(
                flags = 0,
                packageName = "com.android.geto$index",
                label = "Geto $index",
            )
        }

        packageManagerWrapper.setMappedApplicationInfos(mappedApplicationInfos)

        assertNull(subject.getLaunchIntentForPackage(""))
    }

    @Test
    fun getLaunchIntentForPackage_isNotNull() = runTest(testDispatcher) {
        val mappedApplicationInfos = List(20) { index ->
            MappedApplicationInfo(
                flags = 0,
                packageName = "com.android.geto$index",
                label = "Geto $index",
            )
        }

        packageManagerWrapper.setMappedApplicationInfos(mappedApplicationInfos)

        assertNotNull(subject.getLaunchIntentForPackage("com.android.geto1"))
    }
}
