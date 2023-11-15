package com.android.geto.data.mappers

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import com.android.geto.data.local.UserAppSettingsItemEntity
import com.android.geto.domain.model.AppItem
import com.android.geto.domain.model.UserAppSettingsItem

fun List<ApplicationInfo>.toAppItemList(
    packageManager: PackageManager
): List<AppItem> {
    return filter { (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0 }.map {
        val label = packageManager.getApplicationLabel(it)

        val appIcon = packageManager.getApplicationIcon(it.packageName)

        val appIconToBitmap = Bitmap.createBitmap(
            appIcon.intrinsicWidth, appIcon.intrinsicHeight, Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(appIconToBitmap)
        appIcon.setBounds(0, 0, canvas.width, canvas.height)
        appIcon.draw(canvas)

        AppItem(icon = appIconToBitmap, packageName = it.packageName, label = label.toString())
    }.sortedBy { it.label }
}

fun List<UserAppSettingsItemEntity>.toSettingsItemList(): List<UserAppSettingsItem> {
    return map { entity ->
        UserAppSettingsItem(
            enabled = entity.enabled,
            settingsType = entity.settingsType,
            packageName = entity.packageName,
            label = entity.label,
            key = entity.key,
            valueOnLaunch = entity.valueOnLaunch,
            valueOnRevert = entity.valueOnRevert
        )
    }
}

fun UserAppSettingsItem.toSettingsItemEntity(): UserAppSettingsItemEntity {
    return UserAppSettingsItemEntity(
        enabled = enabled,
        settingsType = settingsType,
        packageName = packageName,
        label = label,
        key = key,
        valueOnLaunch = valueOnLaunch,
        valueOnRevert = valueOnRevert
    )
}