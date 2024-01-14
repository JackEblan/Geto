package com.core.domain.repository

import com.core.model.NonSystemApp

interface PackageRepository {
    suspend fun getNonSystemApps(): List<NonSystemApp>
}