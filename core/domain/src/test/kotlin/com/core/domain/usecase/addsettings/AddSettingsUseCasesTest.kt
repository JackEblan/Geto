package com.core.domain.usecase.addsettings

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

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

        assertThat(result.successful).isFalse()
    }

    @Test
    fun `when settings label is blank, return false`() {
        val result = addSettingsUseCases.validateLabel("")

        assertThat(result.successful).isFalse()
    }

    @Test
    fun `when settings value on launch is blank, return false`() {
        val result = addSettingsUseCases.validateValueOnLaunch("")

        assertThat(result.successful).isFalse()
    }

    @Test
    fun `when settings value on revert is blank, return false`() {
        val result = addSettingsUseCases.validateValueOnRevert("")

        assertThat(result.successful).isFalse()
    }
}