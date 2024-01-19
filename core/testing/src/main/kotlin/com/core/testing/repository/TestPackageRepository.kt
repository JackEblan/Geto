package com.core.testing.repository

import com.core.domain.repository.PackageRepository
import com.core.model.NonSystemApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow

class TestPackageRepository : PackageRepository {
    private var nonSystemAppsTestData: List<NonSystemApp> = emptyList()
    override fun getNonSystemApps(): Flow<NonSystemApp> {
        return nonSystemAppsTestData.asFlow()
    }

    /**
     * A test-only API to add non system apps list data.
     */
    fun sendNonSystemApps(value: List<NonSystemApp>) {
        nonSystemAppsTestData = value
    }
}