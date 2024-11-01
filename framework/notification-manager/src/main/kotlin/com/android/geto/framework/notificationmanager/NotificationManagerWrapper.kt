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

import android.graphics.drawable.Drawable
import androidx.annotation.RequiresPermission

interface NotificationManagerWrapper {
    @RequiresPermission("android.permission.POST_NOTIFICATIONS")
    fun notify(
        packageName: String,
        icon: Drawable?,
        contentTitle: String,
        contentText: String,
    )

    fun cancel(id: Int)

    companion object {
        const val ACTION_REVERT_SETTINGS = "ACTION_REVERT_SETTINGS"
        const val EXTRA_PACKAGE_NAME = "package_name"
        const val EXTRA_NOTIFICATION_ID = "notification_id"
    }
}
