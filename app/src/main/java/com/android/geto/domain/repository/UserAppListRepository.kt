package com.android.geto.domain.repository

import com.android.geto.domain.model.AppItem

interface UserAppListRepository {

    suspend fun getUserAppList(): List<AppItem>

}