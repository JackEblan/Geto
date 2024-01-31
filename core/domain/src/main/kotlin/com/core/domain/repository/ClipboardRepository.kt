package com.core.domain.repository

interface ClipboardRepository {

    fun setPrimaryClip(label: String, text: String): String?
}