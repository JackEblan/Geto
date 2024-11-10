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

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.ServiceCompat
import com.android.geto.core.domain.ForegroundServiceAppSettingsUseCase
import com.android.geto.core.model.ForegroundServiceAppSettingsResult
import com.android.geto.core.model.ForegroundServiceAppSettingsResult.Ignore
import com.android.geto.core.model.ForegroundServiceAppSettingsResult.Success
import com.android.geto.framework.notificationmanager.NotificationManagerWrapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class UsageStatsService : Service() {
    @Inject
    lateinit var notificationManagerWrapper: NotificationManagerWrapper

    @Inject
    lateinit var foregroundServiceAppSettingsUseCase: ForegroundServiceAppSettingsUseCase

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    private val notificationId = 1

    private val usageStatsBinder = UsageStatsBinder()

    private var isActive = false

    override fun onBind(intent: Intent?): IBinder {
        return usageStatsBinder
    }

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isActive = true

        ServiceCompat.startForeground(
            this,
            notificationId,
            notificationManagerWrapper.getUsageStatsForegroundServiceNotification(
                contentTitle = getString(R.string.usage_stats),
                contentText = getString(R.string.service_is_running),
            ),
            FOREGROUND_SERVICE_TYPE_SPECIAL_USE,
        )

        serviceScope.launch {
            foregroundServiceAppSettingsUseCase().collectLatest { result ->
                updateUsageStatsForegroundServiceNotification(result = result)
            }
        }

        return START_STICKY_COMPATIBILITY
    }

    private fun updateUsageStatsForegroundServiceNotification(result: ForegroundServiceAppSettingsResult) {
        when (result) {
            is Success -> {
                notificationManagerWrapper.notify(
                    notificationId = notificationId,
                    notification = notificationManagerWrapper.getUsageStatsForegroundServiceNotification(
                        contentTitle = result.packageName,
                        contentText = getString(R.string.usage_stats_app_settings_applied_successfully),
                    ),
                )
            }

            Ignore -> {
                notificationManagerWrapper.notify(
                    notificationId = notificationId,
                    notification = notificationManagerWrapper.getUsageStatsForegroundServiceNotification(
                        contentTitle = getString(R.string.usage_stats),
                        contentText = getString(R.string.service_is_running),
                    ),
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        isActive = false

        serviceScope.cancel()
    }

    fun isActive(): Boolean {
        println(isActive)
        return isActive
    }

    inner class UsageStatsBinder : Binder() {
        fun getService(): UsageStatsService = this@UsageStatsService
    }
}
