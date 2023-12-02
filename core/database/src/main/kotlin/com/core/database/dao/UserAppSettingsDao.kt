package com.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.core.database.model.UserAppSettingsItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserAppSettingsDao {

    @Upsert
    suspend fun upsert(entity: UserAppSettingsItemEntity)

    @Delete
    suspend fun delete(entity: UserAppSettingsItemEntity)

    @Query("SELECT * FROM UserAppSettingsItemEntity WHERE packageName = :packageName")
    fun getUserAppSettingsList(packageName: String): Flow<List<UserAppSettingsItemEntity>>
}