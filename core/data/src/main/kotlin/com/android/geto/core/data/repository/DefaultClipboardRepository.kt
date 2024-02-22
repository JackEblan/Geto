package com.android.geto.core.data.repository

import com.android.geto.core.domain.repository.ClipboardRepository
import com.android.geto.core.domain.wrapper.BuildVersionWrapper
import com.android.geto.core.domain.wrapper.ClipboardManagerWrapper
import javax.inject.Inject

class DefaultClipboardRepository @Inject constructor(
    private val clipboardManagerWrapper: ClipboardManagerWrapper,
    private val buildVersionWrapper: BuildVersionWrapper
) : ClipboardRepository {
    override fun setPrimaryClip(label: String, text: String): String? {
        clipboardManagerWrapper.setPrimaryClip(label = label, text = text)

        return if (buildVersionWrapper.isApi32Higher()) null
        else "$text copied to clipboard"
    }
}