package com.android.geto.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.android.geto.core.database.model.AppSettingsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppSettingsDao {

    @Upsert
    suspend fun upsert(entity: AppSettingsEntity)

    @Delete
    suspend fun delete(entity: AppSettingsEntity)

    @Query("SELECT * FROM AppSettingsEntity WHERE packageName = :packageName")
    fun getAppSettingsList(packageName: String): Flow<List<AppSettingsEntity>>
}