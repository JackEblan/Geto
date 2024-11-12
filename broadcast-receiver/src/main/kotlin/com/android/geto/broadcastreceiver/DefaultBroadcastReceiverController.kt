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

import com.android.geto.core.common.Dispatcher
import com.android.geto.core.common.GetoDispatchers.IO
import com.android.geto.core.domain.broadcastreceiver.BroadcastReceiverController
import com.android.geto.core.domain.foregroundservice.UsageStatsForegroundServiceManager
import com.android.geto.core.domain.framework.NotificationManagerWrapper
import com.android.geto.core.domain.model.AppSettingsResult
import com.android.geto.core.domain.usecase.RevertAppSettingsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class DefaultBroadcastReceiverController @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val revertAppSettingsUseCase: RevertAppSettingsUseCase,
    private val notificationManagerWrapper: NotificationManagerWrapper,
    private val usageStatsForegroundServiceManager: UsageStatsForegroundServiceManager,
) : BroadcastReceiverController {
    override fun revertSettings(packageName: String?, notificationId: Int?) {
        if (packageName == null || notificationId == null) return

        CoroutineScope(ioDispatcher).launch {
            if (revertAppSettingsUseCase(packageName = packageName) == AppSettingsResult.Success) {
                notificationManagerWrapper.cancel(notificationId)
            }
        }
    }

    override fun stopForegroundService() {
        usageStatsForegroundServiceManager.stopForegroundService()
    }
}
