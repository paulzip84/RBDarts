package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.app.RBDartsRoute
import com.rbdarts.app.protectedRouteOrLogin
import org.junit.Test

class ProtectedRouteTest {
    @Test
    fun protectedRouteRedirectsToLoginWhenSignedOut() {
        assertThat(protectedRouteOrLogin(RBDartsRoute.Handicaps, isAuthenticated = false))
            .isEqualTo(RBDartsRoute.Login)
    }
}
