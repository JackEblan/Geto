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
import com.android.geto.core.model.MappedApplicationInfo
import com.android.geto.core.packagemanager.PackageManagerWrapper

class TestPackageManagerWrapper : PackageManagerWrapper {
    private var _mappedApplicationInfoList = emptyList<MappedApplicationInfo>()

    override fun queryIntentActivities(intent: Intent, flags: Int): List<MappedApplicationInfo> {
        return _mappedApplicationInfoList
    }

    override fun getApplicationIcon(packageName: String): Drawable {
        return if (packageName in _mappedApplicationInfoList.map { it.packageName }) ColorDrawable() else throw PackageManager.NameNotFoundException()
    }

    override fun getLaunchIntentForPackage(packageName: String): Intent? {
        return if (packageName in _mappedApplicationInfoList.map { it.packageName }) Intent() else null
    }

    fun setMappedApplicationInfos(value: List<MappedApplicationInfo>) {
        _mappedApplicationInfoList = value
    }
}
