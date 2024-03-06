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
    fun packageRepository_get_non_system_apps_is_not_empty() = runTest(testDispatcher) {
        packageManagerWrapper.setInstalledApplications(
            listOf(
                TargetApplicationInfo(
                    flags = 0, packageName = "Test", label = "Test"
                )
            )
        )

        val nonSystemApps = subject.getNonSystemApps()

        assertTrue { nonSystemApps.isNotEmpty() }
    }

    @Test
    fun packageRepository_get_non_system_apps_is_empty() = runTest(testDispatcher) {
        packageManagerWrapper.setInstalledApplications(
            listOf(
                TargetApplicationInfo(
                    flags = 1, packageName = "Test", label = "Test"
                )
            )
        )

        val nonSystemApps = subject.getNonSystemApps()

        assertTrue { nonSystemApps.isEmpty() }
    }

    @Test
    fun packageRepository_get_application_icon_not_found() = runTest(testDispatcher) {
        packageManagerWrapper.setInstalledApplications(
            listOf(
                TargetApplicationInfo(
                    flags = 0, packageName = "Test", label = "Test"
                )
            )
        )

        val applicationIcon = subject.getApplicationIcon("")

        assertNull(applicationIcon)
    }

    @Test
    fun packageRepository_get_application_icon_found() = runTest(testDispatcher) {
        packageManagerWrapper.setInstalledApplications(
            listOf(
                TargetApplicationInfo(
                    flags = 0, packageName = "Test", label = "Test"
                )
            )
        )

        val applicationIcon = subject.getApplicationIcon("Test")

        assertNotNull(applicationIcon)
    }

    @Test
    fun packageRepository_get_launch_intent_for_package_not_found() = runTest(testDispatcher) {
        packageManagerWrapper.setInstalledApplications(
            listOf(
                TargetApplicationInfo(
                    flags = 0, packageName = "Test", label = "Test"
                )
            )
        )

        val applicationIcon = subject.getLaunchIntentForPackage("")

        assertNull(applicationIcon)
    }

    @Test
    fun packageRepository_get_launch_intent_for_package_found() = runTest(testDispatcher) {
        packageManagerWrapper.setInstalledApplications(
            listOf(
                TargetApplicationInfo(
                    flags = 0, packageName = "Test", label = "Test"
                )
            )
        )

        val applicationIcon = subject.getLaunchIntentForPackage("Test")

        assertNotNull(applicationIcon)
    }
}