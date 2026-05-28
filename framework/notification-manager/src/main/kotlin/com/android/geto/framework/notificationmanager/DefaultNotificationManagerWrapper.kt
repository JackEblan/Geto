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

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

internal class DefaultNotificationManagerWrapper @Inject constructor(@param:ApplicationContext private val context: Context) :
    AndroidNotificationManagerWrapper {
    private val notificationManager =
        context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    override fun notify(
        id: Int,
        notification: Notification,
    ) {
        if (notificationManager.areNotificationsEnabled()) {
            notificationManager.notify(id, notification)
        }
    }

    override fun cancel(id: Int) {
        notificationManager.cancel(id)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun createNotificationChannel(
        channelId: String,
        name: String,
        importance: Int,
    ) {
        notificationManager.createNotificationChannel(
            NotificationChannel(
                channelId,
                name,
                importance,
            ),
        )
    }
}
