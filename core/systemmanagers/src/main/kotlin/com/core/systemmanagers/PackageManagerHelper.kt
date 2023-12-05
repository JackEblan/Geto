package com.core.systemmanagers

import com.core.model.AppItem
import kotlinx.coroutines.flow.Flow

interface PackageManagerHelper {
    fun getNonSystemAppList(): Flow<List<AppItem>>
}