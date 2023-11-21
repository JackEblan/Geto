package com.feature.user_app_list.domain.model

import android.graphics.Bitmap

data class AppItem(
    val icon: Bitmap, val packageName: String, val label: String
)