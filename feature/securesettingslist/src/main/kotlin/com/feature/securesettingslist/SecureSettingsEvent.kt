package com.feature.securesettingslist

sealed class SecureSettingsEvent {
    data class GetSecureSettings(val selectedRadioOptionIndex: Int) : SecureSettingsEvent()

    data class OnCopySecureSettings(val secureSettings: String?) : SecureSettingsEvent()
}