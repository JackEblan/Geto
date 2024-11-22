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
package com.android.geto.core.domain.model

data class GetoShortcutInfoCompat(
    val id: String,
    val icon: ByteArray?,
    val shortLabel: String,
    val longLabel: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GetoShortcutInfoCompat

        if (id != other.id) return false
        if (icon != null) {
            if (other.icon == null) return false
            if (!icon.contentEquals(other.icon)) return false
        } else if (other.icon != null) {
            return false
        }
        if (shortLabel != other.shortLabel) return false
        if (longLabel != other.longLabel) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (icon?.contentHashCode() ?: 0)
        result = 31 * result + shortLabel.hashCode()
        result = 31 * result + longLabel.hashCode()
        return result
    }
}
