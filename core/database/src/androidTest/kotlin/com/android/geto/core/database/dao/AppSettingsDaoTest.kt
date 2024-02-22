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
import com.android.geto.core.database.model.AppSettingsEntity
import com.android.geto.core.model.SettingsType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

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

        appSettingsDao = db.appSettingsDao
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun appSettingsDao_filterByPackageName_returnsFilteredList() = runTest {
        val appSettingsEntity1 = AppSettingsEntity(
            enabled = false,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.geto",
            label = "Geto",
            key = "0",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        val appSettingsEntity2 = AppSettingsEntity(
            enabled = false,
            settingsType = SettingsType.GLOBAL,
            packageName = "com.android.settings",
            label = "Settings",
            key = "1",
            valueOnLaunch = "0",
            valueOnRevert = "1",
            safeToWrite = false
        )

        appSettingsDao.upsert(appSettingsEntity1)

        appSettingsDao.upsert(appSettingsEntity2)

        val userAppSettingsList = appSettingsDao.getAppSettingsList("com.android.geto").first()

        assertEquals(
            expected = listOf("com.android.geto"),
            actual = userAppSettingsList.map { it.packageName })
    }
}