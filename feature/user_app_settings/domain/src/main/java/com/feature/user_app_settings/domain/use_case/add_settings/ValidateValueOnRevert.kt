package com.feature.user_app_settings.domain.use_case.add_settings

import com.feature.user_app_settings.domain.use_case.ValidationResult

class ValidateValueOnRevert {

    operator fun invoke(valueOnRevert: String): ValidationResult {
        if (valueOnRevert.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "Settings value on revert is blank"
            )
        }

        return ValidationResult(successful = true)
    }
}