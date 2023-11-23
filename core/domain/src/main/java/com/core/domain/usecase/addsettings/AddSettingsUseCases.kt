package com.core.domain.usecase.addsettings

import com.core.domain.usecase.ValidatePackageName
data class AddSettingsUseCases(
    val validateLabel: ValidateLabel,
    val validateKey: ValidateKey,
    val validateValueOnLaunch: ValidateValueOnLaunch,
    val validateValueOnRevert: ValidateValueOnRevert,
    val validatePackageName: ValidatePackageName
)
