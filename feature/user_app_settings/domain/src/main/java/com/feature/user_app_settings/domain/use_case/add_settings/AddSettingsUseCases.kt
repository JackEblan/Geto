package com.feature.user_app_settings.domain.use_case.add_settings

import com.feature.user_app_settings.domain.use_case.ValidatePackageName

data class AddSettingsUseCases(
    val validateLabel: ValidateLabel,
    val validateKey: ValidateKey,
    val validateValueOnLaunch: ValidateValueOnLaunch,
    val validateValueOnRevert: ValidateValueOnRevert,
    val validatePackageName: ValidatePackageName
)
