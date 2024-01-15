package com.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.core.database.dao.AppSettingsDao
import com.core.database.model.AppSettingsItemEntity

@Database(
    entities = [AppSettingsItemEntity::class], version = 4, exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract val appSettingsDao: AppSettingsDao

    companion object {
        const val DATABASE_NAME = "Geto.db"
    }

}