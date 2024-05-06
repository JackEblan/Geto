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
package com.android.geto.feature.settings

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.geto.core.data.repository.UserDataRepository
import com.android.geto.core.domain.CleanAppSettingsUseCase
import com.android.geto.core.model.DarkThemeConfig
import com.android.geto.core.model.ThemeBrand
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val cleanAppSettingsUseCase: CleanAppSettingsUseCase,
) : ViewModel() {
    private val intent = Intent().apply {
        action = Intent.ACTION_MAIN
        addCategory(Intent.CATEGORY_LAUNCHER)
    }

    @VisibleForTesting
    var flags = 0

    val settingsUiState: StateFlow<SettingsUiState> =
        userDataRepository.userData.map(SettingsUiState::Success).stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = SettingsUiState.Loading,
        )

    fun updateThemeBrand(themeBrand: ThemeBrand) {
        viewModelScope.launch {
            userDataRepository.setThemeBrand(themeBrand)
        }
    }

    fun updateDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        viewModelScope.launch {
            userDataRepository.setDarkThemeConfig(darkThemeConfig)
        }
    }

    fun updateDynamicColorPreference(useDynamicColor: Boolean) {
        viewModelScope.launch {
            userDataRepository.setDynamicColor(useDynamicColor)
        }
    }

    fun updateAutoLaunchPreference(useAutoLaunch: Boolean) {
        viewModelScope.launch {
            userDataRepository.setAutoLaunch(useAutoLaunch)
        }
    }

    fun cleanAppSettings() {
        viewModelScope.launch {
            cleanAppSettingsUseCase(intent = intent, flags = flags)
        }
    }
}
