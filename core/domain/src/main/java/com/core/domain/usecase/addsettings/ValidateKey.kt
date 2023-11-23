package com.core.domain.usecase.addsettings

import com.core.domain.usecase.ValidationResult

class ValidateKey {

    operator fun invoke(key: String): ValidationResult {
        if (key.isBlank()) {
            return ValidationResult(successful = false, errorMessage = "Settings key is blank")
        }

        return ValidationResult(successful = true)
    }
}