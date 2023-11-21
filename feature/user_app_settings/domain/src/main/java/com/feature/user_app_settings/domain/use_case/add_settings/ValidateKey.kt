package com.feature.user_app_settings.domain.use_case.add_settings

import com.feature.user_app_settings.domain.use_case.ValidationResult

class ValidateKey {

    operator fun invoke(key: String): ValidationResult {
        if (key.isBlank()) {
            return ValidationResult(successful = false, errorMessage = "Settings key is blank")
        }

        return ValidationResult(successful = true)
    }
}