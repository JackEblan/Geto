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

import com.android.geto.core.data.testdoubles.TestAppSettingsDao
import com.android.geto.core.database.model.asEntity
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SettingsType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AppSettingsRepositoryTest {

    private lateinit var appSettingsDao: TestAppSettingsDao

    private lateinit var subject: AppSettingsRepository

    @Before
    fun setup() {
        appSettingsDao = TestAppSettingsDao()

        subject = DefaultAppSettingsRepository(appSettingsDao)
    }

    @Test
    fun appSettingsRepository_upsert_app_settings_delegates_to_dao() = runTest {
        val appSettings = AppSettings(
            id = 0,
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "packageName",
            label = "label",
            key = "key",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        subject.upsertAppSettings(appSettings)

        assertTrue {
            appSettings.asEntity() in appSettingsDao.getAppSettingsList("packageName").first()
        }
    }

    @Test
    fun appSettingsRepository_delete_app_settings_delegates_to_dao() = runTest {
        val appSettings = AppSettings(
            id = 0,
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "packageName",
            label = "label",
            key = "key",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        subject.upsertAppSettings(appSettings)

        subject.deleteAppSettings(appSettings)

        assertFalse {
            appSettings.asEntity() in appSettingsDao.getAppSettingsList("packageName").first()

        }
    }

    @Test
    fun appSettingsRepository_get_app_settings_list() = runTest {
        val packageName = "packageNameToFilter"

        val appSettings = AppSettings(
            id = 0,
            enabled = true,
            settingsType = SettingsType.SECURE,
            packageName = "packageNameToFilter",
            label = "label",
            key = "key",
            valueOnLaunch = "0",
            valueOnRevert = "1"
        )

        subject.upsertAppSettings(appSettings)

        val result = subject.getAppSettingsList(packageName).first()

        assertTrue(result.isNotEmpty())
    }
}