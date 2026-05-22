import Foundation

public struct LeagueMatchPlan: Equatable, Sendable {
    public let league: League
    public let homeTeam: Team
    public let awayTeam: Team
    public let handicap: HandicapResult
}

public struct LeagueService: Sendable {
    private let repository: RBDartsRepository

    public init(repository: RBDartsRepository = InMemoryRBDartsRepository()) {
        self.repository = repository
    }

    public func createLeague(
        name: String,
        gamesPerMatch: Int,
        playersPerTeamPerGame: Int,
        handicapPercent: Double
    ) async throws -> League {
        let now = Date()
        let league = League(
            leagueId: UUID().uuidString,
            leagueName: name,
            gamesPerMatch: max(1, gamesPerMatch),
            playersPerTeamPerGame: max(1, playersPerTeamPerGame),
            handicapPercent: min(max(handicapPercent, 0), 100),
            handicapRoundingRule: .nearest,
            pointsPerGameWin: 1,
            matchBonusPointValue: 1,
            averageUpdateRule: .afterMatch,
            extraInningsRule: .enabled,
            tieBonusRule: .noBonus,
            createdAt: now,
            updatedAt: now
        )
        try await repository.createLeague(league)
        return league
    }

    public func handicapForGame(
        homeTeamId: String,
        awayTeamId: String,
        homeAverageSum: Double,
        awayAverageSum: Double,
        league: League
    ) -> HandicapResult {
        ScoringRules.handicap(
            teamAverageSums: [homeTeamId: homeAverageSum, awayTeamId: awayAverageSum],
            handicapPercent: league.handicapPercent,
            roundingRule: league.handicapRoundingRule
        )
    }

    public func standings(_ standings: [TeamStanding]) -> [TeamStanding] {
        ScoringRules.rankedStandings(standings)
    }
}
