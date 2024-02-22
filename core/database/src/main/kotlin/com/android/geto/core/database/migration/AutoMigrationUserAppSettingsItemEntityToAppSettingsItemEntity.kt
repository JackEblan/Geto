package com.android.geto.core.database.migration

import androidx.room.RenameTable
import androidx.room.migration.AutoMigrationSpec

@RenameTable(fromTableName = "UserAppSettingsItemEntity", toTableName = "AppSettingsItemEntity")
class AutoMigrationUserAppSettingsItemEntityToAppSettingsItemEntity : AutoMigrationSpec