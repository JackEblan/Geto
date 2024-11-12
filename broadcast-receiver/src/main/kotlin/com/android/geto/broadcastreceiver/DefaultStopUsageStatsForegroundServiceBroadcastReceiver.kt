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
import com.android.geto.core.domain.broadcastreceiver.BroadcastReceiverController
import com.android.geto.core.domain.broadcastreceiver.StopUsageStatsForegroundServiceBroadcastReceiver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DefaultStopUsageStatsForegroundServiceBroadcastReceiver @Inject constructor() :
    BroadcastReceiver(), StopUsageStatsForegroundServiceBroadcastReceiver {
    @Inject
    lateinit var broadcastReceiverController: BroadcastReceiverController

    override fun onReceive(context: Context?, intent: Intent?) {
        broadcastReceiverController.stopForegroundService()
    }
}
