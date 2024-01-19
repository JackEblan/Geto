package com.core.data.repository

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.core.common.Dispatcher
import com.core.common.GetoDispatchers.Default
import com.core.domain.repository.PackageRepository
import com.core.domain.util.PackageManagerWrapper
import com.core.model.NonSystemApp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class DefaultPackageRepository @Inject constructor(
    private val packageManagerWrapper: PackageManagerWrapper,
    @Dispatcher(Default) private val defaultDispatcher: CoroutineDispatcher
) : PackageRepository {
    override fun getNonSystemApps(): Flow<NonSystemApp> {
        return packageManagerWrapper.getInstalledApplications()
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
            }.sortedBy { it.label }.asFlow().flowOn(defaultDispatcher)
    }
}