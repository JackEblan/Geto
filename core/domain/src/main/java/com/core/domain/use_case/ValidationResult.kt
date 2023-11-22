package com.core.domain.use_case

data class ValidationResult(val successful: Boolean = false, val errorMessage: String? = null)
