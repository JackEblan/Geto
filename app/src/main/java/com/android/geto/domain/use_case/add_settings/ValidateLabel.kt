package com.android.geto.domain.use_case.add_settings

import com.android.geto.domain.use_case.ValidationResult

class ValidateLabel {

    operator fun invoke(label: String): ValidationResult {
        if (label.isBlank()) {
            return ValidationResult(successful = false, errorMessage = "Settings label is blank")
        }

        return ValidationResult(successful = true)
    }
}