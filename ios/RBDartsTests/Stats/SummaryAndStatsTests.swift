import XCTest
@testable import RBDarts

final class SummaryAndStatsTests: XCTestCase {
    func testProjectionAndSummary() {
        let projection = ProjectionService().baseline(currentScore: 20, inningsPlayed: 4)
        XCTAssertEqual(projection.label, "Estimate")
        XCTAssertEqual(projection.projectedFinalScore, 45)

        let summary = StatsService().gameSummary(participants: [
            ParticipantScorecard(participantId: "a", displayName: "A", scoresByInning: [1: 9, 2: 9]),
            ParticipantScorecard(participantId: "b", displayName: "B", scoresByInning: [1: 1, 2: 1])
        ])
        XCTAssertEqual(summary.winnerIds, ["a"])
    }
}
