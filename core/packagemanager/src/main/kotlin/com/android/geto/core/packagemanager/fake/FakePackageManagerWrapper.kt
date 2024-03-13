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

package com.android.geto.core.packagemanager.fake

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import com.android.geto.core.model.TargetApplicationInfo
import com.android.geto.core.packagemanager.PackageManagerWrapper
import javax.inject.Inject

/**
 * A fake package manager wrapper that provides a fake data to Navigation UI Tests
 */
class FakePackageManagerWrapper @Inject constructor() : PackageManagerWrapper {

    override fun getInstalledApplications(): List<TargetApplicationInfo> {
        return List(5) { index ->
            val icon = ShapeDrawable(OvalShape()).apply {
                paint.color = Color.GRAY

                setBounds(0, 0, (50 * 2), (50 * 2))
            }

            TargetApplicationInfo(
                flags = 0, icon = icon, packageName = "packageName$index", label = "Label $index"
            )
        }
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getApplicationIcon(packageName: String): Drawable {
        throw PackageManager.NameNotFoundException()
    }

    override fun getLaunchIntentForPackage(packageName: String): Intent? {
        return null
    }
}