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
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import com.android.geto.common.R as commonR

internal class DefaultNotificationManagerWrapper @Inject constructor(@param:ApplicationContext private val context: Context) :
    AndroidNotificationManagerWrapper {
    private val notificationManagerCompat = NotificationManagerCompat.from(context)

    @RequiresPermission("android.permission.POST_NOTIFICATIONS")
    override fun notify(
        notificationId: Int,
        notification: Notification,
    ) {
        if (!canPostNotifications()) {
            return
        }

        notificationManagerCompat.notify(
            notificationId,
            notification,
        )
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
}
