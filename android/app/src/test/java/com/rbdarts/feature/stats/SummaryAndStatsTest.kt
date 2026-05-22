package com.rbdarts.feature.stats

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.domain.ParticipantScorecard
import org.junit.Test

class SummaryAndStatsTest {
    @Test
    fun projectionAndSummary() {
        val projection = ProjectionService().baseline(20, 4)
        assertThat(projection.label).isEqualTo("Estimate")
        assertThat(projection.projectedFinalScore).isEqualTo(45.0)

        val summary = StatsService().gameSummary(
            listOf(
                ParticipantScorecard("a", "A", mapOf(1 to 9, 2 to 9)),
                ParticipantScorecard("b", "B", mapOf(1 to 1, 2 to 1))
            )
        )
        assertThat(summary.winnerIds).containsExactly("a")
    }
}
