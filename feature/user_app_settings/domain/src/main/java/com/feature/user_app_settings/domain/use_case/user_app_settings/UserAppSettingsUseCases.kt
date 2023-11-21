package com.feature.user_app_settings.domain.use_case.user_app_settings

import com.feature.user_app_settings.domain.use_case.ValidatePackageName

data class UserAppSettingsUseCases(
    val validateAppName: ValidateAppName,
    val validatePackageName: ValidatePackageName,
    val validateUserAppSettingsList: ValidateUserAppSettingsList
)
