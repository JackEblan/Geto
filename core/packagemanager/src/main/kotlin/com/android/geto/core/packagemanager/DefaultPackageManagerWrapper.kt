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
package com.android.geto.core.packagemanager

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import com.android.geto.core.model.TargetApplicationInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultPackageManagerWrapper @Inject constructor(@ApplicationContext private val context: Context) :
    PackageManagerWrapper {

    private val packageManager = context.packageManager

    @SuppressLint("QueryPermissionsNeeded")
    override fun getInstalledApplications(): List<TargetApplicationInfo> {
        return packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
            .map { applicationInfo ->
                applicationInfo.asTargetApplicationInfo(packageManager)
            }
    }

    override fun getApplicationIcon(packageName: String): Drawable {
        return packageManager.getApplicationIcon(packageName)
    }

    override fun getLaunchIntentForPackage(packageName: String): Intent? {
        return packageManager.getLaunchIntentForPackage(packageName)
    }
}
