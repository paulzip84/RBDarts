import XCTest
@testable import RBDarts

final class ScoringRulesTests: XCTestCase {
    func testStandaloneBasicTotals() throws {
        let participants = [
            ParticipantScorecard(
                participantId: "player-a",
                displayName: "Avery",
                scoresByInning: [1: 1, 2: 2, 3: 3, 4: 4, 5: 5, 6: 6, 7: 7, 8: 8, 9: 9]
            ),
            ParticipantScorecard(
                participantId: "player-b",
                displayName: "Blake",
                scoresByInning: [1: 0, 2: 3, 3: 3, 4: 4, 5: 4, 6: 5, 7: 6, 8: 7, 9: 8]
            )
        ]

        let summary = ScoringRules.summarizeStandalone(participants: participants)

        XCTAssertEqual(summary.participantSummaries.first { $0.participantId == "player-a" }?.total, 45)
        XCTAssertEqual(summary.participantSummaries.first { $0.participantId == "player-b" }?.total, 40)
        XCTAssertEqual(summary.winnerIds, ["player-a"])
        XCTAssertEqual(summary.margin, 5)
        XCTAssertFalse(summary.needsExtraInning)
    }

    func testInvalidScoresAreRejected() {
        XCTAssertThrowsError(try ScoringRules.validate(score: -1))
        XCTAssertThrowsError(try ScoringRules.validate(score: 10))
    }

    func testExtraInningsContinueUntilTieBroken() {
        let tied = [
            ParticipantScorecard(participantId: "a", displayName: "A", scoresByInning: [1: 5, 2: 5, 3: 5, 4: 5, 5: 5, 6: 5, 7: 5, 8: 5, 9: 5]),
            ParticipantScorecard(participantId: "b", displayName: "B", scoresByInning: [1: 5, 2: 5, 3: 5, 4: 5, 5: 5, 6: 5, 7: 5, 8: 5, 9: 5])
        ]
        XCTAssertTrue(ScoringRules.summarizeStandalone(participants: tied).needsExtraInning)

        let resolved = [
            ParticipantScorecard(participantId: "a", displayName: "A", scoresByInning: [1: 5, 2: 5, 3: 5, 4: 5, 5: 5, 6: 5, 7: 5, 8: 5, 9: 5, 10: 1]),
            ParticipantScorecard(participantId: "b", displayName: "B", scoresByInning: [1: 5, 2: 5, 3: 5, 4: 5, 5: 5, 6: 5, 7: 5, 8: 5, 9: 5, 10: 2])
        ]
        let summary = ScoringRules.summarizeStandalone(participants: resolved)
        XCTAssertFalse(summary.needsExtraInning)
        XCTAssertEqual(summary.winnerIds, ["b"])
    }

    func testHandicapRounding() {
        let averages = ["higher": 72.0, "lower": 63.4]
        XCTAssertEqual(ScoringRules.handicap(teamAverageSums: averages, handicapPercent: 80, roundingRule: .nearest).handicap, 7)
        XCTAssertEqual(ScoringRules.handicap(teamAverageSums: averages, handicapPercent: 80, roundingRule: .floor).handicap, 6)
        XCTAssertEqual(ScoringRules.handicap(teamAverageSums: averages, handicapPercent: 80, roundingRule: .ceiling).handicap, 7)
        XCTAssertEqual(ScoringRules.handicap(teamAverageSums: averages, handicapPercent: 80, roundingRule: .decimal).handicap, 6.88, accuracy: 0.001)
    }

    func testBaseballDartLevelScoringCountsOnlyCurrentTarget() throws {
        let turn = makeTurn(id: "turn-1", participantId: "player-a", teamId: nil, playerId: "player-a", inning: 4, score: 4)

        XCTAssertEqual(try ScoringRules.score(turn: turn), 4)
        XCTAssertEqual(try ScoringRules.score(dart: makeDart(index: 1, target: 4, landed: 4, ring: .single), inningTarget: 4), 1)
        XCTAssertEqual(try ScoringRules.score(dart: makeDart(index: 2, target: 4, landed: 4, ring: .triple), inningTarget: 4), 3)
        XCTAssertEqual(try ScoringRules.score(dart: makeDart(index: 3, target: 4, landed: 5, ring: .triple, validity: .wrongNumber), inningTarget: 4), 0)
    }

