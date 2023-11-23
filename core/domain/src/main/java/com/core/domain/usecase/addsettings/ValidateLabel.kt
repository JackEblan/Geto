package com.core.domain.usecase.addsettings

import com.core.domain.usecase.ValidationResult

class ValidateLabel {

    operator fun invoke(label: String): ValidationResult {
        if (label.isBlank()) {
            return ValidationResult(successful = false, errorMessage = "Settings label is blank")
        }

        return ValidationResult(successful = true)
    }
}