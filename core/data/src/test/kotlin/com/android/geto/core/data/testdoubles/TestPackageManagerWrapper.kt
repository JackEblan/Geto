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

package com.android.geto.core.data.testdoubles

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.android.geto.core.model.TargetApplicationInfo
import com.android.geto.core.packagemanager.PackageManagerWrapper

class TestPackageManagerWrapper : PackageManagerWrapper {
    private var _installedApplications = listOf<TargetApplicationInfo>()

    override fun getInstalledApplications(): List<TargetApplicationInfo> {
        return _installedApplications
    }

    override fun getApplicationIcon(packageName: String): Drawable {
        return if (packageName in _installedApplications.map { it.packageName }) ColorDrawable() else throw PackageManager.NameNotFoundException()
    }

    override fun getLaunchIntentForPackage(packageName: String): Intent? {
        return if (packageName in _installedApplications.map { it.packageName }) Intent() else null
    }

    fun setInstalledApplications(value: List<TargetApplicationInfo>) {
        _installedApplications = value
    }
}