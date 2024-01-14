package com.core.data.repository

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.core.common.Dispatcher
import com.core.common.GetoDispatchers.Default
import com.core.domain.repository.PackageRepository
import com.core.domain.util.ByteArrayOutputStreamWrapper
import com.core.domain.util.PackageManagerWrapper
import com.core.model.NonSystemApp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DefaultPackageRepository @Inject constructor(
    private val packageManagerWrapper: PackageManagerWrapper,
    private val byteArrayOutputStreamWrapper: ByteArrayOutputStreamWrapper,
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

                    val byteArrayIcon = byteArrayOutputStreamWrapper.drawableToByteArray(
                        drawable = icon
                    )

                    byteArrayOutputStreamWrapper.reset()

                    NonSystemApp(
                        byteArrayIcon = byteArrayIcon, packageName = it.packageName, label = label
                    )
                }.sortedBy { it.label }
        }
    }
}