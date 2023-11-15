package com.android.geto.presentation.user_app_settings.components

data class AddSettingsDialogState(
    val selectedRadioOptionIndex: Int = 0,
    val label: String = "",
    val labelError: String? = null,
    val key: String = "",
    val keyError: String? = null,
    val valueOnLaunch: String = "",
    val valueOnLaunchError: String? = null,
    val valueOnRevert: String = "",
    val valueOnRevertError: String? = null
)
