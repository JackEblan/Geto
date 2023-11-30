package com.feature.userapplist

sealed class UserAppListEvent {
    data object GetUserAppList: UserAppListEvent()

    data object RefreshUserAppList: UserAppListEvent()
}
