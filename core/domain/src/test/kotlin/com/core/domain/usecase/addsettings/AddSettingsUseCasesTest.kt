package com.core.domain.usecase.addsettings

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class AddSettingsUseCasesTest {

    private lateinit var addSettingsUseCases: AddSettingsUseCases

    @Before
    fun setup() {
        addSettingsUseCases = AddSettingsUseCases(
            validateLabel = ValidateLabel(),
            validateKey = ValidateKey(),
            validateValueOnLaunch = ValidateValueOnLaunch(),
            validateValueOnRevert = ValidateValueOnRevert()
        )
    }

    @Test
    fun `when settings key is blank, return false`() {
        val result = addSettingsUseCases.validateKey("")

        assertEquals(expected = result.successful, actual = false)
    }

    @Test
    fun `when settings label is blank, return false`() {
        val result = addSettingsUseCases.validateLabel("")

        assertEquals(expected = result.successful, actual = false)
    }

    @Test
    fun `when settings value on launch is blank, return false`() {
        val result = addSettingsUseCases.validateValueOnLaunch("")

        assertEquals(expected = result.successful, actual = false)
    }

    @Test
    fun `when settings value on revert is blank, return false`() {
        val result = addSettingsUseCases.validateValueOnRevert("")

        assertEquals(expected = result.successful, actual = false)
    }

    @Test
    fun `when settings key is blank, return true`() {
        val result = addSettingsUseCases.validateKey("Test")

        assertEquals(expected = result.successful, actual = true)
    }

    @Test
    fun `when settings label is blank, return true`() {
        val result = addSettingsUseCases.validateLabel("Test")

        assertEquals(expected = result.successful, actual = true)
    }

    @Test
    fun `when settings value on launch is blank, return true`() {
        val result = addSettingsUseCases.validateValueOnLaunch("Test")

        assertEquals(expected = result.successful, actual = true)
    }

    @Test
    fun `when settings value on revert is blank, return true`() {
        val result = addSettingsUseCases.validateValueOnRevert("Test")

        assertEquals(expected = result.successful, actual = true)
    }
}