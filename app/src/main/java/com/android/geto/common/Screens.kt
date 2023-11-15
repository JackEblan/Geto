package com.android.geto.common

sealed class Screens(val route: String) {
    data object UserAppList : Screens(route = "user_app_list")

    data object UserAppSettings : Screens(route = "user_app_settings")

}
