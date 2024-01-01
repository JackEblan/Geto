package com.core.domain.usecase

import com.core.domain.repository.SettingsResultMessage
import com.core.testing.data.appSettingsTestData
import com.core.testing.repository.TestSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertIs
import kotlin.test.assertTrue

class ValidateSettingsListTest {
    private lateinit var validateSettingsList: ValidateSettingsList

    private lateinit var settingsRepository: TestSettingsRepository

    @Before
    fun setup() {
        settingsRepository = TestSettingsRepository()

        validateSettingsList = ValidateSettingsList(settingsRepository)
    }

    @Test
    fun `When appSettingsList is empty or no enabled items then return Result failure`() = runTest {
        val result = validateSettingsList(emptyList())

        assertTrue { result.isFailure }
    }

    @Test
    fun `When appSettingsList is not empty or atleast one item is enabled then return settingsRepository applySettings as Result`() =
        runTest {
            val result = validateSettingsList(appSettingsTestData)

            assertIs<Result<SettingsResultMessage>>(result)
        }
}