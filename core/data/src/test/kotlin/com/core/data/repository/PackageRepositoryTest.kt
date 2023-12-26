package com.core.data.repository

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import com.core.domain.repository.PackageRepository
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import kotlin.test.assertTrue

@RunWith(MockitoJUnitRunner::class)
class PackageRepositoryTest {
    @Mock
    private lateinit var mockPackageManager: PackageManager

    private lateinit var subject: PackageRepository

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

        subject = PackageRepositoryImpl(
            packageManager = mockPackageManager, ioDispatcher = testDispatcher
        )

        assertTrue { subject.getNonSystemApps().isNotEmpty() }
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

            on { getApplicationIcon(userAppWithoutAppIcon.packageName) } doThrow PackageManager.NameNotFoundException()
        }

        subject = PackageRepositoryImpl(
            packageManager = mockPackageManager, ioDispatcher = testDispatcher
        )

        assertTrue { subject.getNonSystemApps().isNotEmpty() }
    }
}