import XCTest
@testable import RBDarts

final class CorrectionWorkflowTests: XCTestCase {
    func testCorrectionRequiresReasonAndCreatesAudit() throws {
        let score = InningScore(
            inningScoreId: "score-1",
            gameId: "game-1",
            playerId: "player-1",
            teamId: "team-1",
            inningNumber: 7,
            target: 7,
            score: 2,
            enteredByUserId: "scorekeeper-1",
            createdAt: Date(),
            updatedAt: Date()
        )

        let service = CorrectionService()
        XCTAssertThrowsError(try service.requestCorrection(score: score, newValue: 7, reason: "", requestedByUserId: "manager-1", requestedByRole: .leagueManager))

        let audit = try service.requestCorrection(score: score, newValue: 7, reason: "Wrong score", requestedByUserId: "manager-1", requestedByRole: .leagueManager)
        XCTAssertEqual(audit.status, .requested)
        XCTAssertEqual(audit.previousValue, 2)
        XCTAssertEqual(audit.newValue, 7)
    }
}
