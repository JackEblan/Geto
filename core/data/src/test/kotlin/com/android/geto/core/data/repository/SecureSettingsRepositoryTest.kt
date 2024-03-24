/*
 *
 *   Copyright 2023 Einstein Blanco
 *
 *   Licensed under the GNU General Public License v3.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.gnu.org/licenses/gpl-3.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.android.geto.core.data.repository

import com.android.geto.core.data.testdoubles.TestSecureSettingsPermissionWrapper
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.SettingType
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class SecureSettingsRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var secureSettingsPermissionWrapper: TestSecureSettingsPermissionWrapper

    private lateinit var subject: SecureSettingsRepository

    @Before
    fun setup() {
        secureSettingsPermissionWrapper = TestSecureSettingsPermissionWrapper()

        subject = DefaultSecureSettingsRepository(secureSettingsPermissionWrapper)
    }

    @Test
    fun secureSettingsRepository_applyGlobalSettings() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(true)

        val appSettings = listOf(
            AppSetting(
                enabled = true, settingType = SettingType.GLOBAL,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        val result = subject.applySecureSettings(appSettings)

        assertTrue(result)
    }

    @Test
    fun secureSettingsRepository_applySecureSettings() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(true)

        val appSettings = listOf(
            AppSetting(
                enabled = true, settingType = SettingType.SECURE,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        val result = subject.applySecureSettings(appSettings)

        assertTrue(result)
    }

    @Test
    fun secureSettingsRepository_applySystemSettings() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(true)

        val appSettings = listOf(
            AppSetting(
                enabled = true, settingType = SettingType.SYSTEM,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        val result = subject.applySecureSettings(appSettings)

        assertTrue(result)
    }

    @Test
    fun secureSettingsRepository_revertGlobalSettings() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(true)

        val appSettings = listOf(
            AppSetting(
                enabled = true, settingType = SettingType.GLOBAL,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        val result = subject.revertSecureSettings(appSettings)

        assertTrue(result)
    }

    @Test
    fun secureSettingsRepository_revertSecureSettings() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(true)

        val appSettings = listOf(
            AppSetting(
                enabled = true, settingType = SettingType.SECURE,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        val result = subject.revertSecureSettings(appSettings)

        assertTrue(result)
    }

    @Test
    fun secureSettingsRepository_revertSystemSettings() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(true)

        val appSettings = listOf(
            AppSetting(
                enabled = true, settingType = SettingType.SYSTEM,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        val result = subject.revertSecureSettings(appSettings)

        assertTrue(result)
    }

    @Test
    fun secureSettingsRepository_applyGlobalSettings_isFailure() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(false)

        val appSettings = listOf(
            AppSetting(
                enabled = true, settingType = SettingType.GLOBAL,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        assertFailsWith<SecurityException> { subject.applySecureSettings(appSettings) }
    }

    @Test
    fun secureSettingsRepository_applySecureSettings_isFailure() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(false)

        val appSettings = listOf(
            AppSetting(
                enabled = true, settingType = SettingType.SECURE,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        assertFailsWith<SecurityException> { subject.applySecureSettings(appSettings) }
    }

    @Test
    fun secureSettingsRepository_applySystemSettings_isFailure() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(false)

        val appSettings = listOf(
            AppSetting(
                enabled = true, settingType = SettingType.SYSTEM,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        assertFailsWith<SecurityException> { subject.applySecureSettings(appSettings) }
    }

    @Test
    fun secureSettingsRepository_revertGlobalSettings_isFailure() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(false)

        val appSettings = listOf(
            AppSetting(
                enabled = true, settingType = SettingType.GLOBAL,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        assertFailsWith<SecurityException> { subject.revertSecureSettings(appSettings) }
    }

    @Test
    fun secureSettingsRepository_revertSecureSettings_isFailure() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(false)

        val appSettings = listOf(
            AppSetting(
                enabled = true, settingType = SettingType.SECURE,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        assertFailsWith<SecurityException> { subject.revertSecureSettings(appSettings) }
    }

    @Test
    fun secureSettingsRepository_revertSystemSettings_isFailure() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(false)

        val appSettings = listOf(
            AppSetting(
                enabled = true, settingType = SettingType.SYSTEM,
                packageName = "com.android.geto",
                label = "Test",
                key = "test",
                valueOnLaunch = "0",
                valueOnRevert = "1"
            )
        )

        assertFailsWith<SecurityException> { subject.revertSecureSettings(appSettings) }
    }
}