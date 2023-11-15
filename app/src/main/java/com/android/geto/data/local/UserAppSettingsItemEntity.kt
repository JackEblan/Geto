package com.android.geto.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.geto.common.SettingsType

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
