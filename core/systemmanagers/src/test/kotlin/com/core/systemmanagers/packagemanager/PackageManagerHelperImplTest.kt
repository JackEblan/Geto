package com.core.systemmanagers.packagemanager

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import java.io.ByteArrayOutputStream
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class PackageManagerHelperImplTest {
    @Mock
    private lateinit var mockPackageManager: PackageManager

    @Mock
    private lateinit var mockByteArrayOutputStream: ByteArrayOutputStream

    private lateinit var mockPackageManagerHelperImpl: PackageManagerHelperImpl

    private lateinit var testDispatcher: TestDispatcher

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        testDispatcher = StandardTestDispatcher()

        Dispatchers.setMain(testDispatcher)

        mockPackageManager = mock {
            val userApp = mock<ApplicationInfo> {
                it.packageName = "com.android.sample.userapp"
                it.flags = 0
            }

            val applicationInfoList = listOf(userApp)

            on { getInstalledApplications(PackageManager.GET_META_DATA) } doReturn applicationInfoList

            on { getApplicationLabel(userApp) } doReturn "User App"
        }

        mockPackageManagerHelperImpl = PackageManagerHelperImpl(
            defaultDispatcher = testDispatcher,
            packageManager = mockPackageManager,
            byteArrayOutputStream = mockByteArrayOutputStream
        )
    }

    @Test
    fun `filter non-system apps, return true if the applicationInfoList is not empty`() =
        runTest(testDispatcher) {
            assertEquals(expected = true,
                         actual = mockPackageManagerHelperImpl.getNonSystemAppList().first()
                             .isNotEmpty()
            )
        }
}