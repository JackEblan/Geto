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
import com.android.geto.framework.notificationmanager.NotificationManagerWrapper.Companion.EXTRA_NOTIFICATION_ID
import com.android.geto.framework.notificationmanager.NotificationManagerWrapper.Companion.EXTRA_PACKAGE_NAME
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

internal class RevertSettingsBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) = launch {
        val appContext = context?.applicationContext ?: throw IllegalStateException()

        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(appContext, BroadcastReceiverEntryPoint::class.java)

        val packageName = intent?.extras?.getString(EXTRA_PACKAGE_NAME) ?: ""

        val notificationId = intent?.extras?.getInt(EXTRA_NOTIFICATION_ID)

        val broadcastReceiverController = BroadcastReceiverController(
            revertAppSettingsUseCase = hiltEntryPoint.revertAppSettingsUseCase(),
            notificationManagerWrapper = hiltEntryPoint.notificationManagerWrapper(),
        )

        broadcastReceiverController.revertSettings(
            packageName = packageName,
            notificationId = notificationId,
        )
    }
}

private fun BroadcastReceiver.launch(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit,
) {
    val pendingResult = goAsync()
    @OptIn(DelicateCoroutinesApi::class)
    GlobalScope.launch(context) {
        try {
            block()
        } finally {
            pendingResult.finish()
        }
    }
}
