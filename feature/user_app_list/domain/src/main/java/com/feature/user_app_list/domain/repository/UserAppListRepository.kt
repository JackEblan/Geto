package com.feature.user_app_list.domain.repository

import com.feature.user_app_list.domain.model.AppItem

interface UserAppListRepository {

    suspend fun getUserAppList(): List<AppItem>

}