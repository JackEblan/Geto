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
package com.android.geto.core.database.migration

import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.RenameTable
import androidx.room.migration.AutoMigrationSpec

@RenameTable(fromTableName = "UserAppSettingsItemEntity", toTableName = "AppSettingsItemEntity")
class RenameUserAppSettingsItemEntityToAppSettingsItemEntity : AutoMigrationSpec

@RenameTable(fromTableName = "AppSettingsItemEntity", toTableName = "AppSettingsEntity")
class RenameAppSettingsItemEntityToAppSettingsEntity : AutoMigrationSpec

@DeleteColumn(tableName = "AppSettingsEntity", columnName = "safeToWrite")
class DeleteSafeToWrite : AutoMigrationSpec

@RenameTable(fromTableName = "AppSettingsEntity", toTableName = "AppSettingEntity")
@RenameColumn(
    tableName = "AppSettingsEntity",
    fromColumnName = "settingsType",
    toColumnName = "settingType",
)
class RenameAppSettingsEntityToAppSettingEntity : AutoMigrationSpec
