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
package com.android.geto.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.android.geto.common.di.ApplicationScope
import com.android.geto.domain.broadcastreceiver.RevertSettingsBroadcastReceiver
import com.android.geto.domain.broadcastreceiver.RevertSettingsBroadcastReceiver.Companion.EXTRA_NOTIFICATION_ID
import com.android.geto.domain.broadcastreceiver.RevertSettingsBroadcastReceiver.Companion.EXTRA_PACKAGE_NAME
import com.android.geto.domain.framework.NotificationManagerWrapper
import com.android.geto.domain.model.AppSettingsResult
import com.android.geto.domain.usecase.RevertAppSettingsUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
internal class DefaultRevertSettingsBroadcastReceiver @Inject constructor() :
    BroadcastReceiver(), RevertSettingsBroadcastReceiver {

    @Inject
    @ApplicationScope
    lateinit var appScope: CoroutineScope

    @Inject
    lateinit var revertAppSettingsUseCase: RevertAppSettingsUseCase

    @Inject
    lateinit var notificationManagerWrapper: NotificationManagerWrapper

    override fun onReceive(context: Context?, intent: Intent?) {
        val packageName = intent?.extras?.getString(EXTRA_PACKAGE_NAME)

        val notificationId = intent?.extras?.getInt(EXTRA_NOTIFICATION_ID)

        revertSettings(
            packageName = packageName,
            notificationId = notificationId,
        )
    }

    private fun revertSettings(packageName: String?, notificationId: Int?) {
        if (packageName == null || notificationId == null) return

        appScope.launch {
            if (revertAppSettingsUseCase(packageName = packageName) == AppSettingsResult.Success) {
                notificationManagerWrapper.cancel(notificationId)
            }
        }
    }
}
