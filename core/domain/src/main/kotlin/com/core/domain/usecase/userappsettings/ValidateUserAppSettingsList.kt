package com.core.domain.usecase.userappsettings

import com.core.domain.usecase.ValidationResult
import com.core.model.UserAppSettingsItem

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