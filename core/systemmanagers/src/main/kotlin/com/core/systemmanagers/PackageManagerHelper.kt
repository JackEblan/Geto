package com.core.systemmanagers

import android.content.Intent
import com.core.model.AppItem

interface PackageManagerHelper {
    suspend fun getNonSystemAppList(): List<AppItem>

    fun getLaunchIntentForPackage(packageName: String): Intent?
}