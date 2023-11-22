package com.core.domain.repository

import com.core.model.AppItem

interface UserAppListRepository {

    suspend fun getUserAppList(): List<AppItem>

}