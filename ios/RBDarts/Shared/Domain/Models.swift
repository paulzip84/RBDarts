import Foundation

public enum AccountStatus: String, Codable, Sendable {
    case active
    case disabled
    case deleted
}

public enum ActiveStatus: String, Codable, Sendable {
    case active
    case inactive
}

public enum LeagueRole: String, Codable, CaseIterable, Sendable {
    case leagueManager = "LeagueManager"
    case teamManager = "TeamManager"
    case scorekeeper = "Scorekeeper"
    case player = "Player"
    case viewer = "Viewer"
    case admin = "Admin"
}

public enum ParticipantType: String, Codable, Sendable {
    case singles
    case teams
    case player
    case team
}

public enum GameStatus: String, Codable, Sendable {
    case setup
    case scoring
    case complete
    case locked
    case finalized
    case corrected
    case voided
}

public enum MatchStatus: String, Codable, Sendable {
    case scheduled
    case inProgress
    case finalized
    case corrected
    case voided
}

public enum HandicapRoundingRule: String, Codable, CaseIterable, Sendable {
    case nearest
    case floor
    case ceiling
    case decimal
}

public enum AverageUpdateRule: String, Codable, Sendable {
    case afterMatch
}

public enum ExtraInningsRule: String, Codable, Sendable {
    case enabled
    case disabled
    case leagueTieRule
}

public enum TieBonusRule: String, Codable, Sendable {
    case noBonus
    case splitBonus
    case tiebreakerGame
    case rawScoreTiebreak
}

public enum CorrectionStatus: String, Codable, Sendable {
    case requested
    case approved
    case rejected
    case applied
}

public struct UserAccount: Identifiable, Codable, Equatable, Sendable {
    public var id: String { userId }
    public let userId: String
    public var displayName: String
    public var email: String?
    public var linkedProviderIds: [String]
    public var accountStatus: AccountStatus
    public var createdAt: Date
    public var updatedAt: Date
}

public struct RoleAssignment: Identifiable, Codable, Equatable, Sendable {
    public var id: String { roleAssignmentId }
    public let roleAssignmentId: String
    public let userId: String
    public let leagueId: String
    public let teamId: String?
    public var role: LeagueRole
    public var permissions: Set<String>
    public var createdAt: Date
    public var updatedAt: Date
}

public struct Player: Identifiable, Codable, Equatable, Sendable {
    public var id: String { playerId }
    public let playerId: String
    public var userId: String?
    public var firstName: String?
    public var lastName: String?
    public var displayName: String
    public var activeStatus: ActiveStatus
    public var createdAt: Date
    public var updatedAt: Date
}

public struct League: Identifiable, Codable, Equatable, Sendable {
    public var id: String { leagueId }
    public let leagueId: String
    public var leagueName: String
    public var gamesPerMatch: Int
    public var playersPerTeamPerGame: Int
    public var handicapPercent: Double
    public var handicapRoundingRule: HandicapRoundingRule
    public var pointsPerGameWin: Double
    public var matchBonusPointValue: Double
    public var averageUpdateRule: AverageUpdateRule
    public var extraInningsRule: ExtraInningsRule
    public var tieBonusRule: TieBonusRule
    public var createdAt: Date
    public var updatedAt: Date
}

public struct Team: Identifiable, Codable, Equatable, Sendable {
    public var id: String { teamId }
    public let teamId: String
    public let leagueId: String
    public var teamName: String
    public var captainPlayerId: String?
    public var activeStatus: ActiveStatus
    public var createdAt: Date
    public var updatedAt: Date
}

public struct Match: Identifiable, Codable, Equatable, Sendable {
    public var id: String { matchId }
    public let matchId: String
    public let leagueId: String
    public var matchDate: Date
    public var awayTeamId: String
    public var homeTeamId: String
    public var status: MatchStatus
    public var awayTeamMatchPoints: Double
    public var homeTeamMatchPoints: Double
    public var awayTeamAdjustedTotal: Double
    public var homeTeamAdjustedTotal: Double
    public var matchBonusWinnerTeamId: String?
}

public struct Game: Identifiable, Codable, Equatable, Sendable {
    public var id: String { gameId }
    public let gameId: String
    public var matchId: String?
    public var standaloneGameId: String?
    public var gameNumber: Int
    public var status: GameStatus
    public var awayTeamRawScore: Int
    public var homeTeamRawScore: Int
    public var awayTeamHandicap: Double
    public var homeTeamHandicap: Double
    public var winningTeamId: String?
    public var inningsPlayed: Int
    public var lockedAt: Date?
}

public struct StandaloneGame: Identifiable, Codable, Equatable, Sendable {
    public var id: String { standaloneGameId }
    public let standaloneGameId: String
    public let createdByUserId: String
    public var gameName: String
    public var gameDate: Date
    public var participantType: ParticipantType
    public var regulationInnings: Int
    public var extraInningsEnabled: Bool
    public var status: GameStatus
    public var createdAt: Date
    public var completedAt: Date?
}

public struct GameLineup: Identifiable, Codable, Equatable, Sendable {
    public var id: String { gameLineupId }
    public let gameLineupId: String
    public let gameId: String
    public let teamId: String?
    public let playerId: String
    public let lineupSlot: Int
    public let playerAverageAtStartOfMatch: Double
}

public struct InningScore: Identifiable, Codable, Equatable, Sendable {
    public var id: String { inningScoreId }
    public let inningScoreId: String
    public let gameId: String
    public let playerId: String
    public let teamId: String?
    public let inningNumber: Int
    public var target: Int
    public var score: Int
    public let enteredByUserId: String
    public var createdAt: Date
    public var updatedAt: Date
}

public struct PracticeAttempt: Identifiable, Codable, Equatable, Sendable {
    public var id: String { practiceAttemptId }
    public let practiceAttemptId: String
    public let playerId: String
    public let userId: String
    public let target: Int
    public let score: Int
    public let attemptDate: Date
    public let createdAt: Date
}

public struct CorrectionAuditRecord: Identifiable, Codable, Equatable, Sendable {
    public var id: String { correctionId }
    public let correctionId: String
    public let leagueId: String
    public let matchId: String
    public let gameId: String
    public let inningScoreId: String
    public let editedByUserId: String
    public let editedByRole: LeagueRole
    public let reason: String
    public let previousValue: Int
    public let newValue: Int
    public let affectedRecords: [String]
    public var status: CorrectionStatus
    public let createdAt: Date
    public var appliedAt: Date?
}
