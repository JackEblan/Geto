package com.core.domain.usecase.addsettings

data class AddSettingsUseCases(
    val validateLabel: ValidateLabel,
    val validateKey: ValidateKey,
    val validateValueOnLaunch: ValidateValueOnLaunch,
    val validateValueOnRevert: ValidateValueOnRevert
)
