package com.android.geto.presentation.user_app_settings

import com.android.geto.domain.model.UserAppSettingsItem

sealed class UserAppSettingsEvent {
    data object GetUserAppSettingsList : UserAppSettingsEvent()

    data object OnOpenAddSettingsDialog : UserAppSettingsEvent()

    data object OnDismissAddSettingsDialog : UserAppSettingsEvent()

    data object OnRevertSettings : UserAppSettingsEvent()

    data object OnLaunchApp : UserAppSettingsEvent()

    data class OnUserAppSettingsItemCheckBoxChange(
        val checked: Boolean, val userAppSettingsItem: UserAppSettingsItem
    ) : UserAppSettingsEvent()

    data class OnDeleteUserAppSettingsItem(
        val userAppSettingsItem: UserAppSettingsItem
    ) : UserAppSettingsEvent()
}
