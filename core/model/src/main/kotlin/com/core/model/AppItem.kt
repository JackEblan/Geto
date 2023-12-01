package com.core.model

data class AppItem(
    val icon: ByteArray? = null, val packageName: String, val label: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AppItem

        if (!icon.contentEquals(other.icon)) return false

        return true
    }

    override fun hashCode(): Int {
        return icon.contentHashCode()
    }
}