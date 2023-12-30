package com.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.core.database.model.AppSettingsItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppSettingsDao {

    @Upsert
    suspend fun upsert(entity: AppSettingsItemEntity)

    @Delete
    suspend fun delete(entity: AppSettingsItemEntity)

    @Query("SELECT * FROM AppSettingsItemEntity WHERE packageName = :packageName")
    fun getUserAppSettingsList(packageName: String): Flow<List<AppSettingsItemEntity>>
}