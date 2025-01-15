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
package com.android.geto.domain.model

data class GetoApplicationInfo(
    val flags: Int,
    val icon: ByteArray,
    val packageName: String,
    val label: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GetoApplicationInfo

        if (flags != other.flags) return false
        if (!icon.contentEquals(other.icon)) return false
        if (packageName != other.packageName) return false
        if (label != other.label) return false

        return true
    }

    override fun hashCode(): Int {
        var result = flags
        result = 31 * result + icon.contentHashCode()
        result = 31 * result + packageName.hashCode()
        result = 31 * result + label.hashCode()
        return result
    }
}
