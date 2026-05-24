package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.PlayerHandicapState
import org.junit.Test

class PlayerHandicapStateTest {
    @Test
    fun derivesHandicapFromRecentAverageWhenNoOverrideExists() {
        val state = PlayerHandicapState("Morgan", recentAverage = 36)

        assertThat(state.derivedHandicap).isEqualTo(3)
        assertThat(state.projectedScoreWithHandicap).isEqualTo(39)
    }
}
