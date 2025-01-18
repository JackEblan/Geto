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
package com.android.geto.feature.apps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.geto.domain.model.GetoApplicationInfo
import com.android.geto.domain.repository.GetoApplicationInfosRepository
import com.android.geto.domain.usecase.UpdateGetoApplicationInfosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppsViewModel @Inject constructor(
    private val updateGetoApplicationInfosUseCase: UpdateGetoApplicationInfosUseCase,
    private val getoApplicationInfosRepository: GetoApplicationInfosRepository,
) : ViewModel() {
    val appsUiState = getoApplicationInfosRepository.getGetoApplicationInfos().onStart {
        updateGetoApplicationInfos()
    }.map(AppsUiState::Success).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = AppsUiState.Loading,
    )

    private var _searchGetoApplicationInfos =
        MutableStateFlow<List<GetoApplicationInfo>>(emptyList())
    val searchGetoApplicationInfos = _searchGetoApplicationInfos.asStateFlow()

    fun getGetoApplicationInfoByPackageName(text: String) {
        viewModelScope.launch {
            _searchGetoApplicationInfos.update {
                getoApplicationInfosRepository.getGetoApplicationInfoByPackageName(text = text)
            }
        }
    }

    private fun updateGetoApplicationInfos() {
        viewModelScope.launch {
            updateGetoApplicationInfosUseCase()
        }
    }
}
