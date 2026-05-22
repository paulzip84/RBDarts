package com.rbdarts.feature.league

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.data.InMemoryRBDartsRepository
import com.rbdarts.core.domain.TeamStanding
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LeagueMatchTest {
    @Test
    fun handicapAndStandingsRanking() = runTest {
        val service = LeagueService(InMemoryRBDartsRepository())
        val league = service.createLeague("League", 3, 4, 80.0)

        val handicap = service.handicapForGame("home", "away", 72.0, 63.4, league)
        assertThat(handicap.lowerAverageTeamId).isEqualTo("away")
        assertThat(handicap.handicap).isEqualTo(7.0)

        val ranked = service.standings(
            listOf(
                TeamStanding("b", 12.0, 6, 12, 320.0, 318),
                TeamStanding("a", 12.0, 6, 24, 310.0, 300)
            )
        )
        assertThat(ranked.map { it.teamId }).containsExactly("a", "b").inOrder()
    }
}
