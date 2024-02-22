package com.android.geto.core.domain.wrapper

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable

interface PackageManagerWrapper {

    fun getInstalledApplications(): List<ApplicationInfo>

    fun getApplicationLabel(applicationInfo: ApplicationInfo): String

    fun getApplicationIcon(applicationInfo: ApplicationInfo): Drawable

    fun getLaunchIntentForPackage(packageName: String): Intent?
}