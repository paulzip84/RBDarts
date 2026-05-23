package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.app.RBDartsDestinations
import com.rbdarts.app.RBDartsRoute
import org.junit.Test

class RBDartsRoutesTest {
    @Test
    fun topLevelRoutesIncludeRequestedSections() {
        assertThat(RBDartsDestinations.topLevel).containsAtLeast(
            RBDartsRoute.GameType,
            RBDartsRoute.Players,
            RBDartsRoute.Seasons,
            RBDartsRoute.Handicaps,
            RBDartsRoute.Scoring,
            RBDartsRoute.Settings
        )
    }
}
