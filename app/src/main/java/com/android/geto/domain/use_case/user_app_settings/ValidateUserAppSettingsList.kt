package com.android.geto.domain.use_case.user_app_settings

import com.android.geto.domain.model.UserAppSettingsItem
import com.android.geto.domain.use_case.ValidationResult

class ValidateUserAppSettingsList {

    operator fun invoke(userAppSettingsList: List<UserAppSettingsItem>): ValidationResult {
        if (userAppSettingsList.isEmpty() || userAppSettingsList.all { !it.enabled }) {
            return ValidationResult(
                successful = false, errorMessage = "Please enable at least one of your settings"
            )
        }

        return ValidationResult(successful = true)
    }
}