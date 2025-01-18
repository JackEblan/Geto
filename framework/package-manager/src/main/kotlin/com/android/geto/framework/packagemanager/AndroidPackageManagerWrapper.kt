/*
 *
 *   Copyright 2023 Einstein Blanco
 *
 *   Licensed under the GNU General Public License v3.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.gnu.org/licenses/gpl-3.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */
package com.android.geto.framework.packagemanager

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import com.android.geto.domain.common.annotations.Dispatcher
import com.android.geto.domain.common.annotations.GetoDispatchers.Default
import com.android.geto.domain.common.annotations.GetoDispatchers.IO
import com.android.geto.domain.framework.PackageManagerWrapper
import com.android.geto.domain.model.GetoApplicationInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class AndroidPackageManagerWrapper @Inject constructor(
    @Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
) : PackageManagerWrapper {

    private val packageManager = context.packageManager

    private val iconsDirectory = File(context.filesDir, "icons")

    override suspend fun queryIntentActivities(): List<GetoApplicationInfo> {
        val intent = Intent().apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        return withContext(defaultDispatcher) {
            val intentActivities =
                packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)
                    .map { resolveInfo ->
                        resolveInfo.activityInfo.applicationInfo.toGetoApplicationInfo()
                    }

            intentActivities.filter { applicationInfo ->
                (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0
            }.sortedBy { applicationInfo -> applicationInfo.label }
        }
    }

    override fun launchIntentForPackage(packageName: String) {
        val intent = packageManager.getLaunchIntentForPackage(packageName)?.apply {
            flags = FLAG_ACTIVITY_NEW_TASK
        }

        try {
            context.startActivity(intent)
        } catch (_: ActivityNotFoundException) {
        }
    }

    override suspend fun deleteIconPath(packageName: String) {
        withContext(ioDispatcher) {
            val iconFile = File(iconsDirectory, packageName)

            if (iconsDirectory.exists().not() || iconFile.exists().not()) {
                return@withContext
            }

            iconFile.delete()
        }
    }

    private suspend fun ApplicationInfo.toGetoApplicationInfo(): GetoApplicationInfo {
        return GetoApplicationInfo(
            flags = flags,
            iconPath = loadIcon(packageManager).toPath(packageName = packageName),
            packageName = packageName,
            label = loadLabel(packageManager).toString(),
        )
    }

    private suspend fun Drawable.toPath(packageName: String): String {
        if (iconsDirectory.exists().not()) {
            iconsDirectory.mkdir()
        }

        val iconFile = File(iconsDirectory, packageName)

        return if (iconFile.exists()) {
            iconFile.absolutePath
        } else {
            withContext(ioDispatcher) {
                FileOutputStream(iconFile).use { outputStream ->
                    toBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                }
            }

            iconFile.absolutePath
        }
    }
}
