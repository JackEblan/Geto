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
package com.android.geto.activity.shortcut

import com.android.geto.domain.model.AppSettingsResult

sealed interface ShortcutActivityUiState {
    data object Loading : ShortcutActivityUiState

    data class Success(
        val appSettingsResult: AppSettingsResult?,
        val applicationIcon: ByteArray?,
    ) : ShortcutActivityUiState {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Success

            if (appSettingsResult != other.appSettingsResult) return false
            if (!applicationIcon.contentEquals(other.applicationIcon)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = appSettingsResult?.hashCode() ?: 0
            result = 31 * result + (applicationIcon?.contentHashCode() ?: 0)
            return result
        }
    }
}
