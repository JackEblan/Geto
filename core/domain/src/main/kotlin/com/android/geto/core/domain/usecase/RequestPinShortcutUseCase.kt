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
package com.android.geto.core.domain.usecase

import com.android.geto.core.domain.model.GetoShortcutInfoCompat
import com.android.geto.core.domain.model.RequestPinShortcutResult
import com.android.geto.core.domain.model.RequestPinShortcutResult.SupportedLauncher
import com.android.geto.core.domain.model.RequestPinShortcutResult.UnsupportedLauncher
import com.android.geto.core.domain.model.RequestPinShortcutResult.UpdateFailure
import com.android.geto.core.domain.model.RequestPinShortcutResult.UpdateImmutableShortcuts
import com.android.geto.core.domain.model.RequestPinShortcutResult.UpdateSuccess
import com.android.geto.core.domain.repository.ShortcutRepository
import javax.inject.Inject

class RequestPinShortcutUseCase @Inject constructor(
    private val shortcutRepository: ShortcutRepository,
) {
    suspend operator fun invoke(
        packageName: String,
        appName: String,
        getoShortcutInfoCompat: GetoShortcutInfoCompat,
    ): RequestPinShortcutResult {
        if (!shortcutRepository.isRequestPinShortcutSupported()) {
            return UnsupportedLauncher
        }

        val pinnedShortcut = shortcutRepository.getPinnedShortcut(id = getoShortcutInfoCompat.id)

        return if (pinnedShortcut != null) {
            updateShortcuts(
                packageName = packageName,
                appName = appName,
                getoShortcutInfoCompat = getoShortcutInfoCompat,
            )
        } else {
            requestPinShortcut(
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
            SupportedLauncher
        } else {
            UnsupportedLauncher
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
                UpdateSuccess
            } else {
                UpdateFailure
            }
        } catch (e: IllegalArgumentException) {
            UpdateImmutableShortcuts
        }
    }
}
