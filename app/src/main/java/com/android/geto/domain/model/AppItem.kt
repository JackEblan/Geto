package com.android.geto.domain.model

import android.graphics.Bitmap
import androidx.room.Entity

@Entity
data class AppItem(
    val icon: Bitmap, val packageName: String, val label: String
)