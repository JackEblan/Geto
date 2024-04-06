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

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.android.geto.core.model.TargetApplicationInfo

fun ApplicationInfo.asTargetApplicationInfo(packageManager: PackageManager): TargetApplicationInfo {
    val label = try {
        packageManager.getApplicationLabel(this)
    } catch (e: PackageManager.NameNotFoundException) {
        null
    }

    val icon = try {
        packageManager.getApplicationIcon(this)
    } catch (e: PackageManager.NameNotFoundException) {
        null
    }

    return TargetApplicationInfo(
        flags = flags,
        icon = icon,
        packageName = packageName,
        label = label.toString(),
    )
}
