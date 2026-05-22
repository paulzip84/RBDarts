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
}
