package com.core.systemmanagers.packagemanager

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import com.core.common.di.DefaultDispatcher
import com.core.model.AppItem
import com.core.systemmanagers.PackageManagerHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class PackageManagerHelperImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val packageManager: PackageManager,
    private val byteArrayOutputStream: ByteArrayOutputStream
) : PackageManagerHelper {
    @SuppressLint("QueryPermissionsNeeded")
    override suspend fun getNonSystemAppList(): List<AppItem> {
        return packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .filter { (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0 }.map {
                val label = packageManager.getApplicationLabel(it).toString()

                val icon = try {
                    packageManager.getApplicationIcon(it.packageName)
                } catch (e: NameNotFoundException) {
                    null
                }

                val iconByteArray = withContext(defaultDispatcher) {
                    icon?.toBitmap()
                        ?.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)

                    byteArrayOutputStream.toByteArray()
                }

                byteArrayOutputStream.reset()

                AppItem(icon = iconByteArray, packageName = it.packageName, label = label)
            }.sortedBy { it.label }
    }
}