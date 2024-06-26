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
package com.android.geto.core.data.repository

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import com.android.geto.core.common.Dispatcher
import com.android.geto.core.common.GetoDispatchers.IO
import com.android.geto.core.model.MappedApplicationInfo
import com.android.geto.framework.packagemanager.PackageManagerWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class DefaultPackageRepository @Inject constructor(
    private val packageManagerWrapper: PackageManagerWrapper,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : PackageRepository {

    override suspend fun queryIntentActivities(intent: Intent, flags: Int): List<MappedApplicationInfo> {
        return withContext(ioDispatcher) {
            packageManagerWrapper.queryIntentActivities(intent, flags).filter { applicationInfo ->
                (applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) == 0
            }.sortedBy { it.label }
        }
    }

    override suspend fun getApplicationIcon(packageName: String): Drawable? {
        return withContext(ioDispatcher) {
            try {
                packageManagerWrapper.getApplicationIcon(packageName)
            } catch (e: PackageManager.NameNotFoundException) {
                null
            }
        }
    }

    override fun getLaunchIntentForPackage(packageName: String): Intent? {
        return packageManagerWrapper.getLaunchIntentForPackage(packageName)
    }
}
