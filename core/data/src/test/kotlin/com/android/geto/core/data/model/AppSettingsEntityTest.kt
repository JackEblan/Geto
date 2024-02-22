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

package com.android.geto.core.data.model

import com.android.geto.core.database.model.asEntity
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SettingsType
import org.junit.Test
import kotlin.test.assertEquals

class AppSettingsEntityTest {

    @Test
    fun appSettings_canBeMappedToEntity() {
        val appSettings = AppSettings(
            id = 1,
            enabled = false,
            settingsType = SettingsType.SECURE,
            packageName = "packageName",
            label = "label",
            key = "key",
            valueOnLaunch = "valueOnLaunch",
            valueOnRevert = "valueOnRevert",
            safeToWrite = false
        )

        val entity = appSettings.asEntity()

        assertEquals(1, entity.id)
        assertEquals(false, entity.enabled)
        assertEquals(SettingsType.SECURE, entity.settingsType)
        assertEquals("packageName", entity.packageName)
        assertEquals("label", entity.label)
        assertEquals("key", entity.key)
        assertEquals("valueOnLaunch", entity.valueOnLaunch)
        assertEquals("valueOnRevert", entity.valueOnRevert)
    }
}