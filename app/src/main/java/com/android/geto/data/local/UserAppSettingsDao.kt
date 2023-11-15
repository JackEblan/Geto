package com.android.geto.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
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