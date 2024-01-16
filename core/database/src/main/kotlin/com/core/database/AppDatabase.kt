package com.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.core.database.dao.AppSettingsDao
import com.core.database.migration.RenamedTableFromAppSettingsItemEntityToAppSettingsEntityAutoMigration
import com.core.database.model.AppSettingsEntity

@Database(
    entities = [AppSettingsEntity::class], version = 5, autoMigrations = [AutoMigration(
        from = 4,
        to = 5,
        spec = RenamedTableFromAppSettingsItemEntityToAppSettingsEntityAutoMigration::class
    )], exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val appSettingsDao: AppSettingsDao

    companion object {
        const val DATABASE_NAME = "Geto.db"
    }

}