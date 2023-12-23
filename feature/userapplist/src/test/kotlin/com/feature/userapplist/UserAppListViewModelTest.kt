package com.feature.userapplist

import com.core.testing.repository.TestPackageRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class UserAppListViewModelTest {
    private lateinit var testPackageRepository: TestPackageRepository

    @Before
    fun setUp() {
        testPackageRepository = TestPackageRepository()
    }

    @Test
    fun `Get Non System apps`() = runTest {
        val appList = testPackageRepository.getNonSystemApps()

        assertTrue { appList.isNotEmpty() }
    }
}