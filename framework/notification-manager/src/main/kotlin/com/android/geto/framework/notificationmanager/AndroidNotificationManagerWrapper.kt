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
package com.android.geto.framework.notificationmanager

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.android.geto.core.domain.broadcastreceiver.RevertSettingsBroadcastReceiver
import com.android.geto.core.domain.broadcastreceiver.RevertSettingsBroadcastReceiver.Companion.ACTION_REVERT_SETTINGS
import com.android.geto.core.domain.broadcastreceiver.RevertSettingsBroadcastReceiver.Companion.EXTRA_NOTIFICATION_ID
import com.android.geto.core.domain.broadcastreceiver.RevertSettingsBroadcastReceiver.Companion.EXTRA_PACKAGE_NAME
import com.android.geto.core.domain.framework.NotificationManagerWrapper
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import com.android.geto.core.common.R as commonR

internal class AndroidNotificationManagerWrapper @Inject constructor(@ApplicationContext private val context: Context) :
    NotificationManagerWrapper {
    private val notificationManagerCompat = NotificationManagerCompat.from(context)

    @RequiresPermission("android.permission.POST_NOTIFICATIONS")
    override fun notifyRevertNotification(
        notificationId: Int,
        revertSettingsBroadcastReceiver: RevertSettingsBroadcastReceiver,
        packageName: String,
        icon: ByteArray?,
        contentTitle: String,
        contentText: String,
    ) {
        if (canPostNotifications().not()) {
            return
        }

        notificationManagerCompat.notify(
            notificationId,
            getRevertNotification(
                revertSettingsBroadcastReceiver = revertSettingsBroadcastReceiver,
                packageName = packageName,
                icon = icon,
                contentTitle = contentTitle,
                contentText = contentText,
            ),
        )
    }

    @RequiresPermission("android.permission.POST_NOTIFICATIONS")
    override fun updatetUsageStatsForegroundServiceNotification(
        notificationId: Int,
        contentTitle: String,
        contentText: String,
    ) {
        if (canPostNotifications().not()) {
            return
        }

        val usageStatsForegroundServiceNotification =
            notificationManagerCompat.activeNotifications.find { statusBarNotification ->
                statusBarNotification.id == notificationId
            }?.notification?.apply {
                extras = Bundle().apply {
                    putString(Notification.EXTRA_TITLE, contentTitle)

                    putString(Notification.EXTRA_TEXT, contentText)
                }
            }

        if (usageStatsForegroundServiceNotification != null) {
            notificationManagerCompat.notify(
                notificationId,
                usageStatsForegroundServiceNotification,
            )
        }
    }

    override fun cancel(id: Int) {
        notificationManagerCompat.cancel(id)
    }

    override fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val id = context.getString(commonR.string.geto_notification_channel_id)

            val name = context.getString(R.string.geto_notification_channel)

            val description = context.getString(R.string.geto_notification_channel)

            val importance = NotificationManager.IMPORTANCE_DEFAULT

            val channel = NotificationChannel(
                id,
                name,
                importance,
            ).apply {
                this.description = description
            }

            notificationManagerCompat.createNotificationChannel(channel)
        }
    }

    private fun canPostNotifications(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && notificationManagerCompat.areNotificationsEnabled()) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS,
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            notificationManagerCompat.areNotificationsEnabled()
        }
    }

    private fun getRevertNotification(
        revertSettingsBroadcastReceiver: RevertSettingsBroadcastReceiver,
        packageName: String,
        icon: ByteArray?,
        contentTitle: String,
        contentText: String,
    ): Notification {
        createNotificationChannel()

        val notificationId = packageName.hashCode()

        val revertIntent = Intent(context, revertSettingsBroadcastReceiver::class.java).apply {
            action = ACTION_REVERT_SETTINGS
            putExtra(EXTRA_PACKAGE_NAME, packageName)
            putExtra(EXTRA_NOTIFICATION_ID, notificationId)
        }

        val revertPendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            revertIntent,
            FLAG_UPDATE_CURRENT or FLAG_IMMUTABLE,
        )

        return NotificationCompat.Builder(
            context,
            context.getString(commonR.string.geto_notification_channel_id),
        ).apply {
            setSmallIcon(R.drawable.baseline_settings_24)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && icon != null) {
                setLargeIcon(Icon.createWithData(icon, 0, icon.size))
            }

            setContentTitle(contentTitle).setContentText(contentText)
            setPriority(NotificationCompat.PRIORITY_DEFAULT).addAction(
                R.drawable.baseline_settings_24,
                context.getString(R.string.revert),
                revertPendingIntent,
            )
        }.build()
    }
}
