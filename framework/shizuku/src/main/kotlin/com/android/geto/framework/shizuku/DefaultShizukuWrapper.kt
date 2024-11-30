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

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.IBinder
import android.os.RemoteException
import com.android.geto.core.domain.framework.ShizukuWrapper
import dagger.hilt.android.qualifiers.ApplicationContext
import rikka.shizuku.Shizuku
import rikka.shizuku.Shizuku.OnRequestPermissionResultListener
import javax.inject.Inject


class DefaultShizukuWrapper @Inject constructor(@ApplicationContext private val context: Context) :
    ShizukuWrapper {
    private val requestPermissionResult = 1

    private val requestPermissionResultListener =
        OnRequestPermissionResultListener { requestCode, grantResult ->
        }

    private var userService: IUserService? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, binder: IBinder?) {
            if (binder != null && binder.pingBinder()) {
                userService = IUserService.Stub.asInterface(binder)

                try {
                    userService?.grantRuntimePermission(
                        context.packageName,
                        Manifest.permission.WRITE_SECURE_SETTINGS,
                        0,
                    )
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            userService = null;
        }
    }

    override fun onCreate() {
        Shizuku.addRequestPermissionResultListener(requestPermissionResultListener)
    }

    override fun onDestroy() {
        Shizuku.removeRequestPermissionResultListener(requestPermissionResultListener)
    }

    override fun checkPermission(): Boolean {
        if (Shizuku.isPreV11()) {
            return false
        }

        if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
            if (Shizuku.getVersion() >= 10) {
                val serviceArgs = Shizuku.UserServiceArgs(
                    ComponentName(
                        context.packageName,
                        UserService::class.java.getName(),
                    ),
                ).processNameSuffix("user_service").debuggable(BuildConfig.DEBUG)

                Shizuku.bindUserService(serviceArgs, connection)
            } else {
                //Tell the user to upgrade Shizuku.
            }
            return true
        } else if (Shizuku.shouldShowRequestPermissionRationale()) {
            return false
        } else {
            Shizuku.requestPermission(requestPermissionResult);
            return false
        }
    }
}