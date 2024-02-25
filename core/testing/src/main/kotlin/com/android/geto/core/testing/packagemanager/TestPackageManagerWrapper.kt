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

package com.android.geto.core.testing.packagemanager

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.android.geto.core.clipboardmanager.PackageManagerWrapper

class TestPackageManagerWrapper : PackageManagerWrapper {

    private var _installedApplications = listOf<ApplicationInfo>()

    override fun getInstalledApplications(): List<ApplicationInfo> {
        return _installedApplications
    }

    override fun getApplicationLabel(applicationInfo: ApplicationInfo): String {
        return "Application"
    }

    override fun getApplicationIcon(applicationInfo: ApplicationInfo): Drawable {
        return ColorDrawable()
    }

    override fun getLaunchIntentForPackage(packageName: String): Intent? {
        return Intent()
    }

    /**
     * A test-only API to set installed applications.
     */
    fun setInstalledApplications(value: List<ApplicationInfo>) {
        _installedApplications = value
    }
}