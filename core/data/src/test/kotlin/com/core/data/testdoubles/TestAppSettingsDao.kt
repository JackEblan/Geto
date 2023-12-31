package com.core.data.testdoubles

import com.core.database.dao.AppSettingsDao
import com.core.database.model.AppSettingsItemEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TestAppSettingsDao : AppSettingsDao {

    private var _entitiesStateFlow = MutableStateFlow(emptyList<AppSettingsItemEntity>())

    val entitiesStateFlow = _entitiesStateFlow.asStateFlow()

    override suspend fun upsert(entity: AppSettingsItemEntity) {
        _entitiesStateFlow.update { currentList ->

            val updatedList = currentList.toMutableList()

            updatedList.find { it.key == entity.key }?.let {
                updatedList.remove(it)
            }

            updatedList.add(entity)

            updatedList
        }
    }


    override suspend fun delete(entity: AppSettingsItemEntity) {
        _entitiesStateFlow.update { entities ->
            entities.filterNot { it.key == entity.key }
        }
    }

    override fun getUserAppSettingsList(packageName: String): Flow<List<AppSettingsItemEntity>> {
        return _entitiesStateFlow.map { userAppSettingsItemEntities ->
            userAppSettingsItemEntities.filter { it.packageName == packageName }
        }
    }
}