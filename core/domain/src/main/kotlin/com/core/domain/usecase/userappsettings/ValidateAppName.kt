package com.core.domain.usecase.userappsettings

import com.core.domain.usecase.ValidationResult

class ValidateAppName {

    operator fun invoke(appName: String): ValidationResult {
        if (appName.isBlank()) {
            return ValidationResult(successful = false, errorMessage = "App name is invalid")
        }

        return ValidationResult(successful = true)
    }
}