package com.core.data.repository

import com.core.data.testdoubles.TestPackageManagerDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class PackageRepositoryTest {
    private lateinit var testPackageManagerDataSource: TestPackageManagerDataSource

    @Before
    fun setUp() {
        testPackageManagerDataSource = TestPackageManagerDataSource()
    }

    @Test
    fun `Get Non System apps`() = runTest {
        val appList = testPackageManagerDataSource.getNonSystemApps()

        assertTrue { appList.isNotEmpty() }
    }

}