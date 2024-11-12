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
package com.android.geto.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.android.geto.core.database.AppDatabase
import com.android.geto.core.database.model.AppSettingEntity
import com.android.geto.core.domain.model.SettingType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AppSettingsDaoTest {
    private lateinit var appSettingsDao: AppSettingsDao

    private lateinit var db: AppDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java,
        ).build()

        appSettingsDao = db.appSettingsDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun getAppSettingEntitiesByPackageName() = runTest {
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

        val appSettingEntitiesByPackageName =
            appSettingsDao.getAppSettingEntitiesByPackageName("com.android.geto").first()

        assertTrue {
            appSettingEntitiesByPackageName.containsAll(appSettingEntities)
        }
    }

    @Test
    fun deleteAppSettingEntitiesByPackageName() = runTest {
        val oldAppSettingsEntities = List(10) { index ->
            AppSettingEntity(
                id = index,
                enabled = false,
                settingType = SettingType.GLOBAL,
                packageName = "com.android.geto.old",
                label = "Geto",
                key = "Geto",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        val newAppSettingsEntities = List(10) { index ->
            AppSettingEntity(
                id = index + 11,
                enabled = false,
                settingType = SettingType.GLOBAL,
                packageName = "com.android.geto.new",
                label = "Geto",
                key = "Geto",
                valueOnLaunch = "0",
                valueOnRevert = "1",
            )
        }

        appSettingsDao.upsertAppSettingEntities(oldAppSettingsEntities + newAppSettingsEntities)

        appSettingsDao.deleteAppSettingEntitiesByPackageName(packageNames = listOf("com.android.geto.old"))

        val appSettingsList = appSettingsDao.getAppSettingEntities().first()

        assertFalse {
            appSettingsList.containsAll(oldAppSettingsEntities)
        }

        assertTrue {
            appSettingsList.containsAll(newAppSettingsEntities)
        }
    }
}
