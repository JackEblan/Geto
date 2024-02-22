package com.android.geto.feature.appsettings.navigation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.android.geto.feature.appsettings.AppSettingsRoute
import java.net.URLDecoder
import kotlin.text.Charsets.UTF_8


private val URL_CHARACTER_ENCODING = UTF_8.name()

@VisibleForTesting
internal const val PACKAGE_NAME_ARG = "package_name"

@VisibleForTesting
internal const val APP_NAME_ARG = "app_name"

internal class AppSettingsArgs(val packageName: String, val appName: String) {
    constructor(savedStateHandle: SavedStateHandle) : this(
        URLDecoder.decode(
            checkNotNull(
                savedStateHandle[PACKAGE_NAME_ARG]
            ), URL_CHARACTER_ENCODING
        ), URLDecoder.decode(checkNotNull(savedStateHandle[APP_NAME_ARG]), URL_CHARACTER_ENCODING)
    )
}

fun NavController.navigateToAppSettings(packageName: String, appName: String) {
    navigate("app_settings_route/$packageName/$appName")
}

fun NavGraphBuilder.appSettingsScreen(onArrowBackClick: () -> Unit) {
    composable(
        route = "app_settings_route/{$PACKAGE_NAME_ARG}/{$APP_NAME_ARG}"
    ) {
        AppSettingsRoute(onNavigationIconClick = onArrowBackClick)
    }
}
