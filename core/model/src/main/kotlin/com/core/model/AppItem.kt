package com.core.model

import android.graphics.drawable.Drawable

data class AppItem(
    val icon: Drawable? = null, val packageName: String, val label: String
)