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

import com.android.geto.core.data.repository.ShortcutRepository
import com.android.geto.core.model.GetoShortcutInfoCompat
import com.android.geto.core.model.RequestPinShortcutResult
import javax.inject.Inject

class RequestPinShortcutUseCase @Inject constructor(
    private val shortcutRepository: ShortcutRepository,
) {
    operator fun invoke(
        packageName: String,
        appName: String,
        getoShortcutInfoCompat: GetoShortcutInfoCompat,
    ): RequestPinShortcutResult {
        if (!shortcutRepository.isRequestPinShortcutSupported()) {
            return RequestPinShortcutResult.UnSupportedLauncher
        }

        return if (getoShortcutInfoCompat.id !in shortcutRepository.getPinnedShortcuts()
                .map { it.id }
        ) {
            requestPinShortcut(
                packageName = packageName,
                appName = appName,
                getoShortcutInfoCompat = getoShortcutInfoCompat,
            )
        } else {
            updateShortcuts(
                packageName = packageName,
                appName = appName,
                getoShortcutInfoCompat = getoShortcutInfoCompat,
            )
        }
    }

    private fun requestPinShortcut(
        packageName: String,
        appName: String,
        getoShortcutInfoCompat: GetoShortcutInfoCompat,
    ): RequestPinShortcutResult {
        return if (shortcutRepository.requestPinShortcut(
                packageName = packageName,
                appName = appName,
                getoShortcutInfoCompat = getoShortcutInfoCompat,
            )
        ) {
            RequestPinShortcutResult.SupportedLauncher
        } else {
            RequestPinShortcutResult.UnSupportedLauncher
        }
    }

    private fun updateShortcuts(
        packageName: String,
        appName: String,
        getoShortcutInfoCompat: GetoShortcutInfoCompat,
    ): RequestPinShortcutResult {
        return try {
            if (shortcutRepository.updateShortcuts(
                    packageName = packageName,
                    appName = appName,
                    getoShortcutInfoCompats = listOf(getoShortcutInfoCompat),
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
