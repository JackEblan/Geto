package com.android.geto.core.data.repository

import android.content.pm.ApplicationInfo
import com.android.geto.core.domain.repository.PackageRepository
import com.android.geto.core.testing.wrapper.TestPackageManagerWrapper
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
    fun getNonSystemAppsIsNotEmpty_whenFlagsIsZero() = runTest(testDispatcher) {
        packageManagerWrapper.setInstalledApplications(listOf(ApplicationInfo().apply {
            packageName = "Test"
            flags = 0
        }))

        val nonSystemApps = subject.getNonSystemApps()

        assertTrue { nonSystemApps.isNotEmpty() }
    }

    @Test
    fun getNonSystemAppsIsEmpty_whenFlagsIsOne() = runTest(testDispatcher) {
        packageManagerWrapper.setInstalledApplications(listOf(ApplicationInfo().apply {
            packageName = "Test"
            flags = 1
        }))

        val nonSystemApps = subject.getNonSystemApps()

        assertTrue { nonSystemApps.isEmpty() }
    }
}