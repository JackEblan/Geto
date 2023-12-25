package com.core.packagemanager

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.graphics.drawable.ColorDrawable
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
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

    private val testDispatcher = StandardTestDispatcher()

    @Test
    fun `filter non-system apps with app icon return not empty`() = runTest(testDispatcher) {
        mockPackageManager = mock {
            val userAppWithAppIcon = mock<ApplicationInfo> {
                it.packageName = "com.android.sample.userapp1"
                it.flags = 0
            }

            val applicationInfoList = listOf(userAppWithAppIcon)

            on { getInstalledApplications(PackageManager.GET_META_DATA) } doReturn applicationInfoList

            on { getApplicationLabel(userAppWithAppIcon) } doReturn "User App1"

            on { getApplicationIcon(userAppWithAppIcon.packageName) } doReturn ColorDrawable()
        }

        packageManagerDataSource = PackageManagerDataSourceImpl(
            packageManager = mockPackageManager, ioDispatcher = testDispatcher
        )

        assertTrue { packageManagerDataSource.getNonSystemApps().isNotEmpty() }
    }

    @Test
    fun `filter non-system apps without app icon return not empty`() = runTest(testDispatcher) {
        mockPackageManager = mock {
            val userAppWithoutAppIcon = mock<ApplicationInfo> {
                it.packageName = "com.android.sample.userapp1"
                it.flags = 0
            }

            val applicationInfoList = listOf(userAppWithoutAppIcon)

            on { getInstalledApplications(PackageManager.GET_META_DATA) } doReturn applicationInfoList

            on { getApplicationLabel(userAppWithoutAppIcon) } doReturn "User App1"

            on { getApplicationIcon(userAppWithoutAppIcon.packageName) } doThrow NameNotFoundException()
        }

        packageManagerDataSource = PackageManagerDataSourceImpl(
            packageManager = mockPackageManager, ioDispatcher = testDispatcher
        )

        assertTrue { packageManagerDataSource.getNonSystemApps().isNotEmpty() }
    }
}