package com.core.testing.repository

import android.content.pm.ApplicationInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.core.domain.repository.PackageRepository
import com.core.model.AppItem

class TestPackageRepository : PackageRepository {
    private val appList = listOf(ApplicationInfo().apply {
        flags = 0
        packageName = "com.android.geto"
    }, ApplicationInfo().apply {
        flags = 1
        packageName = "com.android.geto1"
    })

    override suspend fun getNonSystemApps(): List<AppItem> {
        return appList.filter { (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0 }.map {
            val label = "Geto"

            val icon = ColorDrawable(Color.GREEN)

            AppItem(icon = icon, packageName = it.packageName, label = label)
        }.sortedBy { it.label }
    }
}