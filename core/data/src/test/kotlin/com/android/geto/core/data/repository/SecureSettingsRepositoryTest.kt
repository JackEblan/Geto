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

import com.android.geto.core.domain.repository.SecureSettingsRepository
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SettingsType
import com.android.geto.core.testing.wrapper.TestSecureSettingsPermissionWrapper
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
    fun resultIsSuccess_whenApplySecureSettings_globalSettingsType_setWriteSecureSettingsIsTrue() =
        runTest(testDispatcher) {
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
    fun resultIsSuccess_whenApplySecureSettings_secureSettingsType_setWriteSecureSettingsIsTrue() =
        runTest(testDispatcher) {
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
    fun resultIsSuccess_whenApplySystemSettings_secureSettingsType_setWriteSecureSettingsIsTrue() =
        runTest(testDispatcher) {
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
    fun resultIsSuccess_whenRevertSecureSettings_globalSettingsType_setWriteSecureSettingsIsTrue() =
        runTest(testDispatcher) {
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
    fun resultIsSuccess_whenRevertSecureSettings_secureSettingsType_setWriteSecureSettingsIsTrue() =
        runTest(testDispatcher) {
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
    fun resultIsSuccess_whenRevertSecureSettings_systemSettingsType_setWriteSecureSettingsIsTrue() =
        runTest(testDispatcher) {
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
    fun resultIsFailure_whenApplySecureSettings_globalSettingsType_setWriteSecureSettingsIsFalse() =
        runTest(testDispatcher) {
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
    fun resultIsFailure_whenApplySecureSettings_secureSettingsType_setWriteSecureSettingsIsFalse() =
        runTest(testDispatcher) {
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
    fun resultIsFailure_whenApplySecureSettings_systemSettingsType_setWriteSecureSettingsIsFalse() =
        runTest(testDispatcher) {
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
    fun resultIsFailure_whenRevertSecureSettings_globalSettingsType_setWriteSecureSettingsIsFalse() =
        runTest(testDispatcher) {
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
    fun resultIsFailure_whenRevertSecureSettings_secureSettingsType_setWriteSecureSettingsIsFalse() =
        runTest(testDispatcher) {
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
    fun resultIsFailure_whenRevertSecureSettings_systemSettingsType_setWriteSecureSettingsIsFalse() =
        runTest(testDispatcher) {
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