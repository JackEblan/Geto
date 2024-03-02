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

package com.android.geto.core.clipboardmanager.fake

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.android.geto.core.clipboardmanager.PackageManagerWrapper
import javax.inject.Inject

class FakePackageManagerWrapper @Inject constructor() : PackageManagerWrapper {

    private var _installedApplications = listOf(ApplicationInfo().apply {
        packageName = "Application1"
        flags = 0
    }, ApplicationInfo().apply {
        packageName = "Application2"
        flags = 0
    }, ApplicationInfo().apply {
        packageName = "Application3"
        flags = 0
    }, ApplicationInfo().apply {
        packageName = "Application4"
        flags = 0
    }, ApplicationInfo().apply {
        packageName = "Application5"
        flags = 0
    })

    override fun getInstalledApplications(): List<ApplicationInfo> {
        return _installedApplications
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getApplicationLabel(applicationInfo: ApplicationInfo): CharSequence {
        return if (applicationInfo in _installedApplications) "Application" else throw PackageManager.NameNotFoundException()
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getApplicationIcon(applicationInfo: ApplicationInfo): Drawable {
        return if (applicationInfo in _installedApplications) ColorDrawable() else throw PackageManager.NameNotFoundException()
    }

    @Throws(PackageManager.NameNotFoundException::class)
    override fun getApplicationIcon(packageName: String): Drawable {
        return if (packageName in _installedApplications.map { it.packageName }) ColorDrawable() else throw PackageManager.NameNotFoundException()
    }

    override fun getLaunchIntentForPackage(packageName: String): Intent? {
        return if (packageName in _installedApplications.map { it.packageName }) Intent() else null
    }
}