package com.core.data.repository

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.core.common.Dispatcher
import com.core.common.GetoDispatchers.Default
import com.core.domain.repository.PackageRepository
import com.core.domain.wrapper.PackageManagerWrapper
import com.core.model.NonSystemApp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DefaultPackageRepository @Inject constructor(
    private val packageManagerWrapper: PackageManagerWrapper,
    @Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher
) : PackageRepository {
    override suspend fun getNonSystemApps(): List<NonSystemApp> {
        return withContext(defaultDispatcher) {
            packageManagerWrapper.getInstalledApplications()
                .filter { (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0 }.map {
                    val label = packageManagerWrapper.getApplicationLabel(it)

                    val icon = try {
                        packageManagerWrapper.getApplicationIcon(it)
                    } catch (e: PackageManager.NameNotFoundException) {
                        null
                    }

                    NonSystemApp(
                        icon = icon, packageName = it.packageName, label = label
                    )
                }.sortedBy { it.label }
        }
    }
}