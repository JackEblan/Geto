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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.geto.domain.model.DarkThemeConfig
import com.android.geto.domain.model.ThemeBrand
import com.android.geto.domain.repository.UserDataRepository
import com.android.geto.domain.usecase.CleanAppSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val cleanAppSettingsUseCase: CleanAppSettingsUseCase,
) : ViewModel() {
    val settingsUiState = userDataRepository.userData.map(SettingsUiState::Success).stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5_000),
        initialValue = SettingsUiState.Loading,
    )

    fun onEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.CleanAppSettings -> {
                cleanAppSettings()
            }

            is SettingsEvent.UpdateAutoLaunch -> {
                updateAutoLaunch(useAutoLaunch = event.useAutoLaunch)
            }

            is SettingsEvent.UpdateDarkThemeConfig -> {
                updateDarkThemeConfig(darkThemeConfig = event.darkThemeConfig)
            }

            is SettingsEvent.UpdateDynamicColor -> {
                updateDynamicColor(useDynamicColor = event.useDynamicColor)
            }

            is SettingsEvent.UpdateThemeBrand -> {
                updateThemeBrand(themeBrand = event.themeBrand)
            }
        }
    }

    private fun updateThemeBrand(themeBrand: ThemeBrand) {
        viewModelScope.launch {
            userDataRepository.setThemeBrand(themeBrand)
        }
    }

    private fun updateDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        viewModelScope.launch {
            userDataRepository.setDarkThemeConfig(darkThemeConfig)
        }
    }

    private fun updateDynamicColor(useDynamicColor: Boolean) {
        viewModelScope.launch {
            userDataRepository.setDynamicColor(useDynamicColor)
        }
    }

    private fun updateAutoLaunch(useAutoLaunch: Boolean) {
        viewModelScope.launch {
            userDataRepository.setAutoLaunch(useAutoLaunch)
        }
    }

    private fun cleanAppSettings() {
        viewModelScope.launch {
            cleanAppSettingsUseCase()
        }
    }
}
