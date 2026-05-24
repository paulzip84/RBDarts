package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.app.RBDartsDestinations
import com.rbdarts.app.RBDartsRoute
import org.junit.Test

class NavigationMenuItemContractTest {
    @Test
    fun drawerContainsOnlyAuthenticatedTopLevelDestinations() {
        assertThat(RBDartsDestinations.topLevel).containsExactly(
            RBDartsRoute.Home,
            RBDartsRoute.GameType,
            RBDartsRoute.Players,
            RBDartsRoute.Seasons,
            RBDartsRoute.Handicaps,
            RBDartsRoute.Scoring,
            RBDartsRoute.Settings
        ).inOrder()
    }

    @Test
    fun loadingAndLoginStayOutsideAuthenticatedNavigation() {
        assertThat(RBDartsDestinations.topLevel).doesNotContain(RBDartsRoute.Loading)
        assertThat(RBDartsDestinations.topLevel).doesNotContain(RBDartsRoute.Login)
    }
}
