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
package com.android.geto.core.model

sealed interface ForegroundServiceAppSettingsResult {
    data class Success(val packageName: String) : ForegroundServiceAppSettingsResult

    data object Failure : ForegroundServiceAppSettingsResult

    data object NoPermission : ForegroundServiceAppSettingsResult

    data object InvalidValues : ForegroundServiceAppSettingsResult

    data object EmptyAppSettings : ForegroundServiceAppSettingsResult

    data object DisabledAppSettings : ForegroundServiceAppSettingsResult
}

fun toForegroundServiceAppSettingsResult(
    result: AppSettingsResult,
    packageName: String,
): ForegroundServiceAppSettingsResult {
    return when (result) {
        AppSettingsResult.Success -> {
            ForegroundServiceAppSettingsResult.Success(
                packageName = packageName,
            )
        }

        AppSettingsResult.Failure -> {
            ForegroundServiceAppSettingsResult.Failure
        }

        AppSettingsResult.NoPermission -> {
            ForegroundServiceAppSettingsResult.NoPermission
        }

        AppSettingsResult.InvalidValues -> {
            ForegroundServiceAppSettingsResult.InvalidValues
        }

        AppSettingsResult.EmptyAppSettings -> {
            ForegroundServiceAppSettingsResult.EmptyAppSettings
        }

        AppSettingsResult.DisabledAppSettings -> {
            ForegroundServiceAppSettingsResult.DisabledAppSettings
        }
    }
}
