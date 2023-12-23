package com.core.packagemanager

import com.core.model.AppItem

interface PackageManagerDataSource {

    suspend fun getNonSystemApps(): List<AppItem>
}