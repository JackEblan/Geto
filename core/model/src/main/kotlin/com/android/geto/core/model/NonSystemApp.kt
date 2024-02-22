package com.android.geto.core.model

import android.graphics.drawable.Drawable

data class NonSystemApp(
    val icon: Drawable? = null, val packageName: String, val label: String
)