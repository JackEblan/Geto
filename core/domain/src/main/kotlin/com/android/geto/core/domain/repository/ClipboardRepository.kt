package com.android.geto.core.domain.repository

interface ClipboardRepository {

    fun setPrimaryClip(label: String, text: String): String?
}