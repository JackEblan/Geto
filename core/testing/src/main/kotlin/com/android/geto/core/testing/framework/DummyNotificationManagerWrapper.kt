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
package com.android.geto.core.testing.framework

import android.app.Service
import android.graphics.drawable.Drawable
import com.android.geto.framework.notificationmanager.NotificationManagerWrapper

class DummyNotificationManagerWrapper : NotificationManagerWrapper {
    override fun notifyRevertSettings(
        cls: Class<*>,
        packageName: String,
        icon: Drawable?,
        contentTitle: String,
        contentText: String,
    ) {
    }

    override fun startUsageStatsForegroundService(
        service: Service,
        id: Int,
        contentTitle: String,
        contentText: String,
    ) {
    }

    override fun updateUsageStatsForegroundServiceNotification(
        id: Int,
        contentTitle: String,
        contentText: String,
    ) {
    }

    override fun cancel(id: Int) {
    }
}
