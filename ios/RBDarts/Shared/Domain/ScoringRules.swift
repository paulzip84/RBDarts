import Foundation

public enum ScoreValidationError: LocalizedError, Equatable, Sendable {
    case invalidScore(Int)
    case invalidInning(Int)
    case lockedGame
    case correctionReasonRequired

    public var errorDescription: String? {
        switch self {
        case .invalidScore:
            return "Scores must be whole numbers from 0 through 9."
        case .invalidInning:
            return "Inning numbers must be positive."
        case .lockedGame:
            return "Locked games require an authorized correction."
        case .correctionReasonRequired:
            return "A correction reason is required."
        }
    }
}

public struct ParticipantScorecard: Codable, Equatable, Sendable {
    public let participantId: String
    public var displayName: String
    public var scoresByInning: [Int: Int]
    public var average: Double?

    public init(participantId: String, displayName: String, scoresByInning: [Int: Int] = [:], average: Double? = nil) {
        self.participantId = participantId
        self.displayName = displayName
        self.scoresByInning = scoresByInning
        self.average = average
    }
}

public struct ParticipantSummary: Codable, Equatable, Sendable {
    public let participantId: String
    public let displayName: String
    public let total: Int
    public let zeroCount: Int
    public let nineCount: Int
    public let performanceVersusAverage: Double?
}

public struct GameSummary: Codable, Equatable, Sendable {
    public let participantSummaries: [ParticipantSummary]
    public let winnerIds: [String]
    public let margin: Int
    public let inningsPlayed: Int
    public let needsExtraInning: Bool
}

public struct HandicapResult: Codable, Equatable, Sendable {
    public let lowerAverageTeamId: String?
    public let higherAverageTeamId: String?
    public let handicap: Double
}

public struct PracticeStats: Codable, Equatable, Sendable {
    public let attempts: Int
    public let average: Double
    public let bestScore: Int
    public let zeroCount: Int
    public let nineCount: Int
    public let recentTrend: Double
}

public struct TeamStanding: Codable, Equatable, Sendable {
    public let teamId: String
    public let leaguePoints: Double
    public let gamesWon: Int
    public let pointDifferential: Int
    public let totalAdjustedScore: Double
    public let totalRawScore: Int
}

public enum ScoringRules {
    public static let defaultRegulationInnings = 9

    public static func validate(score: Int) throws {
        guard (0...9).contains(score) else {
            throw ScoreValidationError.invalidScore(score)
        }
    }

    public static func validate(inningNumber: Int) throws {
        guard inningNumber > 0 else {
            throw ScoreValidationError.invalidInning(inningNumber)
        }
    }

    public static func target(forInning inningNumber: Int) throws -> Int {
        try validate(inningNumber: inningNumber)
        return inningNumber
    }

    public static func recordScore(
        _ score: Int,
        inningNumber: Int,
        for participant: inout ParticipantScorecard
    ) throws {
        try validate(score: score)
        try validate(inningNumber: inningNumber)
        participant.scoresByInning[inningNumber] = score
    }

    public static func participantTotal(
        _ participant: ParticipantScorecard,
        through inningsPlayed: Int? = nil
    ) -> Int {
        participant.scoresByInning.reduce(into: 0) { result, item in
            if inningsPlayed == nil || item.key <= inningsPlayed! {
                result += item.value
            }
        }
    }

    public static func summarizeStandalone(
        participants: [ParticipantScorecard],
        regulationInnings: Int = defaultRegulationInnings,
        extraInningsEnabled: Bool = true
    ) -> GameSummary {
        let maxEnteredInning = participants
            .flatMap { $0.scoresByInning.keys }
            .max() ?? regulationInnings
        let inningsPlayed = max(regulationInnings, maxEnteredInning)

        let summaries = participants.map { participant in
            let total = participantTotal(participant, through: inningsPlayed)
            let enteredScores = participant.scoresByInning
                .filter { $0.key <= inningsPlayed }
                .map(\.value)
            return ParticipantSummary(
                participantId: participant.participantId,
                displayName: participant.displayName,
                total: total,
                zeroCount: enteredScores.filter { $0 == 0 }.count,
                nineCount: enteredScores.filter { $0 == 9 }.count,
                performanceVersusAverage: participant.average.map { Double(total) - $0 }
            )
        }

        let highestTotal = summaries.map(\.total).max() ?? 0
        let winnerIds = summaries
            .filter { $0.total == highestTotal }
            .map(\.participantId)
        let orderedTotals = summaries.map(\.total).sorted(by: >)
        let margin = orderedTotals.count > 1 ? orderedTotals[0] - orderedTotals[1] : 0
        let regulationComplete = inningsPlayed >= regulationInnings
        let tied = winnerIds.count > 1

        return GameSummary(
            participantSummaries: summaries.sorted { $0.displayName < $1.displayName },
            winnerIds: tied ? [] : winnerIds,
            margin: tied ? 0 : margin,
            inningsPlayed: inningsPlayed,
            needsExtraInning: regulationComplete && tied && extraInningsEnabled
        )
    }

