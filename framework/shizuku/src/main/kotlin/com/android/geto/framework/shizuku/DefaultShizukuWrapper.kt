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
import com.android.geto.domain.framework.ShizukuWrapper
import com.android.geto.domain.model.ShizukuStatus
import com.android.geto.domain.repository.UserDataRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import rikka.shizuku.Shizuku
import rikka.shizuku.Shizuku.OnRequestPermissionResultListener
import java.io.DataOutputStream
import javax.inject.Inject

internal class DefaultShizukuWrapper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userDataRepository: UserDataRepository,
) :
    ShizukuWrapper {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    private val requestPermissionResult = 1

    private val requestPermissionResultListener =
        OnRequestPermissionResultListener { _, grantResult ->
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                _shizukuStatus.tryEmit(
                    ShizukuStatus.Granted,
                )

                bindUserService()
            } else {
                _shizukuStatus.tryEmit(
                    ShizukuStatus.Denied,
                )
            }
        }

    private var userService: IUserService? = null

    private val serviceArgs = Shizuku.UserServiceArgs(
        ComponentName(
            context.packageName,
            UserService::class.java.getName(),
        ),
    ).daemon(false).processNameSuffix("user_service").debuggable(BuildConfig.DEBUG)

    private val _shizukuStatus =
        MutableSharedFlow<ShizukuStatus>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override val shizukuStatus = _shizukuStatus.asSharedFlow()

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, binder: IBinder?) {
            if (binder != null && binder.pingBinder()) {
                userService = IUserService.Stub.asInterface(binder)

                _shizukuStatus.tryEmit(
                    ShizukuStatus.Bound,
                )
            }
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            userService = null
        }
    }

    private val onBinderReceivedListener = Shizuku.OnBinderReceivedListener {
        _shizukuStatus.tryEmit(
            ShizukuStatus.AliveBinder,
        )
    }

    private val onBinderDeadListener = Shizuku.OnBinderDeadListener {
        _shizukuStatus.tryEmit(
            ShizukuStatus.DeadBinder,
        )
    }

    override fun onCreate() {
        Shizuku.addBinderReceivedListener(onBinderReceivedListener)

        Shizuku.addBinderDeadListener(onBinderDeadListener)

        Shizuku.addRequestPermissionResultListener(requestPermissionResultListener)
    }

    override fun onDestroy() {
        Shizuku.removeRequestPermissionResultListener(requestPermissionResultListener)

        Shizuku.removeBinderReceivedListener(onBinderReceivedListener)

        Shizuku.removeBinderDeadListener(onBinderDeadListener)
    }

    override fun checkShizukuPermission() {
        scope.launch {
            val useRootMode = userDataRepository.userData.first().useRootMode
            if (useRootMode) {
                grantRuntimePermissionWithRoot()
                return@launch
            }

            try {
                if (Shizuku.isPreV11()) {
                    _shizukuStatus.tryEmit(
                        ShizukuStatus.Denied,
                    )
                } else if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
                    grantRuntimePermission()
                } else if (Shizuku.shouldShowRequestPermissionRationale()) {
                    _shizukuStatus.tryEmit(
                        ShizukuStatus.Denied,
                    )
                } else {
                    Shizuku.requestPermission(requestPermissionResult)
                }
            } catch (e: Throwable) {
                _shizukuStatus.tryEmit(
                    ShizukuStatus.Error,
                )
            }
        }
    }

    private fun grantRuntimePermissionWithRoot() {
        try {
            val process = Runtime.getRuntime().exec("su")
            val os = DataOutputStream(process.outputStream)
            os.writeBytes("pm grant ${context.packageName} android.permission.WRITE_SECURE_SETTINGS\n")
            os.writeBytes("exit\n")
            os.flush()
            val result = process.waitFor()
            if (result == 0) {
                _shizukuStatus.tryEmit(
                    ShizukuStatus.CanWriteSecureSettings,
                )
            } else {
                _shizukuStatus.tryEmit(
                    ShizukuStatus.Denied,
                )
            }
        } catch (e: Exception) {
            _shizukuStatus.tryEmit(
                ShizukuStatus.Error,
            )
        }
    }

    private fun bindUserService() {
        if (Shizuku.getVersion() >= 10) {
            Shizuku.bindUserService(serviceArgs, connection)
        } else {
            _shizukuStatus.tryEmit(
                ShizukuStatus.UpgradeShizuku,
            )
        }
    }

    private fun unbindUserService() {
        Shizuku.unbindUserService(serviceArgs, connection, true)
    }

    private fun grantRuntimePermission() {
        try {
            userService?.grantRuntimePermission(
                context.packageName,
                Manifest.permission.WRITE_SECURE_SETTINGS,
            )

            _shizukuStatus.tryEmit(
                ShizukuStatus.CanWriteSecureSettings,
            )
        } catch (e: RemoteException) {
            _shizukuStatus.tryEmit(
                ShizukuStatus.RemoteException,
            )
        } finally {
            unbindUserService()
        }
    }
}
