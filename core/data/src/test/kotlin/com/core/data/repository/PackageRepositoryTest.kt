package com.core.data.repository

import com.core.domain.repository.PackageRepository
import com.core.testing.util.TestByteArrayOutputStreamWrapper
import com.core.testing.util.TestPackageManagerWrapper
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class PackageRepositoryTest {
    private lateinit var packageManagerWrapper: TestPackageManagerWrapper

    private lateinit var byteArrayIconWrapper: TestByteArrayOutputStreamWrapper

    private lateinit var subject: PackageRepository

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        packageManagerWrapper = TestPackageManagerWrapper()

        byteArrayIconWrapper = TestByteArrayOutputStreamWrapper()

        subject = DefaultPackageRepository(
            packageManagerWrapper = packageManagerWrapper,
            defaultDispatcher = testDispatcher,
            byteArrayOutputStreamWrapper = byteArrayIconWrapper
        )
    }

    @Test
    fun `filter non-system apps with app icon return not empty`() = runTest(testDispatcher) {
        assertTrue { subject.getNonSystemApps().isNotEmpty() }
    }
}