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
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.SettingType
import org.junit.Test
import kotlin.test.assertEquals

class AppSettingEntityTest {

    @Test
    fun appSetting_asEntity() {
        val appSetting = AppSetting(
            id = 0,
            enabled = false,
            settingType = SettingType.SECURE,
            packageName = "com.android.geto",
            label = "Geto",
            key = "Geto",
            valueOnLaunch = "0",
            valueOnRevert = "1",
        )

        val entity = appSetting.asEntity()

        assertEquals(0, entity.id)
        assertEquals(false, entity.enabled)
        assertEquals(SettingType.SECURE, entity.settingType)
        assertEquals("com.android.geto", entity.packageName)
        assertEquals("Geto", entity.label)
        assertEquals("Geto", entity.key)
        assertEquals("0", entity.valueOnLaunch)
        assertEquals("1", entity.valueOnRevert)
    }
}
