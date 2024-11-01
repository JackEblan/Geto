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
package com.android.geto.framework.notificationmanager.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

internal class RevertSettingsBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) = goAsync {
        val appContext = context?.applicationContext ?: throw IllegalStateException()

        val hiltEntryPoint =
            EntryPointAccessors.fromApplication(appContext, BroadcastReceiverEntryPoint::class.java)

        val packageName = intent?.extras?.getString(EXTRA_PACKAGE_NAME) ?: ""

        val notificationId = intent?.extras?.getInt(EXTRA_NOTIFICATION_ID) ?: -1

        val appSettings = hiltEntryPoint.appSettingsBroadcastRepository()
            .getAppSettingsByPackageName(packageName = packageName).first()

        if (appSettings.isEmpty() || appSettings.all { it.enabled.not() }) return@goAsync

        if (hiltEntryPoint.secureSettingsBroadcastRepository().revertSecureSettings(appSettings)) {
            hiltEntryPoint.notificationManagerWrapper().cancel(id = notificationId)
        }
    }

    companion object {
        const val ACTION_REVERT_SETTINGS = "ACTION_REVERT_SETTINGS"
        const val EXTRA_PACKAGE_NAME = "package_name"
        const val EXTRA_NOTIFICATION_ID = "notification_id"
    }
}

private fun BroadcastReceiver.goAsync(
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
