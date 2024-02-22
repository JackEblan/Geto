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

package com.android.geto.feature.securesettingslist

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.geto.core.domain.repository.ClipboardRepository
import com.android.geto.core.domain.repository.SecureSettingsRepository
import com.android.geto.core.model.SettingsType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SecureSettingsListViewModel @Inject constructor(
    private val secureSettingsRepository: SecureSettingsRepository,
    private val clipboardRepository: ClipboardRepository
) : ViewModel() {

    private val _secureSettingsListUiState =
        MutableStateFlow<SecureSettingsListUiState>(SecureSettingsListUiState.Loading)

    private var _snackBar = MutableStateFlow<String?>(null)

    val snackBar = _snackBar.asStateFlow()

    val secureSettingsListUiState = _secureSettingsListUiState.asStateFlow()

    @VisibleForTesting(VisibleForTesting.PRIVATE)
    val loadingDelay = 500L

    fun getSecureSettingsList(selectedSettingsTypeIndex: Int) {
        viewModelScope.launch {
            _secureSettingsListUiState.update { SecureSettingsListUiState.Loading }

            delay(loadingDelay)

            _secureSettingsListUiState.update {
                SecureSettingsListUiState.Success(
                    secureSettingsRepository.getSecureSettings(SettingsType.entries[selectedSettingsTypeIndex])
                )
            }
        }
    }

    fun copySecureSettings(secureSettings: String) {
        val result = clipboardRepository.setPrimaryClip(
            label = "Secure Settings", text = secureSettings
        )

        _snackBar.update { result }
    }

    fun clearSnackBar() {
        _snackBar.value = null
    }
}