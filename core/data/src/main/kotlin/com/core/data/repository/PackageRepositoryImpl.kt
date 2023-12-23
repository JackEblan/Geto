package com.core.data.repository

import com.core.domain.repository.PackageRepository
import com.core.model.AppItem
import com.core.packagemanager.PackageManagerDataSource
import javax.inject.Inject

class PackageRepositoryImpl @Inject constructor(private val packageManagerDataSource: PackageManagerDataSource) :
    PackageRepository {
    override suspend fun getNonSystemApps(): List<AppItem> {
        return packageManagerDataSource.getNonSystemApps()
    }
}