    public static func handicap(
        teamAverageSums: [String: Double],
        handicapPercent: Double,
        roundingRule: HandicapRoundingRule
    ) -> HandicapResult {
        guard teamAverageSums.count == 2 else {
            return HandicapResult(lowerAverageTeamId: nil, higherAverageTeamId: nil, handicap: 0)
        }

        let ordered = teamAverageSums.sorted { lhs, rhs in
            if lhs.value == rhs.value { return lhs.key < rhs.key }
            return lhs.value < rhs.value
        }

        guard let lower = ordered.first, let higher = ordered.last, lower.value < higher.value else {
            return HandicapResult(lowerAverageTeamId: nil, higherAverageTeamId: nil, handicap: 0)
        }

        let raw = (higher.value - lower.value) * (handicapPercent / 100.0)
        let rounded: Double
        switch roundingRule {
        case .nearest:
            rounded = raw.rounded()
        case .floor:
            rounded = Foundation.floor(raw)
        case .ceiling:
            rounded = Foundation.ceil(raw)
        case .decimal:
            rounded = raw
        }

        return HandicapResult(
            lowerAverageTeamId: lower.key,
            higherAverageTeamId: higher.key,
            handicap: rounded
        )
    }

    public static func lockableGameIds(
        games: [Game],
        whenStartingGameNumber gameNumber: Int
    ) -> [String] {
        games
            .filter { $0.gameNumber < gameNumber && $0.status == .complete }
            .map(\.gameId)
    }

    public static func applyCorrection(
        score: InningScore,
        newValue: Int,
        reason: String,
        editedByUserId: String,
        editedByRole: LeagueRole,
        affectedRecords: [String],
        now: Date = Date()
    ) throws -> (InningScore, CorrectionAuditRecord) {
        try validate(score: newValue)
        guard !reason.trimmingCharacters(in: .whitespacesAndNewlines).isEmpty else {
            throw ScoreValidationError.correctionReasonRequired
        }

        var correctedScore = score
        correctedScore.score = newValue
        correctedScore.updatedAt = now

        let audit = CorrectionAuditRecord(
            correctionId: UUID().uuidString,
            leagueId: score.teamId ?? "standalone",
            matchId: score.gameId,
            gameId: score.gameId,
            inningScoreId: score.inningScoreId,
            editedByUserId: editedByUserId,
            editedByRole: editedByRole,
            reason: reason,
            previousValue: score.score,
            newValue: newValue,
            affectedRecords: affectedRecords,
            status: .applied,
            createdAt: now,
            appliedAt: now
        )
        return (correctedScore, audit)
    }

    public static func practiceStats(from attempts: [PracticeAttempt]) -> PracticeStats {
        guard !attempts.isEmpty else {
            return PracticeStats(attempts: 0, average: 0, bestScore: 0, zeroCount: 0, nineCount: 0, recentTrend: 0)
        }

        let scores = attempts.map(\.score)
        let average = Double(scores.reduce(0, +)) / Double(scores.count)
        let recent = scores.suffix(3)
        let previous = scores.dropLast(min(3, scores.count)).suffix(3)
        let recentAverage = recent.isEmpty ? average : Double(recent.reduce(0, +)) / Double(recent.count)
        let previousAverage = previous.isEmpty ? average : Double(previous.reduce(0, +)) / Double(previous.count)

        return PracticeStats(
            attempts: scores.count,
            average: average,
            bestScore: scores.max() ?? 0,
            zeroCount: scores.filter { $0 == 0 }.count,
            nineCount: scores.filter { $0 == 9 }.count,
            recentTrend: recentAverage - previousAverage
        )
    }

    public static func rankedStandings(_ standings: [TeamStanding]) -> [TeamStanding] {
        standings.sorted { lhs, rhs in
            if lhs.leaguePoints != rhs.leaguePoints { return lhs.leaguePoints > rhs.leaguePoints }
            if lhs.gamesWon != rhs.gamesWon { return lhs.gamesWon > rhs.gamesWon }
            if lhs.pointDifferential != rhs.pointDifferential { return lhs.pointDifferential > rhs.pointDifferential }
            if lhs.totalAdjustedScore != rhs.totalAdjustedScore { return lhs.totalAdjustedScore > rhs.totalAdjustedScore }
            if lhs.totalRawScore != rhs.totalRawScore { return lhs.totalRawScore > rhs.totalRawScore }
            return lhs.teamId < rhs.teamId
        }
    }

    public static func projectedFinalScore(currentScore: Int, inningsPlayed: Int, regulationInnings: Int = defaultRegulationInnings) -> Double {
        guard inningsPlayed > 0 else { return 0 }
        return (Double(currentScore) / Double(inningsPlayed)) * Double(regulationInnings)
    }
}
