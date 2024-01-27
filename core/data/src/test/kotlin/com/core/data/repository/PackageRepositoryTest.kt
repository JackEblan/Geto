package com.core.data.repository

import android.content.pm.ApplicationInfo
import com.core.domain.repository.PackageRepository
import com.core.testing.util.TestPackageManagerWrapper
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class PackageRepositoryTest {
    private lateinit var packageManagerWrapper: TestPackageManagerWrapper

    private lateinit var subject: PackageRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        packageManagerWrapper = TestPackageManagerWrapper()

        subject = DefaultPackageRepository(
            packageManagerWrapper = packageManagerWrapper, defaultDispatcher = testDispatcher
        )
    }

    @Test
    fun getNonSystemApps_withInstalledApplications_returnsNotEmpty() = runTest(testDispatcher) {
        packageManagerWrapper.setInstalledApplications(listOf(ApplicationInfo().apply {
            packageName = "Test"
            flags = 0
        }))

        assertTrue { subject.getNonSystemApps().isNotEmpty() }
    }

    @Test
    fun getNonSystemApps_withoutInstalledApplications_returnsEmpty() = runTest(testDispatcher) {
        packageManagerWrapper.setInstalledApplications(listOf(ApplicationInfo().apply {
            packageName = "Test"
            flags = 1
        }))

        assertTrue { subject.getNonSystemApps().isEmpty() }
    }
}