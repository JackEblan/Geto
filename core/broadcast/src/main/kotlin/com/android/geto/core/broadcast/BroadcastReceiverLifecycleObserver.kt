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

package com.android.geto.core.broadcast

import android.content.Context
import android.content.Context.RECEIVER_NOT_EXPORTED
import android.content.IntentFilter
import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject

class BroadcastReceiverLifecycleObserver @Inject constructor(
    private val context: Context, private val shortcutBroadcastReceiver: ShortcutBroadcastReceiver
) : LifecycleEventObserver {

    private val shortcutIntentFilter = IntentFilter(ShortcutBroadcastReceiver.ACTION)

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_START) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.registerReceiver(
                    shortcutBroadcastReceiver, shortcutIntentFilter, RECEIVER_NOT_EXPORTED
                )
            } else {
                context.registerReceiver(
                    shortcutBroadcastReceiver, shortcutIntentFilter
                )
            }

        } else if (event == Lifecycle.Event.ON_DESTROY) {
            context.unregisterReceiver(shortcutBroadcastReceiver)
        }
    }
}