    func testBaseballMaximumsAndPost20Targets() throws {
        let maxTurn = BaseballTurn(
            turnId: "max-turn",
            gameId: "game-1",
            inningNumber: 9,
            targetNumber: 9,
            participantId: "player-a",
            teamId: nil,
            playerId: "player-a",
            lineupSlot: 1,
            darts: [
                makeDart(index: 1, target: 9, landed: 9, ring: .triple),
                makeDart(index: 2, target: 9, landed: 9, ring: .triple),
                makeDart(index: 3, target: 9, landed: 9, ring: .triple)
            ],
            enteredByUserId: "scorekeeper",
            createdAt: Date(timeIntervalSince1970: 0),
            updatedAt: Date(timeIntervalSince1970: 0)
        )

        XCTAssertEqual(try ScoringRules.score(turn: maxTurn), 9)
        XCTAssertEqual(ScoringRules.maximumIndividualScore(), 81)
        XCTAssertEqual(try ScoringRules.maximumTeamScore(playerCount: 4), 324)
        XCTAssertEqual(try ScoringRules.target(forInning: 21, ruleSet: BaseballRuleSet(post20TieRule: .repeat20)), 20)
        XCTAssertEqual(try ScoringRules.target(forInning: 21, ruleSet: BaseballRuleSet(post20TieRule: .restartAt1)), 1)
        XCTAssertEqual(try ScoringRules.target(forInning: 21, ruleSet: BaseballRuleSet(post20TieRule: .bullseyeTiebreaker)), ScoringRules.bullseyeTarget)
    }

    func testBaseballNineInningGameDeclaresWinnerAfterEqualTurns() throws {
        let participants = makePlayers(("player-a", "Avery"), ("player-b", "Blake"))
        let turns = (1...9).flatMap { inning in
            [
                makeTurn(id: "a-\(inning)", participantId: "player-a", teamId: nil, playerId: "player-a", inning: inning, score: 5),
                makeTurn(id: "b-\(inning)", participantId: "player-b", teamId: nil, playerId: "player-b", inning: inning, score: inning == 9 ? 4 : 5)
            ]
        }

        let summary = try ScoringRules.summarizeBaseballGame(participants: participants, turns: turns)

        XCTAssertEqual(summary.winnerIds, ["player-a"])
        XCTAssertEqual(summary.margin, 1)
        XCTAssertFalse(summary.needsExtraInning)
        XCTAssertEqual(summary.currentTarget, 9)
    }

    func testBaseballTeamScoringAggregatesPlayerTurns() throws {
        let team = BaseballParticipant(
            participantId: "team-a",
            displayName: "Team A",
            kind: .team,
            teamId: "team-a",
            playerIds: ["player-1", "player-2", "player-3"],
            lineupOrder: ["player-1", "player-2", "player-3"]
        )
        let opponent = BaseballParticipant(
            participantId: "team-b",
            displayName: "Team B",
            kind: .team,
            teamId: "team-b",
            playerIds: ["player-4", "player-5"],
            lineupOrder: ["player-4", "player-5"]
        )
        let turns = [
            makeTurn(id: "t1", participantId: "team-a", teamId: "team-a", playerId: "player-1", inning: 1, score: 2),
            makeTurn(id: "t2", participantId: "team-a", teamId: "team-a", playerId: "player-2", inning: 1, score: 1),
            makeTurn(id: "t3", participantId: "team-a", teamId: "team-a", playerId: "player-3", inning: 1, score: 3)
        ]

        try ScoringRules.validate(baseballParticipants: [team, opponent])

        XCTAssertEqual(try ScoringRules.teamInningTotal(turns: turns, teamId: "team-a", inningNumber: 1), 6)
        XCTAssertTrue(ScoringRules.isParticipantCompleteForInning(participant: team, turns: turns, inningNumber: 1))
    }

    func testBaseballExtraInningsRequireEqualTurnsBeforeWinner() throws {
        let participants = makePlayers(("team-a", "Team A"), ("team-b", "Team B"))
        let tiedRegulation = (1...9).flatMap { inning in
            [
                makeTurn(id: "a-\(inning)", participantId: "team-a", teamId: nil, playerId: "team-a", inning: inning, score: 5),
                makeTurn(id: "b-\(inning)", participantId: "team-b", teamId: nil, playerId: "team-b", inning: inning, score: 5)
            ]
        }
        let partialExtra = tiedRegulation + [
            makeTurn(id: "a-10", participantId: "team-a", teamId: nil, playerId: "team-a", inning: 10, score: 2)
        ]

        let waiting = try ScoringRules.summarizeBaseballGame(participants: participants, turns: partialExtra)

        XCTAssertTrue(waiting.winnerIds.isEmpty)
        XCTAssertTrue(waiting.needsExtraInning)
        XCTAssertEqual(waiting.currentTarget, 10)

        let resolved = try ScoringRules.summarizeBaseballGame(
            participants: participants,
            turns: partialExtra + [makeTurn(id: "b-10", participantId: "team-b", teamId: nil, playerId: "team-b", inning: 10, score: 3)]
        )

        XCTAssertEqual(resolved.winnerIds, ["team-b"])
        XCTAssertEqual(resolved.margin, 1)
        XCTAssertFalse(resolved.needsExtraInning)
    }

