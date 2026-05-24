import Foundation

public enum ScoreValidationError: LocalizedError, Equatable, Sendable {
    case invalidScore(Int)
    case invalidInning(Int)
    case invalidRuleSet(String)
    case lockedGame
    case correctionReasonRequired
    case unauthorizedCorrection

    public var errorDescription: String? {
        switch self {
        case .invalidScore:
            return "Scores must be whole numbers from 0 through 9."
        case .invalidInning:
            return "Inning numbers must be positive."
        case .invalidRuleSet(let message):
            return message
        case .lockedGame:
            return "Locked games require an authorized correction."
        case .correctionReasonRequired:
            return "A correction reason is required."
        case .unauthorizedCorrection:
            return "User role is not authorized to correct this score."
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
    public static let bullseyeTarget = 25

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
        return try target(forInning: inningNumber, ruleSet: BaseballRuleSet())
    }

    public static func target(forInning inningNumber: Int, ruleSet: BaseballRuleSet) throws -> Int {
        try validate(inningNumber: inningNumber)
        if inningNumber <= 20 { return inningNumber }
        switch ruleSet.post20TieRule {
        case .repeat20:
            return 20
        case .restartAt1:
            return ((inningNumber - 1) % 20) + 1
        case .bullseyeTiebreaker:
            return bullseyeTarget
        }
    }

    public static func maximumTurnScore(ruleSet: BaseballRuleSet = BaseballRuleSet()) -> Int {
        ruleSet.dartsPerTurn * ruleSet.tripleValue
    }

    public static func maximumIndividualScore(ruleSet: BaseballRuleSet = BaseballRuleSet()) -> Int {
        ruleSet.regulationInnings * maximumTurnScore(ruleSet: ruleSet)
    }

    public static func maximumTeamScore(playerCount: Int, ruleSet: BaseballRuleSet = BaseballRuleSet()) throws -> Int {
        guard playerCount >= 0 else {
            throw ScoreValidationError.invalidRuleSet("Player count cannot be negative.")
        }
        return playerCount * maximumIndividualScore(ruleSet: ruleSet)
    }

    public static func validate(ruleSet: BaseballRuleSet) throws {
        guard ruleSet.regulationInnings >= 1 else {
            throw ScoreValidationError.invalidRuleSet("Regulation innings must be at least 1.")
        }
        guard ruleSet.dartsPerTurn == 3 else {
            throw ScoreValidationError.invalidRuleSet("Standard Baseball Darts requires exactly 3 darts per turn.")
        }
        guard maximumTurnScore(ruleSet: ruleSet) <= 9 else {
            throw ScoreValidationError.invalidRuleSet("Standard Baseball Darts turn scores cannot exceed 9.")
        }
    }

    public static func score(
        dart: BaseballDartThrow,
        inningTarget: Int? = nil,
        ruleSet: BaseballRuleSet = BaseballRuleSet()
    ) throws -> Int {
        try validate(ruleSet: ruleSet)
        guard (1...ruleSet.dartsPerTurn).contains(dart.dartIndex) else {
            throw ScoreValidationError.invalidRuleSet("Dart index must be between 1 and \(ruleSet.dartsPerTurn).")
        }
        let target = inningTarget ?? dart.targetNumber
        guard dart.validity == .valid, dart.ring != .miss else { return 0 }

        if target == bullseyeTarget {
            switch dart.ring {
            case .outerBull:
                return 1
            case .innerBull:
                return 2
            default:
                return 0
            }
        }

        guard dart.landedNumber == target else { return 0 }
        switch dart.ring {
        case .single:
            return ruleSet.singleValue
        case .double:
            return ruleSet.doubleValue
        case .triple:
            return ruleSet.tripleValue
        case .outerBull:
            return ruleSet.bullseyeBonusEnabled ? 1 : 0
        case .innerBull:
            return ruleSet.bullseyeBonusEnabled ? 2 : 0
        case .miss:
            return 0
        }
    }

    public static func score(turn: BaseballTurn, ruleSet: BaseballRuleSet = BaseballRuleSet()) throws -> Int {
        try validate(ruleSet: ruleSet)
        try validate(inningNumber: turn.inningNumber)
        guard turn.darts.count == ruleSet.dartsPerTurn else {
            throw ScoreValidationError.invalidRuleSet("A complete Baseball Darts turn requires \(ruleSet.dartsPerTurn) darts.")
        }
        let expectedTarget = try target(forInning: turn.inningNumber, ruleSet: ruleSet)
        guard turn.targetNumber == expectedTarget else {
            throw ScoreValidationError.invalidRuleSet("Turn target \(turn.targetNumber) does not match inning target \(expectedTarget).")
        }
        let total = try turn.darts.reduce(0) { result, dart in
            result + (try score(dart: dart, inningTarget: expectedTarget, ruleSet: ruleSet))
        }
        try validate(score: total)
        return total
    }

    public static func participantInningTotals(
        turns: [BaseballTurn],
        participantId: String,
        ruleSet: BaseballRuleSet = BaseballRuleSet()
    ) throws -> [Int: Int] {
        var totals: [Int: Int] = [:]
        for turn in turns where turn.participantId == participantId {
            totals[turn.inningNumber, default: 0] += try score(turn: turn, ruleSet: ruleSet)
        }
        return totals
    }

    public static func participantTotal(
        turns: [BaseballTurn],
        participantId: String,
        through inningsPlayed: Int? = nil,
        ruleSet: BaseballRuleSet = BaseballRuleSet()
    ) throws -> Int {
        let totals = try participantInningTotals(turns: turns, participantId: participantId, ruleSet: ruleSet)
        return totals.reduce(into: 0) { result, item in
            if inningsPlayed == nil || item.key <= inningsPlayed! {
                result += item.value
            }
        }
    }

    public static func teamInningTotal(
        turns: [BaseballTurn],
        teamId: String,
        inningNumber: Int,
        ruleSet: BaseballRuleSet = BaseballRuleSet()
    ) throws -> Int {
        try validate(inningNumber: inningNumber)
        return try turns
            .filter { $0.teamId == teamId && $0.inningNumber == inningNumber }
            .reduce(0) { result, turn in result + (try score(turn: turn, ruleSet: ruleSet)) }
    }

    public static func validate(baseballParticipants participants: [BaseballParticipant]) throws {
        guard participants.count >= 2 else {
            throw ScoreValidationError.invalidRuleSet("A Baseball Darts game requires at least two participants.")
        }
        for participant in participants where participant.kind == .team {
            guard participant.playerIds.count >= 2 else {
                throw ScoreValidationError.invalidRuleSet("Official team Baseball Darts requires at least two players per team.")
            }
            if !participant.lineupOrder.isEmpty && Set(participant.lineupOrder) != Set(participant.playerIds) {
                throw ScoreValidationError.invalidRuleSet("Team batting order must include the same players as the team roster.")
            }
        }
    }

    public static func isParticipantCompleteForInning(
        participant: BaseballParticipant,
        turns: [BaseballTurn],
        inningNumber: Int,
        ruleSet: BaseballRuleSet = BaseballRuleSet()
    ) -> Bool {
        let participantTurns = turns.filter { $0.participantId == participant.participantId && $0.inningNumber == inningNumber }
        if participant.kind == .team {
            let expectedPlayers = participant.lineupOrder.isEmpty ? participant.playerIds : participant.lineupOrder
            return !expectedPlayers.isEmpty && expectedPlayers.allSatisfy { playerId in
                participantTurns.contains { $0.playerId == playerId && $0.darts.count == ruleSet.dartsPerTurn }
            }
        }
        return participantTurns.contains { $0.darts.count == ruleSet.dartsPerTurn }
    }

    public static func lastEqualTurnInning(
        participants: [BaseballParticipant],
        turns: [BaseballTurn],
        ruleSet: BaseballRuleSet = BaseballRuleSet()
    ) throws -> Int {
        try validate(baseballParticipants: participants)
        try validate(ruleSet: ruleSet)
        let maxInning = turns.map(\.inningNumber).max() ?? 0
        guard maxInning > 0 else { return 0 }
        var lastComplete = 0
        for inning in 1...maxInning {
            if participants.allSatisfy({ isParticipantCompleteForInning(participant: $0, turns: turns, inningNumber: inning, ruleSet: ruleSet) }) {
                lastComplete = inning
            } else {
                break
            }
        }
        return lastComplete
    }

    public static func summarizeBaseballGame(
        participants: [BaseballParticipant],
        turns: [BaseballTurn],
        ruleSet: BaseballRuleSet = BaseballRuleSet()
    ) throws -> BaseballGameSummary {
        try validate(baseballParticipants: participants)
        try validate(ruleSet: ruleSet)
        for turn in turns {
            _ = try score(turn: turn, ruleSet: ruleSet)
        }

        let equalTurnInning = try lastEqualTurnInning(participants: participants, turns: turns, ruleSet: ruleSet)
        let scoringThrough = equalTurnInning == 0 ? (turns.map(\.inningNumber).max() ?? 0) : equalTurnInning
        let summaries = try participants.map { participant -> BaseballParticipantSummary in
            let inningTotals = try participantInningTotals(turns: turns, participantId: participant.participantId, ruleSet: ruleSet)
                .filter { $0.key <= scoringThrough }
            let total = participant.startingHandicap + inningTotals.values.reduce(0, +)
            return BaseballParticipantSummary(
                participantId: participant.participantId,
                displayName: participant.displayName,
                total: total,
                inningTotals: inningTotals,
                zeroCount: inningTotals.values.filter { $0 == 0 }.count,
                nineCount: inningTotals.values.filter { $0 == maximumTurnScore(ruleSet: ruleSet) }.count
            )
        }.sorted { $0.displayName < $1.displayName }

        let highestTotal = summaries.map(\.total).max() ?? 0
        let leaders = summaries.filter { $0.total == highestTotal }
        let orderedTotals = summaries.map(\.total).sorted(by: >)
        let tied = leaders.count > 1
        let equalTurnComplete = equalTurnInning >= ruleSet.regulationInnings
        let canDeclareWinner = equalTurnComplete && !tied
        let needsExtra = ruleSet.extraInningsEnabled && equalTurnComplete && tied
        let nextInning: Int
        if needsExtra {
            nextInning = equalTurnInning + 1
        } else if equalTurnInning == 0 {
            nextInning = 1
        } else {
            nextInning = equalTurnInning
        }

        return BaseballGameSummary(
            participantSummaries: summaries,
            winnerIds: canDeclareWinner ? leaders.map(\.participantId) : [],
            margin: canDeclareWinner && orderedTotals.count > 1 ? orderedTotals[0] - orderedTotals[1] : 0,
            inningsPlayed: max(equalTurnInning, ruleSet.regulationInnings),
            needsExtraInning: needsExtra,
            currentTarget: try target(forInning: nextInning, ruleSet: ruleSet)
        )
    }

    public static func canEditLockedGame(role: LeagueRole) -> Bool {
        role == .leagueManager || role == .admin
    }

    public static func canCorrectScore(
        gameStatus: GameStatus,
        role: LeagueRole,
        isOwnTeam: Bool = false,
        policyAllowsScorekeeper: Bool = true
    ) -> Bool {
        switch gameStatus {
        case .locked, .finalized:
            return canEditLockedGame(role: role)
        case .complete, .corrected:
            return role == .leagueManager || role == .admin || (role == .teamManager && isOwnTeam)
        case .scoring:
            return role == .leagueManager ||
                role == .admin ||
                (role == .scorekeeper && policyAllowsScorekeeper) ||
                (role == .teamManager && isOwnTeam)
        case .setup:
            return role == .leagueManager || role == .admin || role == .teamManager
        case .voided:
            return false
        }
    }

    public static func applyAuthorizedCorrection(
        score: InningScore,
        newValue: Int,
        reason: String,
        editedByUserId: String,
        editedByRole: LeagueRole,
        affectedRecords: [String],
        gameStatus: GameStatus,
        isOwnTeam: Bool = false,
        now: Date = Date()
    ) throws -> (InningScore, CorrectionAuditRecord) {
        guard canCorrectScore(gameStatus: gameStatus, role: editedByRole, isOwnTeam: isOwnTeam) else {
            throw ScoreValidationError.unauthorizedCorrection
        }
        return try applyCorrection(
            score: score,
            newValue: newValue,
            reason: reason,
            editedByUserId: editedByUserId,
            editedByRole: editedByRole,
            affectedRecords: affectedRecords,
            now: now
        )
    }

    public static func canSubstitutePlayer(
        role: LeagueRole,
        midGame: Bool,
        isOwnTeam: Bool = false,
        leagueAllowsMidGame: Bool = false
    ) -> Bool {
        let roleAllowed = role == .leagueManager || role == .admin || (role == .teamManager && isOwnTeam)
        return roleAllowed && (!midGame || leagueAllowsMidGame)
    }

    public static func scoreboardSnapshot(
        gameId: String,
        participants: [BaseballParticipant],
        turns: [BaseballTurn],
        currentParticipantId: String,
        currentThrowerDisplayName: String,
        currentInning: Int,
        currentTurnDarts: [BaseballDartThrow] = [],
        gameStatus: GameStatus = .scoring,
        ruleSet: BaseballRuleSet = BaseballRuleSet()
    ) throws -> BaseballScoreboardSnapshot {
        let summary = try summarizeBaseballGame(participants: participants, turns: turns, ruleSet: ruleSet)
        let currentParticipant = participants.first { $0.participantId == currentParticipantId }
        let target = try target(forInning: currentInning, ruleSet: ruleSet)
        let currentTurnScore = try currentTurnDarts.reduce(0) { result, dart in
            result + (try score(dart: dart, inningTarget: target, ruleSet: ruleSet))
        }
        let currentParticipantTotal = try participantTotal(
            turns: turns,
            participantId: currentParticipantId,
            through: currentInning,
            ruleSet: ruleSet
        ) + currentTurnScore
        let teamInning: Int?
        let teamTotal: Int?
        if let teamId = currentParticipant?.teamId {
            teamInning = try teamInningTotal(turns: turns, teamId: teamId, inningNumber: currentInning, ruleSet: ruleSet) + currentTurnScore
            teamTotal = try turns
                .filter { $0.teamId == teamId && $0.inningNumber <= currentInning }
                .reduce(0) { result, turn in result + (try score(turn: turn, ruleSet: ruleSet)) } + currentTurnScore
        } else {
            teamInning = nil
            teamTotal = nil
        }
        var liveTotals: [String: Int] = [:]
        for participant in participants {
            let base = try participantTotal(
                turns: turns,
                participantId: participant.participantId,
                through: currentInning,
                ruleSet: ruleSet
            ) + participant.startingHandicap
            liveTotals[participant.participantId] = participant.participantId == currentParticipantId ? base + currentTurnScore : base
        }
        let highestLiveTotal = liveTotals.values.max()
        let liveLeaders = liveTotals.filter { $0.value == highestLiveTotal }.map(\.key)
        let orderedLiveTotals = liveTotals.values.sorted(by: >)

        return BaseballScoreboardSnapshot(
            gameId: gameId,
            currentInning: currentInning,
            targetNumber: target,
            currentThrowerId: currentParticipantId,
            currentThrowerDisplayName: currentThrowerDisplayName,
            playerInningScore: currentTurnScore,
            playerTotal: currentParticipantTotal,
            teamInningScore: teamInning,
            teamTotal: teamTotal,
            leaderId: liveLeaders.count == 1 ? liveLeaders.first : nil,
            leadMargin: liveLeaders.count == 1 && orderedLiveTotals.count > 1 ? orderedLiveTotals[0] - orderedLiveTotals[1] : 0,
            remainingInnings: max(ruleSet.regulationInnings - currentInning, 0),
            needsExtraInning: summary.needsExtraInning,
            gameStatus: gameStatus
        )
    }

    public static func baseballAnalytics(
        subjectId: String,
        subjectType: BaseballAnalyticsSubject,
        turns: [BaseballTurn],
        wins: Int = 0,
        gamesPlayed: Int = 0,
        ruleSet: BaseballRuleSet = BaseballRuleSet()
    ) throws -> BaseballAnalyticsSnapshot {
        let subjectTurns = turns.filter {
            if subjectType == .team {
                return $0.teamId == subjectId || $0.participantId == subjectId
            }
            return $0.playerId == subjectId || $0.participantId == subjectId
        }
        guard !subjectTurns.isEmpty else {
            return BaseballAnalyticsSnapshot(
                subjectId: subjectId,
                subjectType: subjectType,
                averageScorePerInning: nil,
                highestInningScore: nil,
                shutoutInnings: 0,
                tripleRate: nil,
                winPercentage: nil,
                projectedFinalScore: nil,
                comebackProbability: nil,
                consistency: nil,
                bestTargetNumbers: [],
                weakestTargetNumbers: [],
                isEstimate: false
            )
        }

        let scores = try subjectTurns.map { try score(turn: $0, ruleSet: ruleSet) }
        let average = Double(scores.reduce(0, +)) / Double(scores.count)
        let variance = scores.map { pow(Double($0) - average, 2) }.reduce(0, +) / Double(scores.count)
        let darts = subjectTurns.flatMap(\.darts)
        let tripleCount = darts.filter { $0.validity == .valid && $0.ring == .triple && $0.landedNumber == $0.targetNumber }.count
        let validDartCount = darts.filter { $0.validity == .valid }.count
        var targetAverages: [Int: Double] = [:]
        for target in Set(subjectTurns.map(\.targetNumber)) {
            let targetScores = try subjectTurns.filter { $0.targetNumber == target }.map { try score(turn: $0, ruleSet: ruleSet) }
            targetAverages[target] = Double(targetScores.reduce(0, +)) / Double(targetScores.count)
        }
        let bestTargets = targetAverages.sorted {
            if $0.value == $1.value { return $0.key < $1.key }
            return $0.value > $1.value
        }.prefix(3).map(\.key)
        let weakestTargets = targetAverages.sorted {
            if $0.value == $1.value { return $0.key < $1.key }
            return $0.value < $1.value
        }.prefix(3).map(\.key)

        return BaseballAnalyticsSnapshot(
            subjectId: subjectId,
            subjectType: subjectType,
            averageScorePerInning: average,
            highestInningScore: scores.max(),
            shutoutInnings: scores.filter { $0 == 0 }.count,
            tripleRate: validDartCount == 0 ? nil : Double(tripleCount) / Double(validDartCount),
            winPercentage: gamesPlayed <= 0 ? nil : Double(wins) / Double(gamesPlayed),
            projectedFinalScore: average * Double(ruleSet.regulationInnings),
            comebackProbability: nil,
            consistency: sqrt(variance),
            bestTargetNumbers: Array(bestTargets),
            weakestTargetNumbers: Array(weakestTargets),
            isEstimate: true
        )
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
