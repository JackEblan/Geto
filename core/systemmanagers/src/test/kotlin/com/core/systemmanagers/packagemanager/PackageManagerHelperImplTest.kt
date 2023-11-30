package com.core.systemmanagers.packagemanager

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@RunWith(MockitoJUnitRunner::class)
class PackageManagerHelperImplTest {
    @Mock
    private lateinit var mockPackageManager: PackageManager

    @Mock
    private lateinit var mockPackageManagerHelperImpl: PackageManagerHelperImpl

    @Before
    fun setup() {
        mockPackageManager = mock {
            val userApp = mock<ApplicationInfo> {
                it.packageName = "com.android.sample.userapp"
                it.flags = 0
            }

            val applicationInfoList = listOf(userApp)

            on { getInstalledApplications(PackageManager.GET_META_DATA) } doReturn applicationInfoList

            on { getApplicationLabel(userApp) } doReturn "User App"
        }

        mockPackageManagerHelperImpl = PackageManagerHelperImpl(mockPackageManager)
    }

    @Test
    fun `filter non-system apps, return true if the applicationInfoList is not empty`() {
        assertThat(mockPackageManagerHelperImpl.getNonSystemAppList()).isNotEmpty()
    }
}