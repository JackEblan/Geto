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

import com.android.geto.core.data.testdoubles.TestPackageManagerWrapper
import com.android.geto.core.model.TargetApplicationInfo
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
    fun setUp() {
        packageManagerWrapper = TestPackageManagerWrapper()

        subject = DefaultPackageRepository(
            packageManagerWrapper = packageManagerWrapper, ioDispatcher = testDispatcher
        )
    }

    @Test
    fun packageRepository_getInstalledApplications_isNotEmpty() = runTest(testDispatcher) {
        val installedApplications = List(20) { index ->
            TargetApplicationInfo(
                flags = 0, packageName = "com.android.geto$index", label = "Geto $index"
            )
        }
        packageManagerWrapper.setInstalledApplications(installedApplications)

        assertTrue { subject.getInstalledApplications().isNotEmpty() }
    }

    @Test
    fun packageRepository_getInstalledApplications_isEmpty() = runTest(testDispatcher) {
        val installedApplications = List(20) { index ->
            TargetApplicationInfo(
                flags = 1, packageName = "com.android.geto$index", label = "Geto $index"
            )
        }

        packageManagerWrapper.setInstalledApplications(installedApplications)

        assertTrue { subject.getInstalledApplications().isEmpty() }
    }

    @Test
    fun packageRepository_getApplicationIcon_isNull() = runTest(testDispatcher) {
        val installedApplications = List(20) { index ->
            TargetApplicationInfo(
                flags = 0, packageName = "com.android.geto$index", label = "Geto $index"
            )
        }

        packageManagerWrapper.setInstalledApplications(installedApplications)

        val applicationIcon = subject.getApplicationIcon("")

        assertNull(applicationIcon)
    }

    @Test
    fun packageRepository_getApplicationIcon_isNotNull() = runTest(testDispatcher) {
        val installedApplications = List(20) { index ->
            TargetApplicationInfo(
                flags = 0, packageName = "com.android.geto$index", label = "Geto $index"
            )
        }

        packageManagerWrapper.setInstalledApplications(installedApplications)

        val applicationIcon = subject.getApplicationIcon("com.android.geto1")

        assertNotNull(applicationIcon)
    }

    @Test
    fun packageRepository_getLaunchIntentForPackage_isNull() = runTest(testDispatcher) {
        val installedApplications = List(20) { index ->
            TargetApplicationInfo(
                flags = 0, packageName = "com.android.geto$index", label = "Geto $index"
            )
        }

        packageManagerWrapper.setInstalledApplications(installedApplications)

        val launchIntentForPackage = subject.getLaunchIntentForPackage("")

        assertNull(launchIntentForPackage)
    }

    @Test
    fun packageRepository_getLaunchIntentForPackage_isNotNull() = runTest(testDispatcher) {
        val installedApplications = List(20) { index ->
            TargetApplicationInfo(
                flags = 0, packageName = "com.android.geto$index", label = "Geto $index"
            )
        }

        packageManagerWrapper.setInstalledApplications(installedApplications)

        val launchIntentForPackage = subject.getLaunchIntentForPackage("com.android.geto1")

        assertNotNull(launchIntentForPackage)
    }
}