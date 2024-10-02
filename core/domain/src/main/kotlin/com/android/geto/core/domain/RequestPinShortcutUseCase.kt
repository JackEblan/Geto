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
package com.android.geto.core.domain

import com.android.geto.core.common.Dispatcher
import com.android.geto.core.common.GetoDispatchers.Default
import com.android.geto.core.data.repository.ShortcutRepository
import com.android.geto.core.model.MappedShortcutInfoCompat
import com.android.geto.core.model.RequestPinShortcutResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestPinShortcutUseCase @Inject constructor(
    @Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher,
    private val shortcutRepository: ShortcutRepository,
) {
    suspend operator fun invoke(
        packageName: String,
        appName: String,
        mappedShortcutInfoCompat: MappedShortcutInfoCompat,
    ): RequestPinShortcutResult {
        if (!shortcutRepository.isRequestPinShortcutSupported()) {
            return RequestPinShortcutResult.UnSupportedLauncher
        }

        val isPinned = withContext(defaultDispatcher) {
            mappedShortcutInfoCompat.id !in shortcutRepository.getPinnedShortcuts().map { it.id }
        }

        return if (isPinned) {
            requestPinShortcut(
                packageName = packageName,
                appName = appName,
                mappedShortcutInfoCompat = mappedShortcutInfoCompat,
            )
        } else {
            updateShortcuts(
                packageName = packageName,
                appName = appName,
                mappedShortcutInfoCompat = mappedShortcutInfoCompat,
            )
        }
    }

    private fun requestPinShortcut(
        packageName: String,
        appName: String,
        mappedShortcutInfoCompat: MappedShortcutInfoCompat,
    ): RequestPinShortcutResult {
        return if (shortcutRepository.requestPinShortcut(
                packageName = packageName,
                appName = appName,
                mappedShortcutInfoCompat = mappedShortcutInfoCompat,
            )
        ) {
            RequestPinShortcutResult.SupportedLauncher
        } else {
            RequestPinShortcutResult.UnSupportedLauncher
        }
    }

    private suspend fun updateShortcuts(
        packageName: String,
        appName: String,
        mappedShortcutInfoCompat: MappedShortcutInfoCompat,
    ): RequestPinShortcutResult {
        return try {
            if (shortcutRepository.updateShortcuts(
                    packageName = packageName,
                    appName = appName,
                    mappedShortcutInfoCompats = listOf(mappedShortcutInfoCompat),
                )
            ) {
                RequestPinShortcutResult.UpdateSuccess
            } else {
                RequestPinShortcutResult.UpdateFailure
            }
        } catch (e: IllegalArgumentException) {
            RequestPinShortcutResult.UpdateImmutableShortcuts
        }
    }
}