    func testBaseballLockedGameCorrectionsRequireAuthorizedRole() throws {
        let score = InningScore(
            inningScoreId: "score-1",
            gameId: "game-1",
            playerId: "player-a",
            teamId: "team-a",
            inningNumber: 7,
            target: 7,
            score: 2,
            enteredByUserId: "scorekeeper",
            createdAt: Date(timeIntervalSince1970: 0),
            updatedAt: Date(timeIntervalSince1970: 0)
        )

        XCTAssertThrowsError(
            try ScoringRules.applyAuthorizedCorrection(
                score: score,
                newValue: 3,
                reason: "Obvious entry mistake",
                editedByUserId: "player-user",
                editedByRole: .player,
                affectedRecords: ["score-1"],
                gameStatus: .locked
            )
        )

        let result = try ScoringRules.applyAuthorizedCorrection(
            score: score,
            newValue: 3,
            reason: "Obvious entry mistake",
            editedByUserId: "manager-user",
            editedByRole: .leagueManager,
            affectedRecords: ["score-1"],
            gameStatus: .locked,
            now: Date(timeIntervalSince1970: 0)
        )

        XCTAssertEqual(result.0.score, 3)
        XCTAssertEqual(result.1.previousValue, 2)
        XCTAssertEqual(result.1.newValue, 3)
        XCTAssertEqual(result.1.editedByRole, .leagueManager)
        XCTAssertEqual(result.1.reason, "Obvious entry mistake")
    }

    func testBaseballScoreboardAndAnalyticsAreDerivedFromDarts() throws {
        let participants = makePlayers(("player-a", "Avery"), ("player-b", "Blake"))
        let turns = [
            makeTurn(id: "a-1", participantId: "player-a", teamId: nil, playerId: "player-a", inning: 1, score: 9),
            makeTurn(id: "b-1", participantId: "player-b", teamId: nil, playerId: "player-b", inning: 1, score: 3),
            makeTurn(id: "a-2", participantId: "player-a", teamId: nil, playerId: "player-a", inning: 2, score: 0)
        ]

        let scoreboard = try ScoringRules.scoreboardSnapshot(
            gameId: "game-1",
            participants: participants,
            turns: turns,
            currentParticipantId: "player-b",
            currentThrowerDisplayName: "Blake",
            currentInning: 2
        )
        let analytics = try ScoringRules.baseballAnalytics(
            subjectId: "player-a",
            subjectType: .player,
            turns: turns,
            wins: 1,
            gamesPlayed: 2
        )

        XCTAssertEqual(scoreboard.targetNumber, 2)
        XCTAssertEqual(scoreboard.leaderId, "player-a")
        XCTAssertEqual(scoreboard.leadMargin, 6)
        XCTAssertEqual(scoreboard.remainingInnings, 7)
        XCTAssertEqual(analytics.averageScorePerInning ?? -1, 4.5, accuracy: 0.001)
        XCTAssertEqual(analytics.highestInningScore, 9)
        XCTAssertEqual(analytics.shutoutInnings, 1)
        XCTAssertEqual(analytics.winPercentage ?? -1, 0.5, accuracy: 0.001)
        XCTAssertTrue(analytics.isEstimate)
    }

    private func makePlayers(_ pairs: (String, String)...) -> [BaseballParticipant] {
        pairs.map { id, name in
            BaseballParticipant(
                participantId: id,
                displayName: name,
                kind: .player,
                playerIds: [id],
                lineupOrder: [id]
            )
        }
    }

    private func makeTurn(
        id: String,
        participantId: String,
        teamId: String?,
        playerId: String,
        inning: Int,
        score: Int
    ) -> BaseballTurn {
        let target = try! ScoringRules.target(forInning: inning, ruleSet: BaseballRuleSet())
        return BaseballTurn(
            turnId: id,
            gameId: "game-1",
            inningNumber: inning,
            targetNumber: target,
            participantId: participantId,
            teamId: teamId,
            playerId: playerId,
            lineupSlot: 1,
            darts: makeDarts(target: target, score: score),
            enteredByUserId: "scorekeeper",
            createdAt: Date(timeIntervalSince1970: 0),
            updatedAt: Date(timeIntervalSince1970: 0)
        )
    }

    private func makeDarts(target: Int, score: Int) -> [BaseballDartThrow] {
        precondition((0...9).contains(score))
        var remaining = score
        return (1...3).map { index in
            let ring: DartRing
            if remaining >= 3 {
                ring = .triple
            } else if remaining == 2 {
                ring = .double
            } else if remaining == 1 {
                ring = .single
            } else {
                ring = .miss
            }
            let value: Int
            switch ring {
            case .triple:
                value = 3
            case .double:
                value = 2
            case .single:
                value = 1
            default:
                value = 0
            }
            remaining -= value
            return makeDart(index: index, target: target, landed: ring == .miss ? nil : target, ring: ring)
        }
    }

    private func makeDart(index: Int, target: Int, landed: Int?, ring: DartRing, validity: DartValidity = .valid) -> BaseballDartThrow {
        BaseballDartThrow(
            dartThrowId: "dart-\(target)-\(index)-\(ring.rawValue)",
            dartIndex: index,
            targetNumber: target,
            landedNumber: landed,
            ring: ring,
            validity: validity
        )
    }
}
