package com.android.geto.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.geto.core.model.AppSettings
import com.android.geto.core.model.SettingsType

@Entity
data class AppSettingsEntity(
    @PrimaryKey val id: Int? = null,
    val enabled: Boolean,
    val settingsType: SettingsType,
    val packageName: String,
    val label: String,
    val key: String,
    val valueOnLaunch: String,
    val valueOnRevert: String,
    @ColumnInfo(name = "safeToWrite", defaultValue = "1") val safeToWrite: Boolean
)

fun AppSettingsEntity.asExternalModel(): AppSettings {
    return AppSettings(
        id = id,
        enabled = enabled,
        settingsType = settingsType,
        packageName = packageName,
        label = label,
        key = key,
        valueOnLaunch = valueOnLaunch,
        valueOnRevert = valueOnRevert,
        safeToWrite = safeToWrite
    )
}

fun AppSettings.asEntity(): AppSettingsEntity {
    return AppSettingsEntity(
        id = id,
        enabled = enabled,
        settingsType = settingsType,
        packageName = packageName,
        label = label,
        key = key,
        valueOnLaunch = valueOnLaunch,
        valueOnRevert = valueOnRevert,
        safeToWrite = safeToWrite
    )
}
