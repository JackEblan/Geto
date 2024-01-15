package com.core.testing.util

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.core.domain.util.PackageManagerWrapper

class TestPackageManagerWrapper : PackageManagerWrapper {
    override fun getInstalledApplications(): List<ApplicationInfo> {
        return listOf(ApplicationInfo().apply {
            packageName = ""
            flags = 0
        })
    }

    override fun getApplicationLabel(applicationInfo: ApplicationInfo): String {
        return "Application"
    }

    override fun getApplicationIcon(applicationInfo: ApplicationInfo): Drawable {
        return ColorDrawable()
    }

    override fun getLaunchIntentForPackage(packageName: String): Intent? {
        return Intent()
    }
}