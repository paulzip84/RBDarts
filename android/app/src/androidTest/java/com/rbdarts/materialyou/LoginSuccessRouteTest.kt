package com.rbdarts.materialyou

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.rbdarts.app.RBDartsRoute
import com.rbdarts.feature.home.HomeScreen
import org.junit.Rule
import org.junit.Test

class LoginSuccessRouteTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun providerSuccessCanRouteToAuthenticatedHome() {
        var signedIn by mutableStateOf(false)
        composeRule.setContent {
            RBDartsComposeTestHarness.Surface(darkTheme = true) {
                if (signedIn) {
                    HomeScreen(onNavigate = {})
                } else {
                    LoginComposeTestHarness.Login(onGoogleSignIn = { signedIn = true })
                }
            }
        }

        composeRule.onNodeWithText("Continue with Google").performClick()

        composeRule.assertVisibleText("Ready to play")
    }
}
