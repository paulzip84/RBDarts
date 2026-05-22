package com.rbdarts.feature.standalonegame

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.data.InMemoryRBDartsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test

class StandaloneGameTest {
    @Test
    fun createScoreAndCompleteStandaloneGame() = runTest {
        val service = StandaloneGameService(InMemoryRBDartsRepository())
        var session = service.createGame("Test", "user-1", listOf("Avery", "Blake"))

        for (inning in 1..9) {
            session = service.enterScore(inning.coerceAtMost(9), "participant-1", inning, session)
            session = service.enterScore((inning - 1).coerceAtLeast(0), "participant-2", inning, session)
        }

        val summary = service.complete(session)
        assertThat(summary.winnerIds).containsExactly("participant-1")
        assertThat(summary.inningsPlayed).isEqualTo(9)
    }
}
