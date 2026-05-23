package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.app.RBDartsRoute
import com.rbdarts.app.protectedRouteOrLogin
import org.junit.Test

class LoginNavigationContractTest {
    @Test
    fun protectedRoutesRedirectToLoginWhenUnauthenticated() {
        assertThat(protectedRouteOrLogin(RBDartsRoute.Scoring, isAuthenticated = false))
            .isEqualTo(RBDartsRoute.Login)
    }
}
