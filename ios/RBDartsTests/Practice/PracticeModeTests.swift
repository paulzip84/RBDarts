import XCTest
@testable import RBDarts

final class PracticeModeTests: XCTestCase {
    func testPracticeStatsRemainSeparateFromOfficialStats() {
        let attempts = [0, 4, 5, 9, 5].enumerated().map { index, score in
            PracticeAttempt(practiceAttemptId: "p\(index)", playerId: "player-a", userId: "user-a", target: 5, score: score, attemptDate: Date(), createdAt: Date())
        }

        let stats = ScoringRules.practiceStats(from: attempts)

        XCTAssertEqual(stats.attempts, 5)
        XCTAssertEqual(stats.average, 4.6, accuracy: 0.001)
        XCTAssertEqual(stats.bestScore, 9)
        XCTAssertEqual(stats.zeroCount, 1)
        XCTAssertEqual(stats.nineCount, 1)
    }
}
