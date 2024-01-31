package com.core.data.repository

import com.core.domain.repository.ClipboardRepository
import com.core.domain.wrapper.BuildVersionWrapper
import com.core.domain.wrapper.ClipboardManagerWrapper
import javax.inject.Inject

class DefaultClipboardRepository @Inject constructor(
    private val clipboardManagerWrapper: ClipboardManagerWrapper,
    private val buildVersionWrapper: BuildVersionWrapper
) : ClipboardRepository {
    override fun setPrimaryClip(label: String, text: String): String? {
        clipboardManagerWrapper.setPrimaryClip(label = label, text = text)

        return if (buildVersionWrapper.isApi31Higher()) null
        else "$label copied to clipboard"
    }
}