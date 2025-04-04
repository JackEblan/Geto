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
package com.android.geto.framework.shizuku

import android.app.IActivityManager
import android.content.pm.IPackageManager
import rikka.shizuku.SystemServiceHelper
import kotlin.system.exitProcess

internal class UserService : IUserService.Stub() {
    private val packageManager = IPackageManager.Stub.asInterface(
        SystemServiceHelper.getSystemService("package"),
    )

    private val activityManager = IActivityManager.Stub.asInterface(
        SystemServiceHelper.getSystemService("activity"),
    )

    override fun destroy() {
        exitProcess(1)
    }

    override fun grantRuntimePermission(
        packageName: String?,
        permissionName: String?,
    ) {
        packageManager.grantRuntimePermission(
            packageName,
            permissionName,
            activityManager.currentUserId,
        )
    }
}
