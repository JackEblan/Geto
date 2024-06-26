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
import com.android.geto.core.database.model.AppSettingEntity
import com.android.geto.core.database.model.asExternalModel
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.SettingType
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

        subject = DefaultAppSettingsRepository(appSettingsDao = appSettingsDao)
    }

    @Test
    fun upsertAppSetting() = runTest {
        val appSetting = AppSetting(
            id = 0,
            enabled = true,
            settingType = SettingType.SECURE,
            packageName = "com.android.geto",
            label = "Geto",
            key = "Geto",
            valueOnLaunch = "0",
            valueOnRevert = "1",
        )

        subject.upsertAppSetting(appSetting)

        assertTrue {
            appSetting in subject.getAppSettingsByPackageName("com.android.geto").first()
        }
    }

    @Test
    fun deleteAppSetting() = runTest {
        val appSettingEntities = List(10) { index ->
            AppSettingEntity(
                id = index,
                enabled = false,
                settingType = SettingType.GLOBAL,
                packageName = "com.android.geto",
                label = "Geto",
                key = "Geto",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        appSettingsDao.upsertAppSettingEntities(appSettingEntities)

        subject.deleteAppSetting(appSettingEntities.first().asExternalModel())

        assertTrue { subject.appSettings.first().size == appSettingEntities.size - 1 }
    }

    @Test
    fun deleteAppSettingsByPackageName() = runTest {
        val oldAppSettingEntities = List(10) { index ->
            AppSettingEntity(
                id = index,
                enabled = false,
                settingType = SettingType.GLOBAL,
                packageName = "com.android.geto",
                label = "Geto",
                key = "Geto",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        val newAppSettingEntities = List(10) { index ->
            AppSettingEntity(
                id = index + 11,
                enabled = false,
                settingType = SettingType.GLOBAL,
                packageName = "com.android.sample",
                label = "Sample",
                key = "Sample",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        appSettingsDao.upsertAppSettingEntities(oldAppSettingEntities + newAppSettingEntities)

        subject.deleteAppSettingsByPackageName(listOf("com.android.geto"))

        assertFalse {
            subject.appSettings.first()
                .containsAll(oldAppSettingEntities.map(AppSettingEntity::asExternalModel))
        }

        assertTrue {
            subject.appSettings.first()
                .containsAll(newAppSettingEntities.map(AppSettingEntity::asExternalModel))
        }
    }

    @Test
    fun getAppSettingsByPackageName() = runTest {
        val appSettingEntities = List(10) { index ->
            AppSettingEntity(
                id = index,
                enabled = false,
                settingType = SettingType.GLOBAL,
                packageName = "com.android.geto",
                label = "Geto",
                key = "Geto",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        appSettingsDao.upsertAppSettingEntities(appSettingEntities)

        assertTrue {
            subject.getAppSettingsByPackageName("com.android.geto").first()
                .containsAll(appSettingEntities.map(AppSettingEntity::asExternalModel))
        }
    }
}
