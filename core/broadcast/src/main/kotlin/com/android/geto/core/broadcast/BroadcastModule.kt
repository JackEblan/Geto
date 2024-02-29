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
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BroadcastModule {

    @Provides
    @Singleton
    fun shortcutBroadcastReceiver(): ShortcutBroadcastReceiver = ShortcutBroadcastReceiver()

    @Provides
    @Singleton
    fun broadcastReceiverLifecycleObserver(
        @ApplicationContext context: Context, shortcutBroadcastReceiver: ShortcutBroadcastReceiver
    ): BroadcastReceiverLifecycleObserver = BroadcastReceiverLifecycleObserver(
        context = context, shortcutBroadcastReceiver = shortcutBroadcastReceiver
    )
}