package com.core.domain.usecase.userapplist

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
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
class GetNonSystemAppsTest {
    @Mock
    private lateinit var mockPackageManager: PackageManager

    private lateinit var getNonSystemApps: GetNonSystemApps

    @Before
    fun setUp() {
        getNonSystemApps = GetNonSystemApps()
    }

    @Test
    fun `filter non-system apps`() {
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

            on { getApplicationIcon(userAppWithoutAppIcon.packageName) } doThrow (NameNotFoundException())
        }

        assertTrue { getNonSystemApps(mockPackageManager).isNotEmpty() }
    }
}