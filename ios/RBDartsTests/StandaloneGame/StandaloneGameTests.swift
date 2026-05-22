import XCTest
@testable import RBDarts

final class StandaloneGameTests: XCTestCase {
    func testStandaloneServiceCreatesAndCompletesGame() async throws {
        let service = StandaloneGameService(
            repository: InMemoryRBDartsRepository(),
            sessionStore: FileActiveScoreSessionStore(directory: URL(fileURLWithPath: NSTemporaryDirectory()))
        )
        var session = try await service.createGame(
            name: "Test",
            createdByUserId: "user-1",
            participantNames: ["Avery", "Blake"]
        )

        for inning in 1...9 {
            session = try await service.enterScore(inning, participantId: "participant-1", inningNumber: inning, in: session)
            session = try await service.enterScore(max(0, inning - 1), participantId: "participant-2", inningNumber: inning, in: session)
        }

        let summary = try await service.complete(session)
        XCTAssertEqual(summary.winnerIds, ["participant-1"])
        XCTAssertEqual(summary.inningsPlayed, 9)
    }
}
