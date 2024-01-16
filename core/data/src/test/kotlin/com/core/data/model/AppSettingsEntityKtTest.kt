package com.core.data.model

import com.core.database.model.asEntity
import com.core.model.AppSettings
import com.core.model.SettingsType
import org.junit.Test
import kotlin.test.assertEquals

class AppSettingsEntityKtTest {

    @Test
    fun app_settings_can_be_mapped_to_app_settings_entity() {
        val appSettings = AppSettings(
            id = 1,
            enabled = false,
            settingsType = SettingsType.SECURE,
            packageName = "packageName",
            label = "label",
            key = "key",
            valueOnLaunch = "valueOnLaunch",
            valueOnRevert = "valueOnRevert",
            safeToWrite = false
        )

        val entity = appSettings.asEntity()

        assertEquals(1, entity.id)
        assertEquals(false, entity.enabled)
        assertEquals(SettingsType.SECURE, entity.settingsType)
        assertEquals("packageName", entity.packageName)
        assertEquals("label", entity.label)
        assertEquals("key", entity.key)
        assertEquals("valueOnLaunch", entity.valueOnLaunch)
        assertEquals("valueOnRevert", entity.valueOnRevert)
    }
}