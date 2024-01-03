package com.core.domain.util

import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable

interface PackageManagerWrapper {

    fun getInstalledApplications(): List<ApplicationInfo>

    fun getApplicationLabel(applicationInfo: ApplicationInfo): String

    fun getApplicationIcon(applicationInfo: ApplicationInfo): Drawable
}