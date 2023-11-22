package com.core.domain.use_case.user_app_settings

import com.core.domain.use_case.ValidatePackageName

data class UserAppSettingsUseCases(
    val validateAppName: ValidateAppName,
    val validatePackageName: ValidatePackageName,
    val validateUserAppSettingsList: ValidateUserAppSettingsList
)
