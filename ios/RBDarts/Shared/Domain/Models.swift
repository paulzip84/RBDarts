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

public enum Post20TieRule: String, Codable, Sendable {
    case repeat20
    case restartAt1
    case bullseyeTiebreaker
}

public enum DartRing: String, Codable, Sendable {
    case miss
    case single
    case double
    case triple
    case outerBull
    case innerBull
}

public enum DartValidity: String, Codable, Sendable {
    case valid
    case wrongNumber
    case bounceOut
    case fellOut
    case foulLine
    case outOfTurn
}

public enum BaseballParticipantKind: String, Codable, Sendable {
    case player
    case team
}

public enum BaseballAnalyticsSubject: String, Codable, Sendable {
    case player
    case team
}

public struct BaseballRuleSet: Codable, Equatable, Sendable {
    public var ruleSetId: String
    public var name: String
    public var regulationInnings: Int
    public var dartsPerTurn: Int
    public var singleValue: Int
    public var doubleValue: Int
    public var tripleValue: Int
    public var extraInningsEnabled: Bool
    public var post20TieRule: Post20TieRule
    public var bullseyeBonusEnabled: Bool
    public var seventhInningStretchEnabled: Bool
    public var mercyRuleEnabled: Bool
    public var handicapEnabled: Bool

    public init(
        ruleSetId: String = "baseball-standard",
        name: String = "Standard Baseball Darts",
        regulationInnings: Int = 9,
        dartsPerTurn: Int = 3,
        singleValue: Int = 1,
        doubleValue: Int = 2,
        tripleValue: Int = 3,
        extraInningsEnabled: Bool = true,
        post20TieRule: Post20TieRule = .repeat20,
        bullseyeBonusEnabled: Bool = false,
        seventhInningStretchEnabled: Bool = false,
        mercyRuleEnabled: Bool = false,
        handicapEnabled: Bool = false
    ) {
        self.ruleSetId = ruleSetId
        self.name = name
        self.regulationInnings = regulationInnings
        self.dartsPerTurn = dartsPerTurn
        self.singleValue = singleValue
        self.doubleValue = doubleValue
        self.tripleValue = tripleValue
        self.extraInningsEnabled = extraInningsEnabled
        self.post20TieRule = post20TieRule
        self.bullseyeBonusEnabled = bullseyeBonusEnabled
        self.seventhInningStretchEnabled = seventhInningStretchEnabled
        self.mercyRuleEnabled = mercyRuleEnabled
        self.handicapEnabled = handicapEnabled
    }
}

public struct BaseballDartThrow: Codable, Equatable, Sendable {
    public let dartThrowId: String
    public let dartIndex: Int
    public let targetNumber: Int
    public let landedNumber: Int?
    public let ring: DartRing
    public let validity: DartValidity

    public init(dartThrowId: String, dartIndex: Int, targetNumber: Int, landedNumber: Int?, ring: DartRing, validity: DartValidity = .valid) {
        self.dartThrowId = dartThrowId
        self.dartIndex = dartIndex
        self.targetNumber = targetNumber
        self.landedNumber = landedNumber
        self.ring = ring
        self.validity = validity
    }
}

public struct BaseballTurn: Codable, Equatable, Sendable {
    public let turnId: String
    public let gameId: String
    public let inningNumber: Int
    public let targetNumber: Int
    public let participantId: String
    public let teamId: String?
    public let playerId: String
    public let lineupSlot: Int
    public let darts: [BaseballDartThrow]
    public let enteredByUserId: String
    public let createdAt: Date
    public let updatedAt: Date
}

public struct BaseballParticipant: Codable, Equatable, Sendable {
    public let participantId: String
    public var displayName: String
    public var kind: BaseballParticipantKind
    public var teamId: String?
    public var playerIds: [String]
    public var lineupOrder: [String]
    public var startingHandicap: Int

    public init(
        participantId: String,
        displayName: String,
        kind: BaseballParticipantKind,
        teamId: String? = nil,
        playerIds: [String] = [],
        lineupOrder: [String] = [],
        startingHandicap: Int = 0
    ) {
        self.participantId = participantId
        self.displayName = displayName
        self.kind = kind
        self.teamId = teamId
        self.playerIds = playerIds
        self.lineupOrder = lineupOrder
        self.startingHandicap = startingHandicap
    }
}

public struct BaseballParticipantSummary: Codable, Equatable, Sendable {
    public let participantId: String
    public let displayName: String
    public let total: Int
    public let inningTotals: [Int: Int]
    public let zeroCount: Int
    public let nineCount: Int
}

public struct BaseballGameSummary: Codable, Equatable, Sendable {
    public let participantSummaries: [BaseballParticipantSummary]
    public let winnerIds: [String]
    public let margin: Int
    public let inningsPlayed: Int
    public let needsExtraInning: Bool
    public let currentTarget: Int
}

public struct BaseballScoreboardSnapshot: Codable, Equatable, Sendable {
    public let gameId: String
    public let currentInning: Int
    public let targetNumber: Int
    public let currentThrowerId: String
    public let currentThrowerDisplayName: String
    public let playerInningScore: Int
    public let playerTotal: Int
    public let teamInningScore: Int?
    public let teamTotal: Int?
    public let leaderId: String?
    public let leadMargin: Int
    public let remainingInnings: Int
    public let needsExtraInning: Bool
    public let gameStatus: GameStatus
}

public struct BaseballAnalyticsSnapshot: Codable, Equatable, Sendable {
    public let subjectId: String
    public let subjectType: BaseballAnalyticsSubject
    public let averageScorePerInning: Double?
    public let highestInningScore: Int?
    public let shutoutInnings: Int
    public let tripleRate: Double?
    public let winPercentage: Double?
    public let projectedFinalScore: Double?
    public let comebackProbability: Double?
    public let consistency: Double?
    public let bestTargetNumbers: [Int]
    public let weakestTargetNumbers: [Int]
    public let isEstimate: Bool
}

public struct BaseballSubstitution: Codable, Equatable, Sendable {
    public let substitutionId: String
    public let gameId: String
    public let teamId: String
    public let outPlayerId: String
    public let inPlayerId: String
    public let effectiveInning: Int
    public let effectiveTurn: Int
    public let authorizedByUserId: String
    public let authorizedByRole: LeagueRole
    public let createdAt: Date
    public let reason: String
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
