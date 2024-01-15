package com.feature.securesettingslist

sealed class SecureSettingsListEvent {
    data class GetSecureSettingsList(val selectedRadioOptionIndex: Int) : SecureSettingsListEvent()

    data class OnCopySecureSettingsList(val secureSettings: String?) : SecureSettingsListEvent()
}