package com.core.domain.repository

import com.core.model.AppItem

interface PackageRepository {
    suspend fun getNonSystemApps(): List<AppItem>
}