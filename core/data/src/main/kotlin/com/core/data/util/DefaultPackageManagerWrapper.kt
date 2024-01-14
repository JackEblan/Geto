package com.core.data.util

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import com.core.domain.util.PackageManagerWrapper
import javax.inject.Inject

class DefaultPackageManagerWrapper @Inject constructor(private val packageManager: PackageManager) :
    PackageManagerWrapper {
    override fun getInstalledApplications(): List<ApplicationInfo> {
        return packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
    }

    override fun getApplicationLabel(applicationInfo: ApplicationInfo): String {
        return packageManager.getApplicationLabel(applicationInfo).toString()
    }

    override fun getApplicationIcon(applicationInfo: ApplicationInfo): Drawable {
        return packageManager.getApplicationIcon(applicationInfo.packageName)
    }

    override fun getLaunchIntentForPackage(packageName: String): Intent? {
        return packageManager.getLaunchIntentForPackage(packageName)
    }
}