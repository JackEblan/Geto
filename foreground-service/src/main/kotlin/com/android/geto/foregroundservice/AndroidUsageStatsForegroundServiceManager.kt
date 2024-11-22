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
package com.android.geto.foregroundservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.core.content.ContextCompat
import com.android.geto.core.domain.foregroundservice.UsageStatsForegroundServiceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class AndroidUsageStatsForegroundServiceManager @Inject constructor(@ApplicationContext private val context: Context) :
    UsageStatsForegroundServiceManager {
    private val usageStatsServiceIntent = Intent(context, UsageStatsService::class.java)

    private lateinit var usageStatsService: UsageStatsService

    private val _isActive =
        MutableSharedFlow<Boolean>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override val isActive = _isActive.asSharedFlow()

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as UsageStatsService.UsageStatsBinder

            usageStatsService = binder.getService()

            _isActive.tryEmit(true)
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            _isActive.tryEmit(false)
        }
    }

    override fun startForegroundService() {
        ContextCompat.startForegroundService(context, usageStatsServiceIntent)

        context.bindService(usageStatsServiceIntent, connection, Context.BIND_AUTO_CREATE)
    }

    override fun stopForegroundService() {
        context.unbindService(connection)

        _isActive.tryEmit(context.stopService(usageStatsServiceIntent).not())
    }
}