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

package com.android.geto.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.geto.core.model.AppSetting
import com.android.geto.core.model.SettingType

@Entity
data class AppSettingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val enabled: Boolean,
    val settingType: SettingType,
    val packageName: String,
    val label: String,
    val key: String,
    val valueOnLaunch: String,
    val valueOnRevert: String
)

fun AppSettingEntity.asExternalModel(): AppSetting {
    return AppSetting(
        id = id ?: 0, enabled = enabled, settingType = settingType,
        packageName = packageName,
        label = label,
        key = key,
        valueOnLaunch = valueOnLaunch,
        valueOnRevert = valueOnRevert
    )
}

fun AppSetting.asEntity(): AppSettingEntity {
    return AppSettingEntity(
        id = id, enabled = enabled, settingType = settingType,
        packageName = packageName,
        label = label,
        key = key,
        valueOnLaunch = valueOnLaunch,
        valueOnRevert = valueOnRevert
    )
}
