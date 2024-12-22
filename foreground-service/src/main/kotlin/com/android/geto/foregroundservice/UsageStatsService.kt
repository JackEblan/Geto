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

import android.app.Notification
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.android.geto.core.common.di.ApplicationScope
import com.android.geto.core.domain.broadcastreceiver.StopUsageStatsForegroundServiceBroadcastReceiver
import com.android.geto.core.domain.broadcastreceiver.StopUsageStatsForegroundServiceBroadcastReceiver.Companion.ACTION_STOP_USAGE_STATS_FOREGROUND_SERVICE
import com.android.geto.core.domain.framework.NotificationManagerWrapper
import com.android.geto.core.domain.model.ForegroundServiceAppSettingsResult
import com.android.geto.core.domain.model.ForegroundServiceAppSettingsResult.Ignore
import com.android.geto.core.domain.model.ForegroundServiceAppSettingsResult.Success
import com.android.geto.core.domain.usecase.ForegroundServiceAppSettingsUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.android.geto.core.common.R as commonR

@AndroidEntryPoint
class UsageStatsService : Service() {
    @Inject
    lateinit var notificationManagerWrapper: NotificationManagerWrapper

    @Inject
    lateinit var foregroundServiceAppSettingsUseCase: ForegroundServiceAppSettingsUseCase

    @Inject
    lateinit var stopUsageStatsForegroundServiceBroadcastReceiver: StopUsageStatsForegroundServiceBroadcastReceiver

    @Inject
    @ApplicationScope
    lateinit var appScope: CoroutineScope

    private val notificationId = 1

    private val usageStatsBinder = UsageStatsBinder()

    override fun onBind(intent: Intent?): IBinder {
        return usageStatsBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startUsageStatsForeground()

        appScope.launch {
            foregroundServiceAppSettingsUseCase().collectLatest { result ->
                updateUsageStatsForegroundServiceNotification(result = result)
            }
        }

        return START_STICKY_COMPATIBILITY
    }

    override fun onDestroy() {
        super.onDestroy()

        appScope.cancel()
    }

    private fun startUsageStatsForeground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            startForeground(
                notificationId,
                getUsageStatsForegroundServiceNotification(
                    contentTitle = getString(R.string.usage_stats_service),
                    contentText = getString(R.string.usage_stats_service_message),
                ),
                FOREGROUND_SERVICE_TYPE_SPECIAL_USE,
            )
        } else {
            startForeground(
                notificationId,
                getUsageStatsForegroundServiceNotification(
                    contentTitle = getString(R.string.usage_stats_service),
                    contentText = getString(R.string.usage_stats_service_message),
                ),
            )
        }
    }

    private fun updateUsageStatsForegroundServiceNotification(result: ForegroundServiceAppSettingsResult) {
        when (result) {
            is Success -> {
                notificationManagerWrapper.updatetUsageStatsForegroundServiceNotification(
                    notificationId = notificationId,
                    contentTitle = result.packageName,
                    contentText = getString(R.string.usage_stats_app_settings_applied_successfully),
                )
            }

            Ignore -> {
                notificationManagerWrapper.updatetUsageStatsForegroundServiceNotification(
                    notificationId = notificationId,
                    contentTitle = getString(R.string.usage_stats_service),
                    contentText = getString(R.string.usage_stats_service_message),
                )
            }
        }
    }

    private fun getUsageStatsForegroundServiceNotification(
        contentTitle: String,
        contentText: String,
    ): Notification {
        notificationManagerWrapper.createNotificationChannel()

        val stopIntent =
            Intent(this, stopUsageStatsForegroundServiceBroadcastReceiver::class.java).apply {
                action = ACTION_STOP_USAGE_STATS_FOREGROUND_SERVICE
            }

        val stopPendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            stopIntent,
            FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE,
        )

        return NotificationCompat.Builder(
            this,
            getString(commonR.string.geto_notification_channel_id),
        ).setSmallIcon(R.drawable.baseline_apps_24).setContentTitle(contentTitle)
            .setContentText(contentText).setPriority(NotificationCompat.PRIORITY_DEFAULT).addAction(
                R.drawable.baseline_apps_24,
                getString(R.string.stop),
                stopPendingIntent,
            ).build()
    }

    inner class UsageStatsBinder : Binder() {
        fun getService(): UsageStatsService = this@UsageStatsService
    }
}
