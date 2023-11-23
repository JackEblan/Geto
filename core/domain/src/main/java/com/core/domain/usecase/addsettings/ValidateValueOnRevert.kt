package com.core.domain.usecase.addsettings

import com.core.domain.usecase.ValidationResult

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