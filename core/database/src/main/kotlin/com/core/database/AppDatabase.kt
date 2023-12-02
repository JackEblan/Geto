package com.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.core.database.dao.UserAppSettingsDao
import com.core.database.model.UserAppSettingsItemEntity

@Database(
    entities = [UserAppSettingsItemEntity::class], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val userAppSettingsDao: UserAppSettingsDao

    companion object {
        const val DATABASE_NAME = "Geto.db"
    }

}