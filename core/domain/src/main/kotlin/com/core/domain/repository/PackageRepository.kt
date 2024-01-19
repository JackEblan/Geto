package com.core.domain.repository

import com.core.model.NonSystemApp
import kotlinx.coroutines.flow.Flow

interface PackageRepository {
    fun getNonSystemApps(): Flow<NonSystemApp>
}