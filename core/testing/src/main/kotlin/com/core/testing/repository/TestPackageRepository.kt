package com.core.testing.repository

import com.core.domain.repository.PackageRepository
import com.core.model.AppItem

class TestPackageRepository : PackageRepository {
    override suspend fun getNonSystemApps(): List<AppItem> {
        return listOf(AppItem(icon = null, packageName = "com.android.geto", label = "Geto"))
    }
}