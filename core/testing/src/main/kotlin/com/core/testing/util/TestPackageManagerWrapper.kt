package com.core.testing.util

import android.content.pm.ApplicationInfo
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.core.domain.util.PackageManagerWrapper
import com.core.testing.data.installedApplicationTestData

class TestPackageManagerWrapper : PackageManagerWrapper {
    override fun getInstalledApplications(): List<ApplicationInfo> {
        return installedApplicationTestData
    }

    override fun getApplicationLabel(applicationInfo: ApplicationInfo): String {
        return "Application"
    }

    override fun getApplicationIcon(applicationInfo: ApplicationInfo): Drawable {
        return ColorDrawable()
    }
}