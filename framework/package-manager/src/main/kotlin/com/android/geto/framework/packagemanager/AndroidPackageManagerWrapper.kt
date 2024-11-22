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
import android.os.Build
import androidx.core.graphics.drawable.toBitmap
import com.android.geto.core.common.Dispatcher
import com.android.geto.core.common.GetoDispatchers.Default
import com.android.geto.core.domain.framework.PackageManagerWrapper
import com.android.geto.core.domain.model.GetoApplicationInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class AndroidPackageManagerWrapper @Inject constructor(
    @Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher,
    @ApplicationContext private val context: Context,
) : PackageManagerWrapper {

    private val packageManager = context.packageManager

    override suspend fun queryIntentActivities(): List<GetoApplicationInfo> {
        val intent = Intent().apply {
            action = Intent.ACTION_MAIN
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PackageManager.MATCH_ALL
        } else {
            PackageManager.MATCH_DEFAULT_ONLY
        }

        return withContext(defaultDispatcher) {
            val intentActivities =
                packageManager.queryIntentActivities(intent, flags).map { resolveInfo ->
                    resolveInfo.activityInfo.applicationInfo.toApplicationInfo()
                }

            intentActivities.filter { applicationInfo ->
                (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0
            }.sortedBy { applicationInfo -> applicationInfo.label }
        }
    }

    override suspend fun getApplicationIcon(packageName: String): ByteArray? {
        return try {
            packageManager.getApplicationIcon(packageName).toByteArray()
        } catch (e: PackageManager.NameNotFoundException) {
            null
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

    private suspend fun ApplicationInfo.toApplicationInfo(): GetoApplicationInfo {
        val stream = ByteArrayOutputStream()

        val bitmap = loadIcon(packageManager).toBitmap()

        withContext(defaultDispatcher) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
        }

        return GetoApplicationInfo(
            flags = flags,
            icon = stream.toByteArray(),
            packageName = packageName,
            label = loadLabel(packageManager).toString(),
        )
    }

    private suspend fun Drawable.toByteArray(): ByteArray {
        val stream = ByteArrayOutputStream()

        withContext(defaultDispatcher) {
            toBitmap().compress(Bitmap.CompressFormat.PNG, 90, stream)
        }

        return stream.toByteArray()
    }
}
