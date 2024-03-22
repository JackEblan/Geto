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

package com.android.geto.core.packagemanager.demo

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import com.android.geto.core.model.TargetApplicationInfo
import com.android.geto.core.packagemanager.PackageManagerWrapper
import javax.inject.Inject

/**
 * A fake package manager wrapper that provides a fake data to Navigation UI Tests
 */
class DemoPackageManagerWrapper @Inject constructor() : PackageManagerWrapper {

    override fun getInstalledApplications(): List<TargetApplicationInfo> {
        return List(5) { index ->
            TargetApplicationInfo(
                flags = 0, packageName = "packageName$index", label = "Label $index"
            )
        }
    }

    override fun getApplicationIcon(packageName: String): Drawable {
        throw PackageManager.NameNotFoundException()
    }

    override fun getLaunchIntentForPackage(packageName: String): Intent? {
        return null
    }
}