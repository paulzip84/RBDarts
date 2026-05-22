package com.rbdarts.feature.league

import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

class LeagueMatchFlowTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun leagueSettingsRenders() {
        composeRule.setContent { LeagueSettingsScreen() }
    }
}
