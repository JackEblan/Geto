package com.core.data.repository.fake

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import com.core.common.di.DefaultDispatcher
import com.core.domain.repository.UserAppListRepository
import com.core.model.AppItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FakeUserAppListRepository @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val packageManager: PackageManager
) : UserAppListRepository {
    override suspend fun getUserAppList(): List<AppItem> {
        val userAppList = withContext(defaultDispatcher) {
            List(30) { index ->
                val appIcon = packageManager.getApplicationIcon("com.android.geto")

                val appIconToBitmap = Bitmap.createBitmap(
                    appIcon.intrinsicWidth, appIcon.intrinsicHeight, Bitmap.Config.ARGB_8888
                )

                val canvas = Canvas(appIconToBitmap)
                appIcon.setBounds(0, 0, canvas.width, canvas.height)
                appIcon.draw(canvas)

                AppItem(
                    icon = appIconToBitmap, packageName = "com.android.geto", label = "Geto"
                )
            }
        }
        return userAppList
    }
}