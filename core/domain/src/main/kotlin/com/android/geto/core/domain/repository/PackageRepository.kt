package com.android.geto.core.domain.repository

import com.android.geto.core.model.NonSystemApp

interface PackageRepository {
    suspend fun getNonSystemApps(): List<NonSystemApp>
}