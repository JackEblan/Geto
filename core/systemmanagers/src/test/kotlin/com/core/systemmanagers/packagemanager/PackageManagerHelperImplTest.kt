package com.core.systemmanagers.packagemanager

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.io.ByteArrayOutputStream

@RunWith(MockitoJUnitRunner::class)
class PackageManagerHelperImplTest {
    @Mock
    private lateinit var mockPackageManager: PackageManager

    @Mock
    private lateinit var mockByteArrayOutputStream: ByteArrayOutputStream

    @Mock
    private lateinit var mockPackageManagerHelperImpl: PackageManagerHelperImpl

    private lateinit var testCoroutineScheduler: TestCoroutineScheduler

    @Before
    fun setup() {
        testCoroutineScheduler = TestCoroutineScheduler()

        mockPackageManager = mock {
            val userApp = mock<ApplicationInfo> {
                it.packageName = "com.android.sample.userapp"
                it.flags = 0
            }

            val applicationInfoList = listOf(userApp)

            on { getInstalledApplications(PackageManager.GET_META_DATA) } doReturn applicationInfoList

            on { getApplicationLabel(userApp) } doReturn "User App"
        }
    }

    @Test
    fun `filter non-system apps, return true if the applicationInfoList is not empty`() {
        val testDispatcher = StandardTestDispatcher(testCoroutineScheduler)

        runTest(testDispatcher) {
            mockPackageManagerHelperImpl = PackageManagerHelperImpl(
                defaultDispatcher = testDispatcher,
                packageManager = mockPackageManager,
                byteArrayOutputStream = mockByteArrayOutputStream
            )

            assertThat(mockPackageManagerHelperImpl.getNonSystemAppList()).isNotEmpty()
        }
    }
}