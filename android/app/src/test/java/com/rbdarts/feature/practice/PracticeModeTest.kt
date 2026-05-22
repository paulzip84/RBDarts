package com.rbdarts.feature.practice

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.domain.PracticeAttempt
import com.rbdarts.core.domain.ScoringRules
import org.junit.Test
import java.time.Instant

class PracticeModeTest {
    @Test
    fun practiceStatsAreIsolatedFromOfficialStats() {
        val attempts = listOf(0, 4, 5, 9, 5).mapIndexed { index, score ->
            PracticeAttempt("p$index", "player-a", "user-a", 5, score, Instant.now(), Instant.now())
        }

        val stats = ScoringRules.practiceStats(attempts)

        assertThat(stats.attempts).isEqualTo(5)
        assertThat(stats.average).isWithin(0.001).of(4.6)
        assertThat(stats.bestScore).isEqualTo(9)
        assertThat(stats.zeroCount).isEqualTo(1)
        assertThat(stats.nineCount).isEqualTo(1)
    }
}
