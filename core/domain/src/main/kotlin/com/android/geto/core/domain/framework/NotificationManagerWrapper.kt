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
package com.android.geto.core.domain.framework

import android.app.Notification
import android.graphics.drawable.Drawable
import androidx.annotation.RequiresPermission
import com.android.geto.core.domain.broadcastreceiver.RevertSettingsBroadcastReceiver

interface NotificationManagerWrapper {
    @RequiresPermission("android.permission.POST_NOTIFICATIONS")
    fun notify(notificationId: Int, notification: Notification)

    fun getUsageStatsForegroundServiceNotification(
        contentTitle: String,
        contentText: String,
    ): Notification

    fun getRevertNotification(
        revertSettingsBroadcastReceiver: RevertSettingsBroadcastReceiver,
        packageName: String,
        icon: Drawable?,
        contentTitle: String,
        contentText: String,
    ): Notification

    fun cancel(id: Int)
}
