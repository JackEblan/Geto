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

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.graphics.drawable.toBitmap
import com.android.geto.framework.notificationmanager.NotificationManagerWrapper.Companion.ACTION_REVERT_SETTINGS
import com.android.geto.framework.notificationmanager.NotificationManagerWrapper.Companion.EXTRA_NOTIFICATION_ID
import com.android.geto.framework.notificationmanager.NotificationManagerWrapper.Companion.EXTRA_PACKAGE_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class AndroidNotificationManagerWrapper @Inject constructor(@ApplicationContext private val context: Context) :
    NotificationManagerWrapper {
    private val notificationManagerCompat = NotificationManagerCompat.from(context)

    @RequiresPermission("android.permission.POST_NOTIFICATIONS")
    override fun notify(
        packageName: String,
        icon: Drawable?,
        contentTitle: String,
        contentText: String,
    ) {
        createNotificationChannel()

        val notificationId = packageName.hashCode()

        val revertIntent = Intent().apply {
            setComponent(
                ComponentName(
                    context.packageName,
                    "com.android.geto.broadcastreceiver.RevertSettingsBroadcastReceiver",
                ),
            )
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

        val notification = NotificationCompat.Builder(
            context,
            context.getString(R.string.geto_notification_channel_id),
        ).setSmallIcon(R.drawable.baseline_settings_24).setLargeIcon(icon?.toBitmap())
            .setContentTitle(contentTitle).setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).addAction(
                R.drawable.baseline_settings_24,
                context.getString(R.string.revert),
                revertPendingIntent,
            ).build()

        NotificationManagerCompat.from(context).notify(notificationId, notification)
    }

    override fun cancel(id: Int) {
        NotificationManagerCompat.from(context).cancel(id)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val id = context.getString(R.string.geto_notification_channel_id)

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
}