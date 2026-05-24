package com.rbdarts.materialyou

import androidx.compose.runtime.Composable
import com.rbdarts.core.ui.AuthUiState
import com.rbdarts.feature.auth.LoginScreen

object LoginComposeTestHarness {
    @Composable
    fun Login(
        state: AuthUiState = AuthUiState(),
        darkTheme: Boolean = true,
        onGoogleSignIn: () -> Unit = {},
        onFacebookSignIn: () -> Unit = {}
    ) {
        RBDartsComposeTestHarness.Surface(darkTheme = darkTheme) {
            LoginScreen(
                state = state,
                configuration = RBDartsComposeTestHarness.configuration,
                onGoogleSignIn = onGoogleSignIn,
                onFacebookSignIn = onFacebookSignIn
            )
        }
    }
}
