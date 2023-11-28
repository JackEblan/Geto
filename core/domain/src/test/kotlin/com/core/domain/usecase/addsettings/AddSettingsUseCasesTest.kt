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
    fun `when settings key is blank, returnError`() {
        val result = addSettingsUseCases.validateKey("")

        assertEquals(result.successful, false)
    }

    @Test
    fun `when settings label is blank, returnError`() {
        val result = addSettingsUseCases.validateLabel("")

        assertEquals(result.successful, false)
    }

    @Test
    fun `when settings value on launch is blank, returnError`() {
        val result = addSettingsUseCases.validateValueOnLaunch("")

        assertEquals(result.successful, false)
    }

    @Test
    fun `when settings value on revert is blank, returnError`() {
        val result = addSettingsUseCases.validateValueOnRevert("")

        assertEquals(result.successful, false)
    }
}