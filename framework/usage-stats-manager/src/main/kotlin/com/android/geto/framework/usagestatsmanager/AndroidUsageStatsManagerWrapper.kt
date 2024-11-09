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
package com.android.geto.framework.usagestatsmanager

import android.app.AppOpsManager
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Build
import android.provider.Settings
import androidx.core.content.getSystemService
import com.android.geto.core.common.Dispatcher
import com.android.geto.core.common.GetoDispatchers.Default
import com.android.geto.core.model.GetoLifeCycle
import com.android.geto.core.model.GetoUsageEvent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

internal class AndroidUsageStatsManagerWrapper @Inject constructor(
    @ApplicationContext private val context: Context,
    @Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher,
) : UsageStatsManagerWrapper {
    private val usageStatsManager = context.getSystemService<UsageStatsManager>()

    private val appOpsManager = context.getSystemService<AppOpsManager>()

    override fun queryEvents(): Flow<GetoUsageEvent> = flow {
        val interval = 1000L

        while (true) {
            val end = System.currentTimeMillis()
            val start = end - interval
            val events = usageStatsManager?.queryEvents(start, end)

            while (events?.hasNextEvent() == true) {
                val event = UsageEvents.Event()

                events.getNextEvent(event)

                when (event.eventType) {
                    UsageEvents.Event.ACTIVITY_RESUMED -> {
                        emit(
                            GetoUsageEvent(
                                packageName = event.packageName,
                                getoLifeCycle = GetoLifeCycle.ACTIVITY_RESUMED,
                            ),
                        )
                    }

                    UsageEvents.Event.ACTIVITY_PAUSED -> {
                        emit(
                            GetoUsageEvent(
                                packageName = event.packageName,
                                getoLifeCycle = GetoLifeCycle.ACTIVITY_PAUSED,
                            ),
                        )
                    }
                }
            }

            delay(interval)
        }
    }.flowOn(defaultDispatcher)

    override fun isUsageStatsPermissionGranted(): Boolean {
        val mode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            appOpsManager?.unsafeCheckOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                context.packageName,
            )
        } else {
            appOpsManager?.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(),
                context.packageName,
            )
        }

        return mode == AppOpsManager.MODE_ALLOWED
    }

    override fun requestUsageStatsPermission() {
        val intent = Intent().apply {
            action = Settings.ACTION_USAGE_ACCESS_SETTINGS

            flags = FLAG_ACTIVITY_NEW_TASK
        }

        context.startActivity(intent)
    }
}
