package com.core.systemmanagers

import com.core.model.AppItem

interface PackageManagerHelper {
    fun getNonSystemAppList(): List<AppItem>
}