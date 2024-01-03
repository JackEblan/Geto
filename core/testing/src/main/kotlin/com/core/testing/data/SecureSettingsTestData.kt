package com.core.testing.data

import com.core.model.SecureSettings

val secureSettingsTestData = listOf(
    SecureSettings(
        id = 0, name = "system", value = "value"
    ),
    SecureSettings(
        id = 1, name = "secure", value = "value"
    ),
    SecureSettings(
        id = 2, name = "global", value = "value"
    )
)