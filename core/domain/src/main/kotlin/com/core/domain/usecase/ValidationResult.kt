package com.core.domain.usecase

data class ValidationResult(val successful: Boolean = false, val errorMessage: String? = null)
