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
package com.android.geto.domain.usecase

import com.android.geto.domain.common.dispatcher.Dispatcher
import com.android.geto.domain.common.dispatcher.GetoDispatchers.Default
import com.android.geto.domain.framework.ShortcutManagerCompatWrapper
import com.android.geto.domain.model.RequestPinShortcutResult
import com.android.geto.domain.model.RequestPinShortcutResult.SupportedLauncher
import com.android.geto.domain.model.RequestPinShortcutResult.UnsupportedLauncher
import com.android.geto.domain.model.RequestPinShortcutResult.UpdateFailure
import com.android.geto.domain.model.RequestPinShortcutResult.UpdateImmutableShortcuts
import com.android.geto.domain.model.RequestPinShortcutResult.UpdateSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestPinShortcutUseCase @Inject constructor(
    @param:Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher,
    private val shortcutManagerCompatWrapper: ShortcutManagerCompatWrapper,
) {
    suspend operator fun invoke(
        componentName: String,
        icon: ByteArray?,
        id: String,
        shortLabel: String,
        longLabel: String,
    ): RequestPinShortcutResult {
        if (!shortcutManagerCompatWrapper.isRequestPinShortcutSupported()) {
            return UnsupportedLauncher
        }

        return withContext(defaultDispatcher) {
            val getoShortcutInfoCompat = shortcutManagerCompatWrapper.getShortcuts().find { it.id == id }

            if (getoShortcutInfoCompat != null) {
                updateShortcuts(
                    componentName = componentName,
                    icon = icon,
                    id = id,
                    shortLabel = shortLabel,
                    longLabel = longLabel,
                )
            } else {
                requestPinShortcut(
                    componentName = componentName,
                    icon = icon,
                    id = id,
                    shortLabel = shortLabel,
                    longLabel = longLabel,
                )
            }
        }
    }

    private fun requestPinShortcut(
        componentName: String,
        icon: ByteArray?,
        id: String,
        shortLabel: String,
        longLabel: String,
    ): RequestPinShortcutResult {
        return if (shortcutManagerCompatWrapper.requestPinShortcut(
                componentName = componentName,
                icon = icon,
                id = id,
                shortLabel = shortLabel,
                longLabel = longLabel,
            )
        ) {
            SupportedLauncher
        } else {
            UnsupportedLauncher
        }
    }

    private fun updateShortcuts(
        componentName: String,
        icon: ByteArray?,
        id: String,
        shortLabel: String,
        longLabel: String,
    ): RequestPinShortcutResult {
        return try {
            if (shortcutManagerCompatWrapper.updateShortcuts(
                    componentName = componentName,
                    icon = icon,
                    id = id,
                    shortLabel = shortLabel,
                    longLabel = longLabel,
                )
            ) {
                UpdateSuccess
            } else {
                UpdateFailure
            }
        } catch (_: IllegalArgumentException) {
            UpdateImmutableShortcuts
        }
    }
}
