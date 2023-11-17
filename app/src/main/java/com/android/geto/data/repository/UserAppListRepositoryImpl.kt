package com.android.geto.data.repository

import android.content.pm.PackageManager
import com.android.geto.data.mappers.toAppItemList
import com.android.geto.domain.model.AppItem
import com.android.geto.domain.repository.UserAppListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserAppListRepositoryImpl @Inject constructor(
    private val defaultDispatcher: CoroutineDispatcher, private val packageManager: PackageManager
) : UserAppListRepository {

    override suspend fun getUserAppList(): List<AppItem> {
        return withContext(defaultDispatcher) {
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
                .toAppItemList(packageManager = packageManager)
        }
    }
}