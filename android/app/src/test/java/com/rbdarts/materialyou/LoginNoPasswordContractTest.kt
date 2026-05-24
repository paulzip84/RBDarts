package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.LoginPresentationState
import org.junit.Test

class LoginNoPasswordContractTest {
    @Test
    fun defaultLoginPresentationContainsNoFirstPartyCredentialCopy() {
        val state = LoginPresentationState()

        assertThat(state.hasFirstPartyCredentialCopy).isFalse()
        assertThat(state.visibleText.joinToString(" ")).doesNotContain("Forgot Password")
    }
}
