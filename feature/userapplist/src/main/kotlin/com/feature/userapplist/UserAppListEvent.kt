package com.feature.userapplist

sealed class UserAppListEvent {
    data object GetNonSystemApps : UserAppListEvent()
}