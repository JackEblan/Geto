package com.android.geto.feature.appsettings

import com.android.geto.core.model.AppSettings

sealed interface AppSettingsUiState {
    data class Success(val appSettingsList: List<AppSettings>) : AppSettingsUiState

    data object Loading : AppSettingsUiState

    data object Empty : AppSettingsUiState
}