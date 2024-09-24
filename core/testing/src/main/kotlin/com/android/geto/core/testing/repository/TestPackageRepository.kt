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
package com.android.geto.core.testing.repository

import android.graphics.Bitmap
import com.android.geto.core.data.repository.PackageRepository
import com.android.geto.core.model.ApplicationInfo

class TestPackageRepository : PackageRepository {
    private var applicationInfos = listOf<ApplicationInfo>()

    override suspend fun queryIntentActivities(): List<ApplicationInfo> {
        return applicationInfos.filter { it.flags == 0 }.sortedBy { it.label }
    }

    override fun getApplicationIcon(packageName: String): Bitmap? {
        return if (applicationInfos.any { it.packageName == packageName }) Bitmap.createBitmap(
            100,
            100,
            Bitmap.Config.ARGB_8888,
        )
        else null
    }

    override fun launchIntentForPackage(packageName: String) {
    }

    fun setApplicationInfos(value: List<ApplicationInfo>) {
        applicationInfos = value
    }
}
