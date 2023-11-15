package com.android.geto.domain.use_case.user_app_settings

import com.android.geto.domain.use_case.ValidatePackageName

data class UserAppSettingsUseCases(
    val validateAppName: ValidateAppName,
    val validatePackageName: ValidatePackageName,
    val validateUserAppSettingsList: ValidateUserAppSettingsList
)
