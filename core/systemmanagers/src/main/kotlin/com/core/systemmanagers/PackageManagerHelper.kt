package com.core.systemmanagers

import com.core.model.AppItem

interface PackageManagerHelper {
    suspend fun getNonSystemAppList(): List<AppItem>
}