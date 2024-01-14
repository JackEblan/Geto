package com.core.testing.repository

import com.core.domain.repository.PackageRepository
import com.core.model.NonSystemApp
import com.core.testing.data.nonSystemAppsTestData

class TestPackageRepository : PackageRepository {
    override suspend fun getNonSystemApps(): List<NonSystemApp> {
        return nonSystemAppsTestData
    }
}