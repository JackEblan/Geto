package com.core.domain.usecase

class ValidatePackageName {

    operator fun invoke(packageName: String): ValidationResult {
        if (packageName.isBlank()) {
            return ValidationResult(successful = false, errorMessage = "Package name is invalid")
        }

        return ValidationResult(successful = true)
    }
}