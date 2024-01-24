package com.core.testing.repository

import com.core.domain.repository.PackageRepository
import com.core.model.NonSystemApp

class TestPackageRepository : PackageRepository {
    private var nonSystemAppsTestData: List<NonSystemApp> = emptyList()
    override suspend fun getNonSystemApps(): List<NonSystemApp> {
        return nonSystemAppsTestData
    }

    /**
     * A test-only API to add non system apps list data.
     */
    fun sendNonSystemApps(value: List<NonSystemApp>) {
        nonSystemAppsTestData = value
    }
}