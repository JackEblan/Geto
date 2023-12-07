package com.core.domain.usecase.addsettings

import com.core.domain.usecase.ValidationResult

class ValidateValueOnLaunch {

    operator fun invoke(valueOnLaunch: String): ValidationResult {
        if (valueOnLaunch.isBlank()) {
            return ValidationResult(
                successful = false, errorMessage = "Settings value on launch is blank"
            )
        }

        return ValidationResult(successful = true)
    }
}