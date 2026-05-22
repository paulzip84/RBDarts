import Foundation

public protocol RBDartsRepository: Sendable {
    func createStandaloneGame(_ game: StandaloneGame, participants: [ParticipantScorecard]) async throws
    func saveStandaloneSummary(_ summary: GameSummary, for gameId: String) async throws
    func createLeague(_ league: League) async throws
    func createMatch(_ match: Match) async throws
    func savePracticeAttempt(_ attempt: PracticeAttempt) async throws
    func requestCorrection(_ correction: CorrectionAuditRecord) async throws
}

public actor InMemoryRBDartsRepository: RBDartsRepository {
    public private(set) var standaloneGames: [String: StandaloneGame] = [:]
    public private(set) var standaloneSummaries: [String: GameSummary] = [:]
    public private(set) var leagues: [String: League] = [:]
    public private(set) var matches: [String: Match] = [:]
    public private(set) var practiceAttempts: [PracticeAttempt] = []
    public private(set) var corrections: [CorrectionAuditRecord] = []

    public init() {}

    public func createStandaloneGame(_ game: StandaloneGame, participants: [ParticipantScorecard]) {
        standaloneGames[game.standaloneGameId] = game
    }

    public func saveStandaloneSummary(_ summary: GameSummary, for gameId: String) {
        standaloneSummaries[gameId] = summary
    }

    public func createLeague(_ league: League) {
        leagues[league.leagueId] = league
    }

    public func createMatch(_ match: Match) {
        matches[match.matchId] = match
    }

    public func savePracticeAttempt(_ attempt: PracticeAttempt) {
        practiceAttempts.append(attempt)
    }

    public func requestCorrection(_ correction: CorrectionAuditRecord) {
        corrections.append(correction)
    }
}
