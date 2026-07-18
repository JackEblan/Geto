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
package com.android.geto.service

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.database.ContentObserver
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import com.android.geto.framework.notificationmanager.AndroidNotificationManagerWrapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.android.geto.common.R as commonR

private const val NOTIFICATION_ID = 1
private const val SERVICE_ACTION_STOP = "com.android.geto.action.STOP"

@AndroidEntryPoint
class SettingsObserverService : Service() {
    @Inject
    lateinit var androidNotificationManagerWrapper: AndroidNotificationManagerWrapper

    private val observer = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean, uri: Uri?) {
            super.onChange(selfChange, uri)

            val category = when {
                uri.toString().startsWith(Settings.System.CONTENT_URI.toString()) -> getString(
                    commonR.string.system,
                )

                uri.toString().startsWith(Settings.Secure.CONTENT_URI.toString()) -> getString(
                    commonR.string.secure,
                )

                uri.toString().startsWith(Settings.Global.CONTENT_URI.toString()) -> getString(
                    commonR.string.global,
                )

                else -> getString(R.string.unknown)
            }

            androidNotificationManagerWrapper.notify(
                id = NOTIFICATION_ID,
                notification = getNotification(
                    title = category,
                    text = uri?.lastPathSegment ?: getString(R.string.unknown),
                ),
            )
        }
    }

    private val stopIntent
        get() = Intent(
            this,
            SettingsObserverService::class.java,
        ).apply {
            action = SERVICE_ACTION_STOP
        }

    private val stopPendingIntent
        get() = PendingIntent.getService(
            this,
            0,
            stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )

    override fun onCreate() {
        super.onCreate()

        _isRunning.update {
            true
        }

        ServiceCompat.startForeground(
            this,
            NOTIFICATION_ID,
            getNotification(
                title = getString(R.string.settings_observer),
                text = getString(R.string.waiting_for_changes),
            ),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            } else {
                0
            },
        )

        contentResolver.registerContentObserver(
            Settings.System.CONTENT_URI,
            true,
            observer,
        )

        contentResolver.registerContentObserver(
            Settings.Secure.CONTENT_URI,
            true,
            observer,
        )

        contentResolver.registerContentObserver(
            Settings.Global.CONTENT_URI,
            true,
            observer,
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            SERVICE_ACTION_STOP -> {
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
                return START_NOT_STICKY
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        _isRunning.update {
            false
        }

        contentResolver.unregisterContentObserver(observer)
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun getNotification(
        title: String,
        text: String,
    ): Notification = NotificationCompat.Builder(
        this,
        AndroidNotificationManagerWrapper.NOTIFICATION_CHANNEL_ID,
    )
        .setSmallIcon(android.R.drawable.ic_menu_info_details)
        .setContentTitle(title)
        .setContentText(text)
        .setOngoing(true)
        .addAction(
            android.R.drawable.ic_menu_close_clear_cancel,
            getString(R.string.stop),
            stopPendingIntent,
        )
        .build()

    companion object {
        private val _isRunning = MutableStateFlow(false)
        val isRunning = _isRunning.asStateFlow()
    }
}
