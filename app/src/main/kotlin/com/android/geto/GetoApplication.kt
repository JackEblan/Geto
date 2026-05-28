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
package com.android.geto

import android.app.Application
import android.app.NotificationManager
import android.os.Build
import com.android.geto.framework.notificationmanager.AndroidNotificationManagerWrapper
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GetoApplication : Application() {
    @Inject
    lateinit var notificationManagerWrapper: AndroidNotificationManagerWrapper

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManagerWrapper.createNotificationChannel(
                channelId = AndroidNotificationManagerWrapper.NOTIFICATION_CHANNEL_ID,
                name = getString(R.string.app_name),
                importance = NotificationManager.IMPORTANCE_DEFAULT,
            )
        }
    }
}
