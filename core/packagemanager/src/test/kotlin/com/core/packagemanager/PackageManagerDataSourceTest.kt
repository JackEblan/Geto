package com.core.packagemanager

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class PackageManagerDataSourceTest {
    @Mock
    private lateinit var mockPackageManager: PackageManager

    private lateinit var packageManagerDataSource: PackageManagerDataSource

    private lateinit var testDispatcher: TestDispatcher

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        testDispatcher = StandardTestDispatcher()

        Dispatchers.setMain(testDispatcher)

        mockPackageManager = mock {
            val userAppWithAppIcon = mock<ApplicationInfo> {
                it.packageName = "com.android.sample.userapp1"
                it.flags = 0
            }

            val userAppWithoutAppIcon = mock<ApplicationInfo> {
                it.packageName = "com.android.sample.userapp2"
                it.flags = 0
            }

            val applicationInfoList = listOf(userAppWithAppIcon, userAppWithoutAppIcon)

            on { getInstalledApplications(PackageManager.GET_META_DATA) } doReturn applicationInfoList

            on { getApplicationLabel(userAppWithAppIcon) } doReturn "User App1"

            on { getApplicationLabel(userAppWithoutAppIcon) } doReturn "User App2"

            on { getApplicationIcon(userAppWithoutAppIcon.packageName) } doThrow (PackageManager.NameNotFoundException())
        }

        packageManagerDataSource = PackageManagerDataSourceImpl(
            packageManager = mockPackageManager,
            ioDispatcher = testDispatcher
        )
    }

    @Test
    fun `filter non-system apps`() = runTest(testDispatcher) {
        assertTrue { packageManagerDataSource.getNonSystemApps().isNotEmpty() }
    }
}