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

data class LauncherAppsActivityInfo(
    val componentName: String,
    val packageName: String,
    val activityIcon: ByteArray?,
    val activityLabel: String,
    val firstInstallTime: Long,
    val lastUpdateTime: Long,
    val isSystem: Boolean,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LauncherAppsActivityInfo

        if (firstInstallTime != other.firstInstallTime) return false
        if (lastUpdateTime != other.lastUpdateTime) return false
        if (isSystem != other.isSystem) return false
        if (componentName != other.componentName) return false
        if (packageName != other.packageName) return false
        if (!activityIcon.contentEquals(other.activityIcon)) return false
        if (activityLabel != other.activityLabel) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstInstallTime.hashCode()
        result = 31 * result + lastUpdateTime.hashCode()
        result = 31 * result + isSystem.hashCode()
        result = 31 * result + componentName.hashCode()
        result = 31 * result + packageName.hashCode()
        result = 31 * result + (activityIcon?.contentHashCode() ?: 0)
        result = 31 * result + activityLabel.hashCode()
        return result
    }
}
