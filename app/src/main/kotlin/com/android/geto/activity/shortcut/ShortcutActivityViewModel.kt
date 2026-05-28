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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.geto.domain.framework.PackageManagerWrapper
import com.android.geto.domain.usecase.ApplyAppSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShortcutActivityViewModel @Inject constructor(
    private val applyAppSettingsUseCase: ApplyAppSettingsUseCase,
    private val packageManagerWrapper: PackageManagerWrapper,
) : ViewModel() {
    private val _shortcutActivityUiState =
        MutableStateFlow<ShortcutActivityUiState>(ShortcutActivityUiState.Loading)
    val shortcutActivityUiState = _shortcutActivityUiState.asStateFlow()

    fun applyAppSettings(packageName: String) {
        viewModelScope.launch {
            _shortcutActivityUiState.update {
                ShortcutActivityUiState.Success(
                    appSettingsResult = applyAppSettingsUseCase(packageName = packageName),
                    applicationIcon = packageManagerWrapper.getApplicationIcon(packageName = packageName),
                )
            }
        }
    }
}
