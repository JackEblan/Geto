package com.feature.user_app_settings.domain.use_case

data class ValidationResult(val successful: Boolean = false, val errorMessage: String? = null)
