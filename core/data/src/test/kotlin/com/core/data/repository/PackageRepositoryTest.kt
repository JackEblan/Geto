package com.core.data.repository

import com.core.domain.repository.PackageRepository
import com.core.testing.util.TestPackageManagerWrapper
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull

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
    fun getNonSystemApps_returnsFirstItemNotNull() = runTest(testDispatcher) {
        assertNotNull(subject.getNonSystemApps().first())
    }
}