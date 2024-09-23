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

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.geto.core.data.repository.PackageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.annotations.VisibleForTesting
import javax.inject.Inject

@HiltViewModel
class AppsViewModel @Inject constructor(
    private val packageRepository: PackageRepository,
) : ViewModel() {
    private val intent = Intent().apply {
        action = Intent.ACTION_MAIN
        addCategory(Intent.CATEGORY_LAUNCHER)
    }

    @VisibleForTesting
    var flags = 0

    private val _appsUiState = MutableStateFlow<AppsUiState?>(null)

    val appsUiState = _appsUiState.onStart {
        queryIntentActivities()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null,
    )

    private fun queryIntentActivities() {
        viewModelScope.launch {
            _appsUiState.update {
                AppsUiState.Loading
            }

            _appsUiState.update {
                AppsUiState.Success(
                    mappedApplicationInfoList = packageRepository.queryIntentActivities(
                        intent = intent,
                        flags = flags,
                    ),
                )
            }
        }
    }
}
