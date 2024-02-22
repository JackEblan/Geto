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

import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SettingsType
import com.android.geto.core.testing.securesettings.TestSecureSettingsPermissionWrapper
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

class SecureSettingsRepositoryTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var secureSettingsPermissionWrapper: TestSecureSettingsPermissionWrapper

    private lateinit var subject: SecureSettingsRepository

    private val appSettingsList = mutableListOf<AppSettings>()

    @Before
    fun setup() {
        secureSettingsPermissionWrapper = TestSecureSettingsPermissionWrapper()

        subject = DefaultSecureSettingsRepository(secureSettingsPermissionWrapper)
    }

    @Test
    fun secureSettingsRepository_applied_global_settings() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(true)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        appSettingsList.add(appSettings)

        val result = subject.applySecureSettings(appSettingsList)

        assertTrue { result.isSuccess }
    }

    @Test
    fun secureSettingsRepository_applied_secure_settings() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(true)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
                valueOnRevert = "1",
                safeToWrite = false
            )

            appSettingsList.add(appSettings)

            val result = subject.applySecureSettings(appSettingsList)

            assertTrue { result.isSuccess }
        }

    @Test
    fun secureSettingsRepository_applied_system_settings() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(true)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
                valueOnRevert = "1",
                safeToWrite = false
            )

            appSettingsList.add(appSettings)

            val result = subject.applySecureSettings(appSettingsList)

            assertTrue { result.isSuccess }
        }

    @Test
    fun secureSettingsRepository_reverted_global_settings() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(true)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
                valueOnRevert = "1",
                safeToWrite = false
            )

            appSettingsList.add(appSettings)

            val result = subject.revertSecureSettings(appSettingsList)

            assertTrue { result.isSuccess }
        }

    @Test
    fun secureSettingsRepository_reverted_secure_settings() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(true)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
                valueOnRevert = "1",
                safeToWrite = false
            )

            appSettingsList.add(appSettings)

            val result = subject.revertSecureSettings(appSettingsList)

            assertTrue { result.isSuccess }
        }

    @Test
    fun secureSettingsRepository_reverted_system_settings() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(true)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
                valueOnRevert = "1",
                safeToWrite = false
            )

            appSettingsList.add(appSettings)

            val result = subject.revertSecureSettings(appSettingsList)

            assertTrue { result.isSuccess }
        }

    @Test
    fun secureSettingsRepository_failed_to_apply_global_settings() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(false)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
                valueOnRevert = "1",
                safeToWrite = false
            )

            appSettingsList.add(appSettings)

            val result = subject.applySecureSettings(appSettingsList)

            assertTrue { result.isFailure }
        }

    @Test
    fun secureSettingsRepository_failed_to_apply_secure_settings() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(false)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
                valueOnRevert = "1",
                safeToWrite = false
            )

            appSettingsList.add(appSettings)

            val result = subject.applySecureSettings(appSettingsList)

            assertTrue { result.isFailure }
        }

    @Test
    fun secureSettingsRepository_failed_to_apply_system_settings() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(false)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
                valueOnRevert = "1",
                safeToWrite = false
            )

            appSettingsList.add(appSettings)

            val result = subject.applySecureSettings(appSettingsList)

            assertTrue { result.isFailure }
        }

    @Test
    fun secureSettingsRepository_failed_to_revert_global_settings() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(false)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
                valueOnRevert = "1",
                safeToWrite = false
            )

            appSettingsList.add(appSettings)

            val result = subject.revertSecureSettings(appSettingsList)

            assertTrue { result.isFailure }
        }

    @Test
    fun secureSettingsRepository_failed_to_revert_secure_settings() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(false)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
                valueOnRevert = "1",
                safeToWrite = false
            )

            appSettingsList.add(appSettings)

            val result = subject.revertSecureSettings(appSettingsList)

            assertTrue { result.isFailure }
        }

    @Test
    fun secureSettingsRepository_failed_to_revert_system_settings() = runTest(testDispatcher) {
        secureSettingsPermissionWrapper.setWriteSecureSettings(false)

        val appSettings = AppSettings(
            enabled = true,
            settingsType = SettingsType.SYSTEM,
            packageName = "com.android.geto",
            label = "Test",
            key = "test",
            valueOnLaunch = "0",
                valueOnRevert = "1",
                safeToWrite = false
            )

            appSettingsList.add(appSettings)

            val result = subject.revertSecureSettings(appSettingsList)

            assertTrue { result.isFailure }
        }
}