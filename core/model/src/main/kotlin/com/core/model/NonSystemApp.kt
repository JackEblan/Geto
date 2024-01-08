package com.core.model

data class NonSystemApp(
    val byteArrayIcon: ByteArray? = null, val packageName: String, val label: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NonSystemApp

        if (byteArrayIcon != null) {
            if (other.byteArrayIcon == null) return false
            if (!byteArrayIcon.contentEquals(other.byteArrayIcon)) return false
        } else if (other.byteArrayIcon != null) return false

        return true
    }

    override fun hashCode(): Int {
        return byteArrayIcon?.contentHashCode() ?: 0
    }
}