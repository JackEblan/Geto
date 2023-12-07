package com.core.testing.repository

import android.annotation.SuppressLint
import android.content.Intent
import com.core.model.AppItem
import com.core.systemmanagers.PackageManagerHelper

class TestPackageManagerHelper : PackageManagerHelper {
    @SuppressLint("QueryPermissionsNeeded")
    override suspend fun getNonSystemAppList(): List<AppItem> {
        return emptyList()
    }

    override fun getLaunchIntentForPackage(packageName: String): Intent? {
        return null
    }
}