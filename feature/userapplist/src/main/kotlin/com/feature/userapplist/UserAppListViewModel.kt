package com.feature.userapplist

import androidx.lifecycle.ViewModel
import com.core.model.AppItem
import com.core.systemmanagers.PackageManagerHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UserAppListViewModel @Inject constructor(packageManagerHelper: PackageManagerHelper) :
    ViewModel() {

    private val _apps = MutableStateFlow<List<AppItem>>(emptyList())

    val apps = _apps.asStateFlow()

    init {
        _apps.value = packageManagerHelper.getNonSystemAppList()
    }
}