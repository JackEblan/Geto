package com.core.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserAppSettingsItemEntity(
    val enabled: Boolean,
    val settingsType: Int,
    val packageName: String,
    val label: String,
    @PrimaryKey val key: String,
    val valueOnLaunch: String,
    val valueOnRevert: String
)
