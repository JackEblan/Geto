package com.core.packagemanager

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.core.common.Dispatcher
import com.core.common.GetoDispatchers.IO
import com.core.model.AppItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PackageManagerDataSourceImpl @Inject constructor(
    private val packageManager: PackageManager,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : PackageManagerDataSource {
    override suspend fun getNonSystemApps(): List<AppItem> {
        return withContext(ioDispatcher) {
            packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
                .filter { (it.flags and ApplicationInfo.FLAG_SYSTEM) == 0 }.map {
                    val label = packageManager.getApplicationLabel(it).toString()

                    val icon = try {
                        packageManager.getApplicationIcon(it.packageName)
                    } catch (e: PackageManager.NameNotFoundException) {
                        null
                    }

                    AppItem(icon = icon, packageName = it.packageName, label = label)
                }.sortedBy { it.label }
        }
    }
}