package com.core.systemmanagers.packagemanager

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.core.model.AppItem
import com.core.systemmanagers.PackageManagerHelper
import javax.inject.Inject

class PackageManagerHelperImpl @Inject constructor(
    private val packageManager: PackageManager
) : PackageManagerHelper {
    @SuppressLint("QueryPermissionsNeeded")
    override fun getNonSystemAppList(): List<AppItem> {
        return packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0 }.map {
                val label = packageManager.getApplicationLabel(it).toString()

                AppItem(packageName = it.packageName, label = label)
            }.sortedBy { it.label }
    }
}