import Foundation

public struct StatsService: Sendable {
    public init() {}

    public func gameSummary(participants: [ParticipantScorecard]) -> GameSummary {
        ScoringRules.summarizeStandalone(participants: participants)
    }

    public func standings(_ standings: [TeamStanding]) -> [TeamStanding] {
        ScoringRules.rankedStandings(standings)
    }
}
