package com.core.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserAppSettingsItemEntity::class], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val userAppSettingsDao: UserAppSettingsDao

    companion object {
        const val DATABASE_NAME = "Geto.db"
    }

}