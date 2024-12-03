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
import com.android.geto.core.domain.model.ShizukuStatus
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import rikka.shizuku.Shizuku
import rikka.shizuku.Shizuku.OnRequestPermissionResultListener
import javax.inject.Inject


class DefaultShizukuWrapper @Inject constructor(@ApplicationContext private val context: Context) :
    ShizukuWrapper {
    private val requestPermissionResult = 1

    private val requestPermissionResultListener =
        OnRequestPermissionResultListener { _, grantResult ->
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                _shizukuStatus.tryEmit(
                    ShizukuStatus.Granted,
                )
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
    ).processNameSuffix("user_service").debuggable(BuildConfig.DEBUG)

    private val _shizukuStatus =
        MutableSharedFlow<ShizukuStatus>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override val shizukuStatus = _shizukuStatus.asSharedFlow()

    private var _isBinderAlive = false

    private var _isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, binder: IBinder?) {
            if (binder != null && binder.pingBinder()) {
                userService = IUserService.Stub.asInterface(binder)

                _isBound = true

                grantRuntimePermission()
            }
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            userService = null

            _isBound = false
        }
    }

    private val onBinderReceivedListener = Shizuku.OnBinderReceivedListener {
        _isBinderAlive = true

        _shizukuStatus.tryEmit(
            ShizukuStatus.AliveBinder,
        )
    }

    private val onBinderDeadListener = Shizuku.OnBinderDeadListener {
        _isBinderAlive = false

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
        if (_isBinderAlive.not()) {
            _shizukuStatus.tryEmit(
                ShizukuStatus.DeadBinder,
            )
            return
        }

        if (Shizuku.isPreV11()) {
            _shizukuStatus.tryEmit(
                ShizukuStatus.Denied,
            )
        } else if (Shizuku.checkSelfPermission() == PackageManager.PERMISSION_GRANTED) {
            _shizukuStatus.tryEmit(
                ShizukuStatus.Loading,
            )

            updateUserService()
        } else if (Shizuku.shouldShowRequestPermissionRationale()) {
            _shizukuStatus.tryEmit(
                ShizukuStatus.Denied,
            )
        } else {
            Shizuku.requestPermission(requestPermissionResult)
        }
    }

    private fun updateUserService() {
        if (_isBound) {
            unbindUserService()
        } else {
            bindUserService()
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

        _isBound = false

        _shizukuStatus.tryEmit(
            ShizukuStatus.UnBound,
        )
    }

    private fun grantRuntimePermission() {
        try {
            userService?.grantRuntimePermission(
                context.packageName,
                Manifest.permission.WRITE_SECURE_SETTINGS,
                0,
            )

            _shizukuStatus.tryEmit(
                ShizukuStatus.CanWriteSecureSettings,
            )
        } catch (e: RemoteException) {
            _shizukuStatus.tryEmit(
                ShizukuStatus.RemoteException,
            )
        }
    }
}