package com.core.data.repository

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import com.core.common.di.DefaultDispatcher
import com.core.domain.repository.UserAppListRepository
import com.core.model.AppItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserAppListRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val packageManager: PackageManager
) : UserAppListRepository {

    override suspend fun getUserAppList(): List<AppItem> {
        return packageManager.getInstalledApplications(PackageManager.GET_META_DATA).toAppItemList()
    }

    private suspend fun List<ApplicationInfo>.toAppItemList(): List<AppItem> {
        return withContext(defaultDispatcher) {
            filter { (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0 }.map {
                val label = packageManager.getApplicationLabel(it)

                val appIcon = packageManager.getApplicationIcon(it.packageName)

                val appIconToBitmap = Bitmap.createBitmap(
                    appIcon.intrinsicWidth, appIcon.intrinsicHeight, Bitmap.Config.ARGB_8888
                )

                val canvas = Canvas(appIconToBitmap)
                appIcon.setBounds(0, 0, canvas.width, canvas.height)
                appIcon.draw(canvas)

                AppItem(
                    icon = appIconToBitmap, packageName = it.packageName, label = label.toString()
                )
            }.sortedBy { it.label }
        }
    }
}