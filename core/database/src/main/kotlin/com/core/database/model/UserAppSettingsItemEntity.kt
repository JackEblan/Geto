package com.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.core.model.SettingsType

@Entity
data class UserAppSettingsItemEntity(
    val enabled: Boolean,
    val settingsType: SettingsType,
    val packageName: String,
    val label: String,
    @PrimaryKey val key: String,
    val valueOnLaunch: String,
    val valueOnRevert: String
)
